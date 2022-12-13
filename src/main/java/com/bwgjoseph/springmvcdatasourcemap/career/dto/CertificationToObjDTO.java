package com.bwgjoseph.springmvcdatasourcemap.career.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.Referenceable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Set;

import static com.bwgjoseph.springmvcdatasourcemap.common.ReferencesToFieldDTO.ATTRIBUTE_TO_OBJ;

@Data
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationToObjDTO
        implements Referenceable {
    String name;
    String issuedBy;

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of(ATTRIBUTE_TO_OBJ);
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of();
    }
}
