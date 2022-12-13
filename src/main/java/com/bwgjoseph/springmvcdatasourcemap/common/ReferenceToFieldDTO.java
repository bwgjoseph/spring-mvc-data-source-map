package com.bwgjoseph.springmvcdatasourcemap.common;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReferenceToFieldDTO {
    String appliedTo;
    LocalDateTime dateObtained;
    ReferenceType referenceType;
    String comment;
}
