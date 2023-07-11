package com.notsohan.tms.controller;

import com.notsohan.tms.event.RegistrationCompleteEvent;
import com.notsohan.tms.model.Tourist;
import com.notsohan.tms.model.TouristModel;
import com.notsohan.tms.model.VerificationToken;
import com.notsohan.tms.service.TouristService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(path = "/user")
public class TouristController {

    private final TouristService touristService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public TouristController(TouristService touristService,
                             ApplicationEventPublisher applicationEventPublisher){
        this.touristService = touristService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody TouristModel touristModel,
                               HttpServletRequest request){
        Tourist tourist = touristService.registerUser(touristModel);
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(
                tourist,
                applicationUrl(request)
        ));
        return "Success!";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result = touristService.validateVerificationToken(token);

        if(result.equalsIgnoreCase("valid")){
            return "User verified successfully";
        }else{
            return "Bad Token";
        }
    }

    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          HttpServletRequest request){
        VerificationToken verificationToken
                = touristService.generateNewVerificationToken(oldToken);

        Tourist tourist = verificationToken.getTourist();
        resendVerificationTokenMail(applicationUrl(request), verificationToken);

        return "Verification Link sent!";
    }

    private void resendVerificationTokenMail(String applicationUrl,
                                             VerificationToken verificationToken) {
        String url =
                applicationUrl + "verificationRegistration?token=" + verificationToken;
        log.info("Click the link to verify your account " + url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName() +
                ":" +
                request.getServerPort() +
                "/user"+
                request.getContextPath();
    }
}
