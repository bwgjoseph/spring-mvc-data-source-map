package com.bwgjoseph.springmvcdatasourcemap.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuperBuilder
@Data
@NoArgsConstructor
public abstract class ReferencesDTO {

    public static final String ATTRIBUTE_TO_OBJ = "*";
    List<ReferenceDTO> references;

    public Set<String> getContentSetByField(String field) {
        return references
                .stream()
                .filter(ref -> ref.getField().equals(field))
                .map(ReferenceDTO::getContent)
                .collect(Collectors.toSet());
    }

    public abstract boolean isAttributedToObject();

    public boolean isAttributedToObjectInferred() {
        Set<String> attributeToObj = Set.of(ATTRIBUTE_TO_OBJ);
        return getMandatoryReferences().equals(attributeToObj)
                || getOptionalReferences().equals(attributeToObj);
    }

    public abstract Set<String> getMandatoryReferences();

    public abstract Set<String> getOptionalReferences();

}
