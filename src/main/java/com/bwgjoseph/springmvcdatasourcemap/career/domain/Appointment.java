package com.bwgjoseph.springmvcdatasourcemap.career.domain;

import com.bwgjoseph.springmvcdatasourcemap.career.dto.AppointmentDTO;
import com.bwgjoseph.springmvcdatasourcemap.common.References;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class Appointment extends References {
  String position;
  String rank;

  @Override
  public Set<String> getSupportedReferences() {
    return Set.of(AppointmentDTO.Fields.position, AppointmentDTO.Fields.rank);
  }

}
