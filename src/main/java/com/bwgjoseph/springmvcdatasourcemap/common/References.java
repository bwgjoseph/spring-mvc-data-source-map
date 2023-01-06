package com.bwgjoseph.springmvcdatasourcemap.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
public abstract class References {
  List<Reference> references;

  public void addReferences(List<Reference> references) {
    if (this.references == null || this.references.isEmpty()) {
      this.references = new ArrayList<>();
    }

    this.references.addAll(references);
  }

  @JsonIgnore
  public abstract Set<String> getSupportedReferences();

}
