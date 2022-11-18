package com.bwgjoseph.springmvcdatasourcemap.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Data
@NoArgsConstructor
public abstract class ReferencesDTO {
    List<ReferenceDTO> references;

    public abstract Set<String> getMandatoryReferences();

    public abstract Set<String> getOptionalReferences();

    public Set<String> getAllSupportedReferences() {
        Set<String> allSupportedReferences = new HashSet<>(getMandatoryReferences());
        allSupportedReferences.addAll(getOptionalReferences());
        return allSupportedReferences;
    }

}
