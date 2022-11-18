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

    protected abstract Set<String> getMandatoryReferences();

    protected abstract Set<String> getOptionalReferences();

    Set<String> getAllSupportedReferences() {
        Set<String> allSupportedReferences = new HashSet<>(getMandatoryReferences());
        allSupportedReferences.addAll(getOptionalReferences());
        return allSupportedReferences;
    }
}
