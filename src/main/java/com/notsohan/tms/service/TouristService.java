package com.notsohan.tms.service;

import com.notsohan.tms.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class TouristService {

    private final TouristRepository touristRepository;
    private final PasswordEncoder passwordEncoder;
    public final VerificationTokenRepository verificationTokenRepository;
    public final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public TouristService(TouristRepository touristRepository,
                          PasswordEncoder passwordEncoder,
                          VerificationTokenRepository verificationTokenRepository,
                          PasswordResetTokenRepository passwordResetTokenRepository){
        this.touristRepository = touristRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }
    public Tourist registerUser(TouristModel touristModel) {
        Tourist tourist = new Tourist();
        tourist.setFirstName(touristModel.getFirstName());
        tourist.setLastName(touristModel.getLastName());
        tourist.setEmail(touristModel.getEmail());
        tourist.setLocation(touristModel.getLocation());
        tourist.setPhoneNo(touristModel.getPhoneNo());
        tourist.setGender(touristModel.getGender());
        tourist.setPassword(passwordEncoder.encode(touristModel.getPassword()));

        touristRepository.save(tourist);
        return tourist;
    }

    public void saveVerificationTokenForUser(String token, Tourist tourist){
        VerificationToken verificationToken = new VerificationToken(tourist, token);
        verificationTokenRepository.save(verificationToken);
    }

    public void saveVerificationTokenForUser(Tourist tourist, String token) {
        VerificationToken verificationToken = new VerificationToken(tourist, token);
        verificationTokenRepository.save(verificationToken);
    }

    public String validateVerificationToken(String token) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(token);

        if(verificationToken==null){
            return "invalid token";
        }

        Tourist tourist = verificationToken.getTourist();
        Calendar cal = Calendar.getInstance();
        if((verificationToken.getExpirationTime().getTime()
                - cal.getTime().getTime())<=0){
            verificationTokenRepository.delete(verificationToken);
            return"expired";
        }

        tourist.setEnable(true);
        touristRepository.save(tourist);
        return "valid";
    }

    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public Tourist findUserByEmail(String email) {
        return touristRepository.findByEmail(email);
    }

    public void createPasswordTokenForUser(Tourist tourist, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(tourist, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken =
                passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken==null){
            return "invalid";
        }
        Tourist tourist = passwordResetToken.getTourist();
        Calendar cal = Calendar.getInstance();

        if((passwordResetToken.getExpirationTime().getTime())
                - cal.getTime().getTime() <=0){
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }
        return "valid";
    }

    public Optional<Tourist> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getTourist());
    }

    public void changePassword(Tourist tourist, String newPassword) {
        tourist.setPassword(passwordEncoder.encode(newPassword));
        touristRepository.save(tourist);
    }

    public boolean checkIfValidOldPassword(Tourist tourist, String oldPassword) {
        return passwordEncoder.matches(oldPassword, tourist.getPassword());
    }
}
