package tdc.edu.vn.project_mobile_be.services.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdc.edu.vn.project_mobile_be.commond.AppException;
import tdc.edu.vn.project_mobile_be.enums.ErrorCode;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.AuthenticationRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.IntrospectRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.LogoutRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.jwt.RefreshRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.jwt.AuthenticationResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.jwt.IntrospectResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.roles.Role;
import tdc.edu.vn.project_mobile_be.entities.token.InvalidatedToken;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.InvalidatedTokenRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.RoleRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;

import java.text.ParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationServiceImp {
    @Autowired
    UserRepository userRepository;
    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;
    @Autowired
    RoleRepository roleRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
//    @NonFinal
//    @Value("${jwt.valid-duration}")
//    protected long VALID_DURATION;
//
//    @NonFinal
//    @Value("${jwt.refreshable-duration}")
//    protected long REFRESHABLE_DURATION;

    @Transactional
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        Optional<User> optionalUser = userRepository.findByUserEmail(request.getUserEmail());
        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("Khong tim thay");
        }
        User user = optionalUser.get();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getUserPassword(), user.getUserPassword());

        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);

        return AuthenticationResponseDTO.builder()
                .token(token)
                .authenticated(true)
                .build();
    }
    //Kiem tra token
    public IntrospectResponseDTO introspect(IntrospectRequestDTO request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (Exception e) {
            isValid = false;
        }

        return IntrospectResponseDTO.builder()
                .valid(isValid)
                .build();
    }


    public void logout(LogoutRequestDTO request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = new Date();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expires(expiryTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token không hợp lệ hoặc đã hết hạn");
        }
    }


    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean verified = signedJWT.verify(verifier);
        if (!verified) {
            throw new RuntimeException("Token không hợp lệ");
        }

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new RuntimeException("Token đã bị vô hiệu hóa");
        }

        return signedJWT;
    }

    public AuthenticationResponseDTO refreshToken(RefreshRequestDTO request) throws ParseException, JOSEException {
        //Kiem tra hieu luc token
        var signedJWT = verifyToken(request.getToken(),true);
        //Thuc hien Refresh
        //Lay ID token va tg hieu luc
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        //logout token
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expires(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
        //Lay User
        var userName = signedJWT.getJWTClaimsSet().getSubject();

        var user = userRepository.findByUserEmail(userName).orElseThrow(
                () -> new RuntimeException("Khong tim thay USER")
        );
        var token = generateToken(user);

        return AuthenticationResponseDTO.builder()
                .token(token)
                .authenticated(true)
                .build();

    }

    @Transactional
    public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserEmail())
                .issuer("22211tt4420.mail.tdc.edu.vn")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (user != null) {
            Set<Role> roles = user.getRoles();
            if (roles != null && !roles.isEmpty()) {
                roles.forEach(role -> {
                    stringJoiner.add("ROLE_" + role.getRoleName());
                    // Kiểm tra nếu role có permissions không
                    if (role.getPermissions() != null) {
                        role.getPermissions().forEach(permission -> {
                            stringJoiner.add(permission.getPermissionName());
                        });
                    }
                });
            } else {
                log.warn("User has no roles assigned.");
            }
        } else {
            log.warn("User is null.");
        }

        return stringJoiner.toString();
    }
}
