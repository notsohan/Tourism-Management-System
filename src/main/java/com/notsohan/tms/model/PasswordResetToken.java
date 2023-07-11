package com.notsohan.tms.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity(name = "PasswordResetToken")
@Table(name = "password_reset_token")
@Data
@NoArgsConstructor
public class PasswordResetToken {
    private static final int EXPIRATION_TIME = 10;

    @Id
    @SequenceGenerator(
            name = "passwordReset_sequence",
            sequenceName = "passwordReset_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "passwordReset_sequence"
    )
    private Long id;
    private String token;
    private Date expirationTime;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "tourist_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "tourist_password_token_fk"
            )
    )
    private Tourist tourist;

    public PasswordResetToken(Tourist tourist, String token){
        super();
        this.tourist = tourist;
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }
    public PasswordResetToken(String token){
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
