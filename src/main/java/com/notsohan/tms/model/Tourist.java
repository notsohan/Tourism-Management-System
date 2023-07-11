package com.notsohan.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Tourist")
@Table(
        name = "tourist",
        uniqueConstraints = @UniqueConstraint(
                name = "tourist_email_unique",
                columnNames = "email"
        )
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tourist {

    @Id
    @SequenceGenerator(
            name = "tourist_sequence",
            sequenceName = "tourist_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tourist_sequence"
    )
    @Column(
            name = "tourist_id",
            updatable = false
    )
    private Long touristId;
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
            name = "phone_no",
            nullable = false
    )
    private String phoneNo;
    @Column(
            name = "password",
            length = 60
    )
    private String password;
    @Column(
            name = "location",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String location;
    @Column(
            name = "gender",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String gender;
    @Column(
            name = "role",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String role = "USER";
    private boolean enable = false;

    public Tourist(String firstName,
                   String lastName,
                   String email,
                   String phoneNo,
                   String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Tourist{" +
                "touristId=" + touristId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", location='" + location + '\'' +
                ", role='" + role + '\'' +
                ", enable=" + enable +
                '}';
    }
}
