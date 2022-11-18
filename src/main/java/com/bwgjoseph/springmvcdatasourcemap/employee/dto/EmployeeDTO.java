package com.bwgjoseph.springmvcdatasourcemap.employee.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
public class EmployeeDTO extends ReferencesDTO {
    String id;
    Gender gender;
    String employeeName;
    String bioNotes;

    @Override
    protected Set<String> getMandatoryReferences() {
        return Set.of();
    }

    @Override
    protected Set<String> getOptionalReferences() {
        return Set.of();
    }

    public enum Gender {FEMALE, MALE}
}
