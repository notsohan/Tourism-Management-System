package com.notsohan.tms.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Package")
@Table(name = "package")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Package {
    @Id
    @SequenceGenerator(
            name = "package_sequence",
            sequenceName = "package_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "package_sequence"

    )
    @Column(
            name = "package_id",
            updatable = false
    )
    private Long packageId;
    @Column(
            name = "pac_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String pacName;
    @Column(
            name = "hotel_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String hotelName;
    @Column(
            name = "cost",
            nullable = false
    )
    private int cost;
    @Column(
            name = "country",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String country;
    @Column(
            name = "location",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String location;

    public Package(String pacName,
                   String hotelName,
                   int cost,
                   String country,
                   String location) {
        this.pacName = pacName;
        this.hotelName = hotelName;
        this.cost = cost;
        this.country = country;
        this.location = location;
    }
}
