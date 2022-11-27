package com.bwgjoseph.springmvcdatasourcemap.employee.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import com.bwgjoseph.springmvcdatasourcemap.config.ValidReference;
import com.bwgjoseph.springmvcdatasourcemap.employee.domain.Country;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@ValidReference
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
public class EmployeeDTO extends ReferencesDTO {
  String id;
  Gender gender;
  EmployeeNameDTO employeeName;
  String bioNotes;
  String nationality;
  @Valid GeolocationDTO address;

  @Override
  public Map<String, String> getFieldDTOtoFieldDOMapping() {
    return Map.of(Fields.nationality, String.format("%s.%s", Fields.nationality, Country.Fields.country));
  }

  @Override
  public boolean isAttributedToObject() {
    return false;
  }

  @Override
  public Set<String> getMandatoryReferences() {
    return Set.of(Fields.gender, Fields.nationality);
  }

  @Override
  public Set<String> getOptionalReferences() {
    return Set.of(Fields.employeeName);
  }

  public enum Gender {FEMALE, MALE}

}
