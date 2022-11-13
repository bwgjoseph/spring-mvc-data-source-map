package com.bwgjoseph.springmvcdatasourcemap.career.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class CareerHistoryDTO extends ReferencesDTO {
  String id;
  String company;

  AppointmentDTO appointment;
  String duration;
  String lastDrawnSalary;
  List<String> skills;

  List<CertificationDTO> certs;
}
