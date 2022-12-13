package com.bwgjoseph.springmvcdatasourcemap.career.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.Referenceable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Set;

@Data
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationToFieldDTO
        implements Referenceable {
    String name;
    String issuedBy;

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of(Fields.name, Fields.issuedBy);
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of();
    }
}
