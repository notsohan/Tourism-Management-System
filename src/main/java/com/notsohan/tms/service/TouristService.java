package com.notsohan.tms.service;

import com.notsohan.tms.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class TouristService {

    private final TouristRepository touristRepository;
    private final PasswordEncoder passwordEncoder;
    public final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public TouristService(TouristRepository touristRepository,
                          PasswordEncoder passwordEncoder,
                          VerificationTokenRepository verificationTokenRepository){
        this.touristRepository = touristRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
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

    public void saveVerificationTokenForUser(Tourist tourist, String token) {
        VerificationToken verificationToken = new VerificationToken(tourist, token);
        verificationTokenRepository.save(verificationToken);
    }

    public String validateVerificationToken(String token) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(token);

        if(verificationToken == null){
             return "invalid";
        }

        Tourist tourist = verificationToken.getTourist();
        Calendar calendar = Calendar.getInstance();

        if((verificationToken.getExpirationTime().getTime()
                - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(verificationToken);
            return "expired";
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
}
