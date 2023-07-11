package com.notsohan.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ManagerModel {
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String phoneNo;
    private String password;
    private String matchingPassword;
}
