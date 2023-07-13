package com.notsohan.tms.service;

import com.notsohan.tms.model.Guide;
import com.notsohan.tms.model.GuideModel;
import com.notsohan.tms.model.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GuideService {

    private final GuideRepository guideRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public GuideService(GuideRepository guideRepository,
                        PasswordEncoder passwordEncoder){
        this.guideRepository = guideRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(GuideModel guideModel) {
        Guide guide = new Guide();
        guide.setFirstName(guideModel.getFirstName());
        guide.setLastName(guideModel.getLastName());
        guide.setEmail(guideModel.getEmail());
        guide.setGender(guideModel.getGender());
        guide.setPhoneNo(guideModel.getPhoneNo());
        guide.setPassword(passwordEncoder.encode(guideModel.getPassword()));

        guideRepository.save(guide);
    }

    public Guide findByEmail(String email) {
        return guideRepository.findByEmail(email);
    }

    public void changePassword(Guide guide, String newPassword) {
        guide.setPassword(passwordEncoder.encode(newPassword));
        guideRepository.save(guide);
    }
}
