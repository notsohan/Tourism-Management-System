package com.notsohan.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TouristModel {
    private String firstName;
    private String lastName;
    private String email;
    private String location;
    private String gender;
    private String phoneNo;
    private String password;
    private String matchingPassword;
}
