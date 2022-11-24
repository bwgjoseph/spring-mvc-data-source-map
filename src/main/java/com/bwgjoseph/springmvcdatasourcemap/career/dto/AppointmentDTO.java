package com.bwgjoseph.springmvcdatasourcemap.career.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import com.bwgjoseph.springmvcdatasourcemap.config.ValidReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
@FieldNameConstants
@ValidReference
public class AppointmentDTO extends ReferencesDTO {
    String position;
    String rank;

    @Override
    public boolean isAttributedToObject() {
        return true;
    }

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of(ATTRIBUTE_TO_OBJ);
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of();
    }

}
