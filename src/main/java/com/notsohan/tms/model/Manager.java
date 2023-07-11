package com.notsohan.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Manager")
@Table(
        name = "manager",
        uniqueConstraints = @UniqueConstraint(
                name = "manager_email_unique",
                columnNames = "email"
        )
)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Manager {
    @Id
    @SequenceGenerator(
            name = "manager_sequence",
            sequenceName = "manager_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "manager_sequence"
    )
    @Column(
            name = "manager_id",
            updatable = false
    )
    private Long managerId;
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
            name = "email"
    )
    private String email;
    @Column(
            name = "gender",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String gender;
    @Column(
            name = "phone_no",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String phoneNo;
    @Column(
            name = "password",
            length = 60
    )
    private String password;
    private String role = "ADMIN";

    public Manager(String firstName,
                   String lastName,
                   String email,
                   String gender,
                   String phoneNo,
                   String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.password = password;
    }
}
