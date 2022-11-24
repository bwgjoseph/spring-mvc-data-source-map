package com.bwgjoseph.springmvcdatasourcemap.employee.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import com.bwgjoseph.springmvcdatasourcemap.config.ValidReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@ValidReference
@FieldNameConstants
public class EmployeeDTO extends ReferencesDTO {
    String id;
    Gender gender;
    String employeeName;
    String bioNotes;

    @Override
    public boolean isAttributedToObject() {
        return false;
    }

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of(Fields.gender);
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of(Fields.employeeName);
    }

    public enum Gender {FEMALE, MALE}

}
