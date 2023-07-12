package com.notsohan.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Guide")
@Table(name = "guide")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guide {
    @Id
    @SequenceGenerator(
            name = "guide_sequence",
            sequenceName = "guide_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "guide_sequence"
    )
    @Column(
            name = "guide_id",
            updatable = false
    )
    private Long guideId;
    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;
    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;
    @Column(
            name = "gender",
            nullable = false
    )
    private String gender;
    @Column(
            name = "phone_no",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String phoneNo;
    @Column(
            name = "email"
    )
    private String email;
    @Column(
            name = "password",
            length = 60
    )
    private String password;
    private String role = "GUIDE";

    public Guide(String firstName,
                 String lastName,
                 String gender,
                 String phoneNo,
                 String email,
                 String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
    }
}