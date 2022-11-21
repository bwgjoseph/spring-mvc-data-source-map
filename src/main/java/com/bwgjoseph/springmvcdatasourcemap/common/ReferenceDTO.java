package com.bwgjoseph.springmvcdatasourcemap.common;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReferenceDTO {
  String field;
  String content;
  List<SourceDTO> sources;
}
