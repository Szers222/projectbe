package tdc.edu.vn.project_mobile_be.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.requests.RegisterRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.RegisterResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import tdc.edu.vn.project_mobile_be.entities.user.UserOtp;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.RegisterRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.UserOtpRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.RegisterUserService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RegisterUserServiceImp implements RegisterUserService {
    @Autowired
    @Qualifier("registerRepository")
    private final RegisterRepository registerRepository;
    @Autowired
    private final UserOtpRepository userOtpRepository;
    @Autowired
    private final EmailService emailService;

    private static final Duration OTP_EXPIRATION_DURATION = Duration.ofSeconds(30);
    private static final Duration OTP_DELETION_DURATION = Duration.ofHours(2); // OTP deletion after 2 hours

    private String generateOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }

    private void sendVerificationEmail(String email, String otp) {
        String subject = "Email Verification";
        String body = "Your verification OTP is: " + otp;
        emailService.sendEmail(email, subject, body);
    }

    private UserOtp insertOtp(User user) {
        String otp = generateOTP();
        UserOtp userOtp = UserOtp.builder()
                .user(user)
                .otp(otp)
                .otpTime(LocalDateTime.now())
                .isVerified(true)
                .build();

        return userOtpRepository.save(userOtp);
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) {
        User existingUser = registerRepository.findByUserEmail(request.getUserEmail());
        if (existingUser != null && existingUser.getUserStatus() == 1) {
            throw new RuntimeException("User already verified");
        }

        if (existingUser != null) {
            UserOtp existingUserOtp = userOtpRepository.findByUser(existingUser);
            if (existingUserOtp != null) {
                if (Duration.between(existingUserOtp.getOtpTime(), LocalDateTime.now()).compareTo(OTP_EXPIRATION_DURATION) > 0) {
                    userOtpRepository.delete(existingUserOtp);
                }
            }
        }

        User user = new User();
        user.setUserEmail(request.getUserEmail());
        user.setUserPassword(request.getUserPassword());
        user.setUserPhone(request.getUserPhone());
        user.setUserBirthday(request.getUserBirthday());
        user.setUserAddress(request.getUserAddress());
        user.setUserLastName(request.getUserLastName());
        user.setUserFirstName(request.getUserFirstName());
        user.setUserStatus(0);
        registerRepository.save(user);

        UserOtp userOtp = insertOtp(user);
        sendVerificationEmail(user.getUserEmail(), userOtp.getOtp());
        return RegisterResponseDTO.builder()
                .userEmail(user.getUserEmail())
                .build();
    }

    @Override
    public void verify(String email, String otp) {
        User user = registerRepository.findByUserEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        } else if (user.getUserStatus() == 1) {
            throw new RuntimeException("User is already verified");
        }

        UserOtp userOtp = userOtpRepository.findByUser(user);
        if (userOtp == null) {
            throw new RuntimeException("OTP not found");
        }

        if (otp.equals(userOtp.getOtp())) {
            user.setUserStatus(1);
            registerRepository.save(user);
            userOtp.setVerified(true);
            userOtpRepository.save(userOtp);
        } else {
            throw new RuntimeException("Invalid OTP");
        }
    }

    @Scheduled(fixedRate = 10000)
    public void otpVerifi() {
        LocalDateTime now = LocalDateTime.now();
        List<UserOtp> userOtps = userOtpRepository.findAll();
        for (UserOtp userOtp : userOtps) {
            if (userOtp.isVerified() && Duration.between(userOtp.getOtpTime(), now).compareTo(OTP_EXPIRATION_DURATION) > 0) {
                userOtp.setVerified(false); // Reset to false after expiration time
                userOtpRepository.save(userOtp);
            }
        }
    }

    @Scheduled(fixedRate = 10000)
    public void deleteExpiredOtpAndUser() {
        LocalDateTime now = LocalDateTime.now();
        List<UserOtp> userOtps = userOtpRepository.findAll();
        for (UserOtp userOtp : userOtps) {
            if (!userOtp.isVerified() &&
                    Duration.between(userOtp.getOtpTime(), now).compareTo(OTP_DELETION_DURATION) > 0) {
                userOtpRepository.delete(userOtp);
                User user = userOtp.getUser();
                registerRepository.delete(user);
            }
        }
    }
}

