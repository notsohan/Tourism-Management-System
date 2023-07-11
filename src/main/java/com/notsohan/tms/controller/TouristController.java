package com.notsohan.tms.controller;

import com.notsohan.tms.event.RegistrationCompleteEvent;
import com.notsohan.tms.model.PasswordModel;
import com.notsohan.tms.model.Tourist;
import com.notsohan.tms.model.TouristModel;
import com.notsohan.tms.model.VerificationToken;
import com.notsohan.tms.service.TouristService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

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
            return "Bad user!";
        }
    }

    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          HttpServletRequest request
    ){
        VerificationToken verificationToken
                = touristService.generateNewVerificationToken(oldToken);

        Tourist tourist = verificationToken.getTourist();
        resendVerificationTokenMail(tourist, applicationUrl(request), verificationToken);

        return "Verification Link sent!";
    }

    private void resendVerificationTokenMail(Tourist tourist,
                                             String applicationUrl,
                                             VerificationToken verificationToken) {
        String url =
                applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();
        log.info("Click the link to verify your account! " + url);
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel,
                                HttpServletRequest request){
        Tourist tourist = touristService.findUserByEmail(passwordModel.getEmail());

        String url = "";
        if(tourist!=null){
            String token = UUID.randomUUID().toString();
            touristService.createPasswordTokenForUser(tourist, token);
            url = passwordResetTokenMail(tourist, applicationUrl(request), token);
        }
        return url;
    }

    private String passwordResetTokenMail(Tourist tourist, String applicationUrl, String token) {
        String url =
                applicationUrl
                        + "/savePassword?token="
                        + token;
        log.info("Click the link to Reset your password: "+ url);
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordModel passwordModel){
        String result = touristService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")){
            return "Invalid Token";
        }
        Optional<Tourist> user = touristService.getUserByPasswordResetToken(token);
        if(user.isPresent()){
            touristService.changePassword(user.get(), passwordModel.getNewPassword());
            return "Password Reset Successfully!";
        }else{
            return "Invalid Token";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel){
        Tourist tourist = touristService.findUserByEmail(passwordModel.getEmail());
        if(!touristService.checkIfValidOldPassword(tourist, passwordModel.getOldPassword())) {
            return "Invalid Old Password";
        }
        touristService.changePassword(tourist, passwordModel.getNewPassword());
        return "Password change successfully!";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName() +
                ":" +
                request.getServerPort() +
                "/user" +
                request.getContextPath();
    }
}
