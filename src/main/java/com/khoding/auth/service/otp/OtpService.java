package com.khoding.auth.service.otp;

import com.khoding.auth.domain.otp.Otp;
import com.khoding.auth.domain.user.User;

public interface OtpService {
    OtpSMS generateOtp(User user);

    Otp verifyOtp(Otp otp);

    Otp saveOtp(Otp otp);

    OtpSMS deliverOtp(Otp otp);

    Otp getAllUserOtpOrderByDateIssuedDesc(User user);
}
