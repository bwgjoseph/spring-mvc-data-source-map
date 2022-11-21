package com.bwgjoseph.springmvcdatasourcemap.career.dto;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import com.bwgjoseph.springmvcdatasourcemap.config.ValidReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
@ValidReference(mandatoryRefs = {"name", "issuedBy"},
        optionalRefs = {})
public class CertificationDTO extends ReferencesDTO {
    String name;
    String issuedBy;
}
