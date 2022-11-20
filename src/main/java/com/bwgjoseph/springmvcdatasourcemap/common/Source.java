package com.bwgjoseph.springmvcdatasourcemap.common;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Source {
  LocalDateTime dateObtained;

  ReferenceType referenceType;
  String comment;

}
