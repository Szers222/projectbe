package tdc.edu.vn.project_mobile_be.services.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.AppException;
import tdc.edu.vn.project_mobile_be.commond.ErrorCode;
import tdc.edu.vn.project_mobile_be.dtos.requests.AuthenticationRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.IntrospectRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.AuthenticationResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.IntrospectResponseDTO;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserRepository;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationServiceImp {
    @Autowired
    UserRepository userRepository;

    @NonFinal
    protected static final String SIGNER_KEY = "CHpSZnAya03fnmgaxVnU9u4gdxZvnx+UFcrrC5i9+ZwI4+f5CNP0BlQEDCjNeyHh\n";

    //Dang Nhap
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        var userName = userRepository.findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getUserPassword(), userName.getUserPassword());
        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(request.getUserEmail());


        return AuthenticationResponseDTO.builder()
                    .token(token)
                    .authenticated(true)
                    .build();

    }
    //JWT
    public IntrospectResponseDTO introspect(IntrospectRequestDTO request) throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Kiểm tra token hết hạn hay chưa
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean isVerified = signedJWT.verify(verifier);
        boolean isValid = isVerified && expirationTime.after(new Date());

        return IntrospectResponseDTO.builder()
                .valid(isValid)
                .build();

    }
    //Thuat toan token
    String generateToken(String email) {
        //Header(nội dung thuật toán)
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        //Body(Nội dụng gửi đi token)
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(email) // tai khoan
                .issuer("22211tt4420.mail.tdc.edu.vn") // định danh do ai issuer
                .issueTime(new Date()) // thời điểm hiện tại
                .expirationTime(new Date( // het han sau 1 gio
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("customClaim","Custom")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            //Ky
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            // tra ve theo kieu string
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

}
