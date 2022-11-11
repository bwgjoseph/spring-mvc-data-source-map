package com.bwgjoseph.springmvcdatasourcemap.career.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferenceDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CareerHistoryDTO {
    String id;
    String company;

    AppointmentDTO appointment;
    String duration;
    String lastDrawnSalary;
    List<String> skills;

    List<CertificationDTO> certs;


    List<ReferenceDTO> references;
}
