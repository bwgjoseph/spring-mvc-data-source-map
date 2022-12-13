package com.bwgjoseph.springmvcdatasourcemap.common;

import com.bwgjoseph.springmvcdatasourcemap.config.ValidReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ValidReference
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class ReferencesToFieldDTO<T extends Referenceable> {

    public static final String ATTRIBUTE_TO_OBJ = "*";

    List<ReferenceToFieldDTO> references;

    T value;

    @JsonIgnore
    public Set<String> getMandatoryReferences() {
        return value.getMandatoryReferences();
    }

    @JsonIgnore
    public Set<String> getOptionalReferences() {
        return value.getOptionalReferences();
    }

    @JsonIgnore
    public boolean isAttributedToObjectInferred() {
        Set<String> attributeToObj = Set.of(ATTRIBUTE_TO_OBJ);
        return getMandatoryReferences().equals(attributeToObj)
                || getOptionalReferences().equals(attributeToObj);
    }

    @JsonIgnore
    public Set<String> getSupportedReferences() {
        Set<String> allSupported = new HashSet<>(getOptionalReferences());
        allSupported.addAll(getMandatoryReferences());
        return allSupported;
    }
}
