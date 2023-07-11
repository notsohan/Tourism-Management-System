package com.notsohan.tms.controller;

import com.notsohan.tms.model.Manager;
import com.notsohan.tms.model.ManagerModel;
import com.notsohan.tms.model.PasswordModel;
import com.notsohan.tms.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/management")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService){
        this.managerService = managerService;
    }
    @PostMapping("/register")
    public String registerUser(@RequestBody ManagerModel managerModel){
        managerService.registerUser(managerModel);
        return "Success!";
    }

    @PostMapping("/changePassword")
    public String changePassWord(@RequestBody PasswordModel passwordModel){
        Manager manager = managerService.findByEmail(passwordModel.getEmail());

        managerService.changePassword(manager, passwordModel.getNewPassword());
        return "Password Change successfully!!";
    }

}
