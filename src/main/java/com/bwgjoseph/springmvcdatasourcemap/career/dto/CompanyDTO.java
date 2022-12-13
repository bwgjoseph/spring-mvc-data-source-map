package com.bwgjoseph.springmvcdatasourcemap.career.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.Referenceable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Set;

@Data
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO
        implements Referenceable {
    String company;

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of(Fields.company);
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of();
    }
}
