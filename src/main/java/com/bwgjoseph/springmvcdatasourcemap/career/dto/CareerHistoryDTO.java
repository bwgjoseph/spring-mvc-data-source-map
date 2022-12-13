package com.bwgjoseph.springmvcdatasourcemap.career.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesToFieldDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CareerHistoryDTO {
    String id;

    @Valid
    ReferencesToFieldDTO<CompanyDTO> company;

    @Valid
    ReferencesToFieldDTO<AppointmentDTO> appointment;
    String duration;
    String lastDrawnSalary;

    List<@Valid ReferencesToFieldDTO<SkillDTO>> skills;

    //    List<@Valid CertificationToFieldDTO> certsToField;
    List<@Valid ReferencesToFieldDTO<CertificationToObjDTO>> certs;
}
