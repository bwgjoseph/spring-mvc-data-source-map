package com.bwgjoseph.springmvcdatasourcemap.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuperBuilder
@Data
@NoArgsConstructor
public abstract class ReferencesDTO {
  List<ReferenceDTO> references;

  public Set<String> getContentSetByField(String field) {
    return references
      .stream()
      .filter(ref -> ref.getField().equals(field))
      .map(ReferenceDTO::getContent)
      .collect(Collectors.toSet());
  }

}
