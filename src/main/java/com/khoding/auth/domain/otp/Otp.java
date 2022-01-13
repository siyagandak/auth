package com.khoding.auth.domain.otp;

import com.khoding.auth.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(name = "otp_token")
    private String otp;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @ManyToOne
    private User user;
    @Column(name = "date_issued")
    private LocalDateTime dateIssued;
    @Column(name = "date_expiry")
    private LocalDateTime dateExpiry;
    @Column(name = "last_modified")
    private LocalDateTime lastmodified;

    public Otp() {
    }

    public Otp(String otp, Status status, User user, LocalDateTime dateIssued,
               LocalDateTime dateExpiry, LocalDateTime lastmodified) {
        this.otp = otp;
        this.status = status;
        this.user = user;
        this.dateIssued = dateIssued;
        this.dateExpiry = dateExpiry;
        this.lastmodified = lastmodified;
    }

    public static Otp of(String otp, Status status, User user, LocalDateTime dateIssued,
                         LocalDateTime dateExpiry, LocalDateTime lastmodified) {
        return new Otp(otp, status, user, dateIssued, dateExpiry, lastmodified);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDateTime dateIssued) {
        this.dateIssued = dateIssued;
    }

    public LocalDateTime getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(LocalDateTime dateExpiry) {
        this.dateExpiry = dateExpiry;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(LocalDateTime lastmodified) {
        this.lastmodified = lastmodified;
    }

    @Override
    public String toString() {
        return "Otp{" +
                "id=" + id +
                ", status=" + status +
                ", user=" + user +
                ", dateIssued=" + dateIssued +
                ", dateExpiry=" + dateExpiry +
                ", lastmodified=" + lastmodified +
                '}';
    }
}
