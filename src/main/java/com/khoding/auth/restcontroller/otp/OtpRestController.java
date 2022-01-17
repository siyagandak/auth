package com.khoding.auth.restcontroller.otp;

import com.khoding.auth.domain.otp.Otp;
import com.khoding.auth.domain.otp.OtpStatus;
import com.khoding.auth.domain.user.User;
import com.khoding.auth.response.MessageResponse;
import com.khoding.auth.service.otp.OtpRequest;
import com.khoding.auth.service.otp.OtpService;
import com.khoding.auth.service.otp.VerifyOtpRequest;
import com.khoding.auth.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/api/otp/")
public class OtpRestController {
    private final OtpService otpService;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(OtpRestController.class);
    private static final String LOGGER_PREFIX = "[OtpRestController]";

    public OtpRestController(OtpService otpService, UserService userService) {
        this.otpService = otpService;
        this.userService = userService;
    }

    @PostMapping("/generate_otp")
    public ResponseEntity<?> generateOtp(@Valid @RequestBody OtpRequest otpRequest) {
        User user = userService.getUserByUsername(otpRequest.getMobileNUmber());
        LOGGER.info("{} Generate otp for user ={}", LOGGER_PREFIX,
                userService.getUserByUsername(otpRequest.getMobileNUmber()));
        if (Objects.isNull(user)) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.buildMessage("User record not found"));
        }
        return ResponseEntity.ok(otpService.generateOtp(user));
    }

    @PostMapping("/verify_otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        User user = userService.getUserByUsername(verifyOtpRequest.getMobileNumber());
        if (Objects.isNull(user)) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.buildMessage("User record not found"));
        }
        LOGGER.info("{} Verify otp for user ={}", LOGGER_PREFIX, user);

        Otp otp = otpService.getAllUserOtpOrderByDateIssuedDesc(user);
        if (Objects.isNull(otp)) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.buildMessage("No otp record found. Request otp to verify"));
        }

        if (otp.getStatus().equals(OtpStatus.PENDING_VERIFICATION)) {
            if (LocalDateTime.now().isAfter(otp.getDateExpiry())) {
                otp.setStatus(OtpStatus.EXPIRED);
                otpService.saveOtp(otp);
                return ResponseEntity
                        .badRequest()
                        .body(MessageResponse.buildMessage("Invalid Request. Otp expired"));
            }
            otpService.verifyOtp(otp);
            return ResponseEntity.ok(MessageResponse.buildMessage("Otp Verification successful"));
        }

        return ResponseEntity
                .badRequest()
                .body(MessageResponse.buildMessage("Invalid otp"));
    }
}
