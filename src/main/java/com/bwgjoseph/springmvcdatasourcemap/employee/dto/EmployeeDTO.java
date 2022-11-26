package com.bwgjoseph.springmvcdatasourcemap.employee.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import com.bwgjoseph.springmvcdatasourcemap.config.ValidReference;
import com.bwgjoseph.springmvcdatasourcemap.employee.domain.Country;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.util.Map;
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
    String nationality;
    GeolocationDTO address;

    @Override
    public Map<String, String> getFieldDTOtoFieldDOMapping() {
        return Map.of(Fields.nationality, String.format("%s.%s", Fields.nationality, Country.Fields.country));
    }

    @Override
    public boolean isAttributedToObject() {
        return false;
    }

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of(Fields.gender, Fields.nationality);
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of(Fields.employeeName);
    }

    public enum Gender {FEMALE, MALE}

}
