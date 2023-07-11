package com.notsohan.tms.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity(name = "VerificationToken")
@Table(name = "verification_token")
@Data
public class VerificationToken {
    private static final int EXPIRATION_TIME = 10;

    @Id
    @SequenceGenerator(
            name = "verificationToken_sequence",
            sequenceName = "verificationToken_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "verificationToken_sequence"
    )
    private Long id;
    private String token;
    private Date expirationTime;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "tourist_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "tourist_verify_token_fk"
            )
    )
    private Tourist tourist;

    public VerificationToken(){}
    public VerificationToken(Tourist tourist, String token){
        super();
        this.tourist = tourist;
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }
    public VerificationToken(String token){
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }
    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
