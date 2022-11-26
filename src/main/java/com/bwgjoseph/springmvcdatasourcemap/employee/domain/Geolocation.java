package com.bwgjoseph.springmvcdatasourcemap.employee.domain;

import com.bwgjoseph.springmvcdatasourcemap.common.References;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@SuperBuilder
@Data
@NoArgsConstructor
public class Geolocation extends References {
    GeoJsonPoint address;
}
