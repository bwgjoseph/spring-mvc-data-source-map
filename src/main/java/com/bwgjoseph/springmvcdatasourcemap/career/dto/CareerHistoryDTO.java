package com.bwgjoseph.springmvcdatasourcemap.career.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

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

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of("company", "skills[*]");
    }

    @Override
    protected Set<String> getOptionalReferences() {
        return Set.of("duration");
    }
}
