package com.bwgjoseph.springmvcdatasourcemap.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

public interface Referenceable {
    
    @JsonIgnore
    Set<String> getMandatoryReferences();

    @JsonIgnore
    Set<String> getOptionalReferences();

}