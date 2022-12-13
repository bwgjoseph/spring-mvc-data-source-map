package com.bwgjoseph.springmvcdatasourcemap.career.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Certification {
    String name;
    String issuedBy;
}
