package com.bwgjoseph.springmvcdatasourcemap.employee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants
public class Country {
    String country;
    String continent;

    public Country(String country) {
        this.country = country;
        this.continent = country.equals("Singapore") ? "Asia" : "Others";
    }
}
