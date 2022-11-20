package com.bwgjoseph.springmvcdatasourcemap.common;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Reference {
  String field;
  String content;

  List<Source> sources;

}
