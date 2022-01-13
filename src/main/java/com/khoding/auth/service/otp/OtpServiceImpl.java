package com.khoding.auth.service.otp;

import com.khoding.auth.domain.otp.Otp;
import com.khoding.auth.domain.otp.Status;
import com.khoding.auth.domain.user.User;
import com.khoding.auth.repository.otp.OtpRepository;
import com.khoding.auth.service.user.UserService;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepository;
    private final UserService userService;
    private final OtpSMS otpSMS;
    private static final Logger LOGGER = LoggerFactory.getLogger(OtpServiceImpl.class);
    private static final String LOGGER_PREFIX = "[OtpServiceImpl]";

    public OtpServiceImpl(OtpRepository otpRepository, UserService userService, OtpSMS otpSMS) {
        this.otpRepository = otpRepository;
        this.userService = userService;
        this.otpSMS = otpSMS;
    }

    @Override
    public OtpSMS generateOtp(User user) {
        LOGGER.info("{} Generating otp for user {}", LOGGER_PREFIX, user.getUsername());
        inValidatePrevOtps(user);
        Otp otp = Otp.of(otpGenerator().toString(), Status.PENDING_VERIFICATION, user, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10L), LocalDateTime.now());
        otpRepository.save(otp);
        return deliverOtp(otp);
    }

    @Override
    public OtpSMS deliverOtp(Otp otp) {
        LOGGER.info("{} Delivering otp to user = {}", LOGGER_PREFIX, otp.getUser().getUsername());
        LocalDateTime otpExpiryDate = otp.getDateExpiry();
        LOGGER.info("{} Otp for user ={} expires ={}", LOGGER_PREFIX, otp.getUser().getUsername(),
                otpExpiryDate);
        MessageRequest messageRequest = MessageRequest.of("Welcome beloved client. Your one time " +
                        "password for verification is " + otp.getOtp() + ". Valid until "
                        + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(otpExpiryDate) +
                        ". Thank you for banking with us. You matter most.",
                otp.getUser().getUsername());

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            OtpSMS otpSMS1 = otpSMS.deliverOtp(messageRequest);
            Map<String, String> params = new HashMap<>();
            params.put("user", otpSMS1.getUser());
            params.put("password", otpSMS1.getPassword());
            params.put("message", otpSMS1.getMessage());
            params.put("msisdn", otpSMS1.getMsisdn());
            HttpEntity<OtpSMS> otpSMSHttpEntity = new HttpEntity<OtpSMS>(otpSMS.deliverOtp(messageRequest), headers);
            ResponseEntity<SMSGatewayResponse> smsGatewayResponse = restTemplate
                    .exchange("https://secure.zss.co.zw/vportal/cnm/vsms/plain?user={user}&password={password}&message={message}&msisdn={msisdn}",
                            HttpMethod.GET, otpSMSHttpEntity, SMSGatewayResponse.class, params);
            LOGGER.info("{}  SMS sending {}", LOGGER_PREFIX, smsGatewayResponse);
        } catch (Exception exception) {
            LOGGER.error("{} catch {}", LOGGER_PREFIX, exception.getMessage());
        }
        return otpSMS.deliverOtp(messageRequest);
    }

    @Override
    public Otp verifyOtp(Otp otp) {
        LOGGER.info("{} Verifying otp for user {}", LOGGER_PREFIX, otp.getUser().getUsername());
        User user = userService.getUserByUsername(otp.getUser().getUsername());
        Otp currentOtp = getAllUserOtpOrderByDateIssuedDesc(user);
        if (currentOtp.getOtp().equals(otp.getOtp())) {
            otp.setStatus(Status.VERIFIED);
            otp.setLastmodified(LocalDateTime.now());
            otpRepository.save(otp);
            user.setVerfied(Boolean.TRUE);
            user.setLastmodified(LocalDateTime.now());
            userService.saveUser(user);
        }
        return otp;
    }

    @Override
    public Otp getAllUserOtpOrderByDateIssuedDesc(User user) {
        LOGGER.info("{} retrieving otp for user ={} in desc order", LOGGER_PREFIX, user.getUsername());
        Optional<Otp> otp = otpRepository.findAllByUserOrderByDateIssuedDesc(user)
                .stream()
                .findFirst();
        if (otp.isEmpty()) {
            return null;
        }
        return otp.get();
    }

    @Override
    public Otp saveOtp(Otp otp) {
        LOGGER.info("{} Saving otp...{}", LOGGER_PREFIX, otp);
        return otpRepository.save(otp);
    }

    private Integer otpGenerator() {
        Integer min = 100000;
        Integer max = 999999;
        Integer number = (int) (Math.random() * (max - min + 1) + min);
        return number;
    }

    private void inValidatePrevOtps(User user) {
        List<Otp> otpList = otpRepository.findAllByUser(user);
        if (!otpList.isEmpty()) {
            otpList.forEach(otp -> {
                otp.setStatus(Status.EXPIRED);
                otpRepository.save(otp);
            });
        }
    }
}
