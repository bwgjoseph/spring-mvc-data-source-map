package com.bwgjoseph.springmvcdatasourcemap.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reference {
    String field;
    String content;
    LocalDateTime dateObtained;

    ReferenceType referenceType;
    String comment;

}
