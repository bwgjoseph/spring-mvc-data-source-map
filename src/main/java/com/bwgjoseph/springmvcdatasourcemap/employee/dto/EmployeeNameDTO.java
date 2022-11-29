package com.bwgjoseph.springmvcdatasourcemap.employee.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import com.bwgjoseph.springmvcdatasourcemap.config.ValidReference;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Builder
@ValidReference
@EqualsAndHashCode(callSuper = true)
public class EmployeeNameDTO extends ReferencesDTO {
  String firstName;
  String lastName;


  @Override
  public Set<String> getMandatoryReferences() {
    return Set.of(ATTRIBUTE_TO_OBJ);
  }

  @Override
  public Set<String> getOptionalReferences() {
    return Set.of();
  }
}
