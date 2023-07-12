package com.notsohan.tms.controller;

import com.notsohan.tms.model.Guide;
import com.notsohan.tms.model.GuideModel;
import com.notsohan.tms.model.PasswordModel;
import com.notsohan.tms.service.GuideService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/guide")
public class GuideController {

    private final GuideService guideService;

    public GuideController(GuideService guideService){
        this.guideService = guideService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody GuideModel guideModel){
        guideService.registerUser(guideModel);
        return "Success!";
    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel){
        Guide guide = guideService.findByEmail(passwordModel.getEmail());

        guideService.changePassword(guide, passwordModel.getNewPassword());
        return "Password Change Successfully!!";
    }
}
