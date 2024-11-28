package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdc.edu.vn.project_mobile_be.dtos.requests.otp.EmailRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.otp.RegisterRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.otp.ResetPasswordRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.cart.Cart;
import tdc.edu.vn.project_mobile_be.entities.idcard.IdCard;
import tdc.edu.vn.project_mobile_be.entities.roles.Role;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.entities.user.UserOtp;
import tdc.edu.vn.project_mobile_be.enums.UserRole;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ForgotPasswordRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.RegisterRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.RoleRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserOtpRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.CartService;
import tdc.edu.vn.project_mobile_be.interfaces.service.UserOtpService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserOtpServiceImp implements UserOtpService {

    @Autowired
    @Qualifier("registerRepository")
    private final RegisterRepository registerRepository;

    @Autowired
    private final UserOtpRepository userOtpRepository;
    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final EmailService emailService;
    @Autowired
    private final ForgotPasswordRepository forgotPasswordRepository;
    @Autowired
    private CartService cartService;

    private static final Duration OTP_EXPIRATION_DURATION = Duration.ofSeconds(120);

    private String generateOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000); // Generates a 6-digit OTP
        return String.valueOf(otpValue);
    }

    private void sendVerificationEmail(String email, String otp) {
        String subject = "Email Verification";
        String body = "Your verification OTP is: " + otp;
        try {
            emailService.sendEmail(email, subject, body);
        } catch (Exception e) {
            // Log email sending failure or handle accordingly
            throw new RuntimeException("Failed to send verification email");
        }
    }

    private UserOtp insertOtp(User user) {
        UserOtp existingUserOtp = userOtpRepository.findByUser(user);
        if (existingUserOtp != null) {
            if (Duration.between(existingUserOtp.getOtpTime(), LocalDateTime.now()).compareTo(OTP_EXPIRATION_DURATION) > 0) {
                userOtpRepository.delete(existingUserOtp);
                userOtpRepository.flush();
            } else {
                throw new RuntimeException("OTP còn hiệu lực, vui lòng đợi.");
            }
        }
        String otp = generateOTP();
        UserOtp userOtp = UserOtp.builder()
                .user(user)
                .otp(otp)
                .otpTime(LocalDateTime.now())
                .build();
        return userOtpRepository.save(userOtp);
    }

    @Override
    public User createEmail(EmailRequestDTO request) {
        User existingUser = registerRepository.findByUserEmail(request.getUserEmail());
        if (existingUser != null && existingUser.getUserStatus() == 1) {
            throw new RuntimeException("Tài khoản đã tồn tại");
        }

        if (existingUser == null) {
            User user = new User();
            user.setUserEmail(request.getUserEmail());
            user.setUserStatus(0); // Status: not verified
            registerRepository.save(user);
            UserOtp userOtp = insertOtp(user);
            sendVerificationEmail(user.getUserEmail(), userOtp.getOtp());
        } else {
            UserOtp existingUserOtp = userOtpRepository.findByUser(existingUser);

            if (existingUserOtp != null) {
                // Check if OTP has expired
                if (Duration.between(existingUserOtp.getOtpTime(), LocalDateTime.now()).compareTo(OTP_EXPIRATION_DURATION) > 0) {
                    // Delete expired OTP and send a new one
                    userOtpRepository.delete(existingUserOtp);
                    // Optionally call flush to ensure changes are committed immediately
                    userOtpRepository.flush();
                }
            }

            UserOtp userOtp = insertOtp(existingUser);
            sendVerificationEmail(existingUser.getUserEmail(), userOtp.getOtp());
        }

        return User.builder()
                .userEmail(request.getUserEmail())
                .build();
    }

    @Override
    public void forgotPassword(String email) {
        User user = forgotPasswordRepository.findByUserEmail(email);
        if (user != null && user.getUserStatus() == 1) {
            forgotPasswordRepository.save(user);
            UserOtp userOtp = insertOtp(user);
            sendVerificationEmail(user.getUserEmail(), userOtp.getOtp());
        } else {
            throw new RuntimeException("Tài khoản không hợp lệ");
        }
    }

    @Override
    public void verifyOtp(String email, String otp) {
        User user = forgotPasswordRepository.findByUserEmail(email);
        if (user == null || user.getUserStatus() != 1) {
            throw new RuntimeException("Tài khoản không hợp lệ");
        }

        UserOtp userOtp = userOtpRepository.findByUser(user);
        if (userOtp == null || !otp.equals(userOtp.getOtp())) {
            throw new RuntimeException("OTP không hợp lệ");
        }

        if (Duration.between(userOtp.getOtpTime(), LocalDateTime.now()).compareTo(OTP_EXPIRATION_DURATION) > 0) {
            throw new RuntimeException("OTP đã hết hạn");
        }
    }

    @Override
    public User resetPassword(String email, ResetPasswordRequestDTO request) {
        User user = forgotPasswordRepository.findByUserEmail(email);
        if (user == null || user.getUserStatus() != 1) {
            throw new RuntimeException("Tài khoản không hợp lệ");
        }

        if (!request.getUserPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu và xác nhận mật khẩu không khớp");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodePassword = passwordEncoder.encode(request.getUserPassword());
        user.setUserPassword(encodePassword);
        forgotPasswordRepository.save(user);

        return user;
    }

    @Override
    @Transactional
    public User register(String userEmail, RegisterRequestDTO request) {
        User existingUser = registerRepository.findByUserEmail(userEmail);
        if (existingUser.getUserStatus() == 0) {
            throw new RuntimeException("Tài khoản chưa xác thực");
        }
        for (int i = 0; i < 1; i++) {
            Cart cartByUser = cartService.createCartByUser(existingUser.getUserId());
            cartByUser.setCartStatus(i);
        }
        if (existingUser.getUserStatus() == 1) {
            existingUser.setUserPhone(request.getUserPhone());
            existingUser.setUserBirthday(request.getUserBirthday());
            existingUser.setUserLastName(request.getUserLastName());
            existingUser.setUserFirstName(request.getUserFirstName());

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            String encodedPassword = passwordEncoder.encode(request.getUserPassword());
            existingUser.setUserPassword(encodedPassword);

            Role defaultRole = roleRepository.findByRoleName("USER")
                    .orElseThrow(() -> new RuntimeException("Role 'USER' không tồn tại"));
            if (!existingUser.getRoles().contains(defaultRole)) {
                existingUser.getRoles().add(defaultRole);
            }
            if (existingUser.getICard() == null) {
                IdCard emptyIdCard = new IdCard();
                existingUser.setICard(emptyIdCard);
            }
            return registerRepository.save(existingUser);
        }
        return existingUser;
    }

    @Override
    public void verify(String email, String otp) {
        User user = registerRepository.findByUserEmail(email);
        if (user == null) {
            throw new RuntimeException("Tài khoản không tồn tại");
        } else if (user.getUserStatus() == 1) {
            throw new RuntimeException("Tài khoản da duọc kích hoat");
        }
        UserOtp userOtp = userOtpRepository.findByUser(user);
        if (userOtp == null) {
            throw new RuntimeException("OTP khong ton tai");
        }
        if (otp.equals(userOtp.getOtp())) {
            if (Duration.between(userOtp.getOtpTime(), LocalDateTime.now()).compareTo(OTP_EXPIRATION_DURATION) <= 0) {
                user.setUserStatus(1);
                registerRepository.save(user);
            } else {
                throw new RuntimeException("OTP het han ");
            }
        }
        else {
            throw new RuntimeException("OTP khong dung");
        }
    }
    // Cleanup expired OTPs every 60 seconds
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void otpExpirationCleanup() {
        LocalDateTime expirationTime = LocalDateTime.now().minus(OTP_EXPIRATION_DURATION);
        userOtpRepository.deleteExpiredOtps(expirationTime);
    }
}




