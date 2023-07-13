package com.notsohan.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageModel {
    private String pacName;
    private String hotelName;
    private int cost;
    private String location;
    private String country;
}
