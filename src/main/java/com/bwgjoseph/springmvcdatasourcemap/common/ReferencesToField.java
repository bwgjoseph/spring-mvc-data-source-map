package com.bwgjoseph.springmvcdatasourcemap.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ReferencesToField<T> {
    T value;
    List<ReferenceToField> references;
}
