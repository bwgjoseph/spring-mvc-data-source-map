package com.bwgjoseph.springmvcdatasourcemap.career.domain;

import com.bwgjoseph.springmvcdatasourcemap.common.References;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class Appointment extends References {
  String position;
  String rank;
}
