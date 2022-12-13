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
public class SkillDTO implements Referenceable {
    String skill;

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of(Fields.skill);
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of();
    }
}
