package com.bwgjoseph.springmvcdatasourcemap.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReferenceDTO {
    String field;
    String content;
    List<SourceDTO> sources;
}
