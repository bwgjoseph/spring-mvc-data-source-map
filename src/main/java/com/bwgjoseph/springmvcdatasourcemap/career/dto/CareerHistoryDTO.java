package com.bwgjoseph.springmvcdatasourcemap.career.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import com.bwgjoseph.springmvcdatasourcemap.config.ValidReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Data
@FieldNameConstants
@SuperBuilder
@NoArgsConstructor
@ValidReference
public class CareerHistoryDTO extends ReferencesDTO {
    String id;
    String company;

    @Valid AppointmentDTO appointment;
    String duration;
    String lastDrawnSalary;
    List<String> skills;

    List<@Valid CertificationDTO> certs;

    @Override
    public boolean isAttributedToObject() {
        return false;
    }

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of(Fields.company, Fields.skills);
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of(Fields.duration);
    }
}
