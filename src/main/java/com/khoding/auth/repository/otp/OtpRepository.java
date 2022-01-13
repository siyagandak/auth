package com.khoding.auth.repository.otp;

import com.khoding.auth.domain.otp.Otp;
import com.khoding.auth.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    List<Otp> findAllByUser(User user);
    List<Otp> findAllByUserOrderByDateIssuedDesc(User user);
    Otp findByUser(User user);
}
