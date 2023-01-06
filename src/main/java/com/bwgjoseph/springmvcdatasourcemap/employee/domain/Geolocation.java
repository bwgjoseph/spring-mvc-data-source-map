package com.bwgjoseph.springmvcdatasourcemap.employee.domain;

import com.bwgjoseph.springmvcdatasourcemap.common.References;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.Set;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Geolocation extends References {
  GeoJsonPoint address;

  @Override
  public Set<String> getSupportedReferences() {
    return Set.of("*");
  }
}
