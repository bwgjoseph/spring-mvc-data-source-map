package com.bwgjoseph.springmvcdatasourcemap.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

public interface Referenceable {

  @JsonIgnore
  Set<String> getMandatoryReferences();

  @JsonIgnore
  Set<String> getOptionalReferences();

  @JsonIgnore
  default Set<String> getSupportedReferences() {
    Set<String> supportedSet = new HashSet<>();
    supportedSet.addAll(getMandatoryReferences());
    supportedSet.addAll(getOptionalReferences());
    return supportedSet;
  }

}
