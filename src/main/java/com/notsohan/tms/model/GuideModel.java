package com.notsohan.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideModel {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String password;
    private String matchingPassword;
    private String gender;
}
