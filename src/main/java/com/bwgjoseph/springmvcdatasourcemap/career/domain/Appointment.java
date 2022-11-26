package com.bwgjoseph.springmvcdatasourcemap.career.domain;

import com.bwgjoseph.springmvcdatasourcemap.common.References;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class Appointment extends References {
    String position;
    String rank;
}
