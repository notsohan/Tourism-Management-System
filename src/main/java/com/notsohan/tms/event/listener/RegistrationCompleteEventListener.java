package com.notsohan.tms.event.listener;

import com.notsohan.tms.event.RegistrationCompleteEvent;
import com.notsohan.tms.model.Tourist;
import com.notsohan.tms.service.TouristService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener
        implements ApplicationListener<RegistrationCompleteEvent> {

    private final TouristService touristService;

    @Autowired
    public RegistrationCompleteEventListener(TouristService touristService){
        this.touristService = touristService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        Tourist tourist = event.getTourist();
        String token = UUID.randomUUID().toString();
        touristService.saveVerificationTokenForUser(tourist, token);
        String url = event.getApplicationUrl() + "/verifyRegistration?" + token;

        log.info("Click the link to verify your account!" + url);
    }
}
