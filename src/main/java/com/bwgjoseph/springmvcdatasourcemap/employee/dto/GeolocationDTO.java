package com.bwgjoseph.springmvcdatasourcemap.employee.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
public class GeolocationDTO extends ReferencesDTO {
    double longitude;
    double latitude;

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
