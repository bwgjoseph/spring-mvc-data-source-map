package com.bwgjoseph.springmvcdatasourcemap.employee.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import com.bwgjoseph.springmvcdatasourcemap.config.ValidReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@ValidReference
public class GeolocationDTO extends ReferencesDTO {
  double longitude;
  double latitude;


  @Override
  public Set<String> getMandatoryReferences() {
    return Set.of(ATTRIBUTE_TO_OBJ);
  }

  @Override
  public Set<String> getOptionalReferences() {
    return Set.of();
  }
}
