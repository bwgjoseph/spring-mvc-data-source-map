package com.bwgjoseph.springmvcdatasourcemap.employee.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import com.bwgjoseph.springmvcdatasourcemap.config.ValidReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@ValidReference(mandatoryRefs = {"gender"},
        optionalRefs = {"employeeName"})
public class EmployeeDTO extends ReferencesDTO {
    String id;
    Gender gender;
    String employeeName;
    String bioNotes;

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of("gender");
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of();
    }

    public enum Gender {FEMALE, MALE}

}
