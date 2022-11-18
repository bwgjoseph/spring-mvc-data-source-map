package com.bwgjoseph.springmvcdatasourcemap.career.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
public class AppointmentDTO extends ReferencesDTO {
    String position;
    String rank;

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of("position");
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of("rank");
    }
}
