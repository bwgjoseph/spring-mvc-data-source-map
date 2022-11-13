package com.bwgjoseph.springmvcdatasourcemap.employee.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class EmployeeDTO extends ReferencesDTO {
  String id;
  Gender gender;
  String employeeName;
  String bioNotes;

  public enum Gender {FEMALE, MALE}

  ;

}
