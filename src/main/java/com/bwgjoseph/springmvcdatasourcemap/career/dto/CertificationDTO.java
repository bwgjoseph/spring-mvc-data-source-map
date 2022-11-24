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
public class CertificationDTO extends ReferencesDTO {
    String name;
    String issuedBy;

    @Override
    public boolean isAttributedToObject() {
        return false;
    }

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of(Fields.name, Fields.issuedBy);
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of();
    }
}
