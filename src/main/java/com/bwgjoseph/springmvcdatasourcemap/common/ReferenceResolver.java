package com.bwgjoseph.springmvcdatasourcemap.common;

import com.bwgjoseph.springmvcdatasourcemap.career.domain.CareerHistory;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.CareerHistoryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Mapper
@Component
@Slf4j
public class ReferenceResolver {
  @Autowired
  ObjectMapper objectMapper;

  /**
   * This method syncs the references of a CareerHistory by removing references which have stale content
   * <p>
   * We do it in the last step of converter after field has been mapped, and before the final build method.
   * <p>
   * TODO: Consider if it should be done elsewhere:
   * 2. Further upstream: HandlerInterceptor
   * 3. Cross-cut concert: interceptor via AOP?
   **/
  @AfterMapping
  public void syncReference(CareerHistoryDTO careerHistoryDTO, @MappingTarget CareerHistory.CareerHistoryBuilder careerHistoryBuilder) {
    List<Reference> inSyncReference = new ArrayList<>();
    List<ReferenceDTO> references = careerHistoryDTO.getReferences();

    try {
      String jsonString = objectMapper.writeValueAsString(careerHistoryDTO);


      for (ReferenceDTO ref : references) {
        String jsonPath = ref.getField();
        log.info("JSON PATH {}", jsonPath);

        if (jsonPath.contains("[*]")) {
          List<String> contentList = JsonPath.parse(jsonString)
            .read(String.format("$.%s", jsonPath));
          if (contentList != null && contentList.contains(ref.getContent())) {
            inSyncReference.add(referenceDTOtoReference(ref));
          }
        } else {

          String content = JsonPath.parse(jsonString)
            .read(String.format("$.%s", jsonPath));

          if (content != null && content.equals(ref.getContent())) {
            inSyncReference.add(referenceDTOtoReference(ref));
          }
        }
      }

    } catch (JsonProcessingException e) {
      e.printStackTrace();
      inSyncReference = references.stream().map(refDto -> referenceDTOtoReference(refDto)).toList();
    }

    careerHistoryBuilder.references(inSyncReference);
  }

  public Reference referenceDTOtoReference(ReferenceDTO referenceDTO) {
    if (referenceDTO == null) {
      return null;
    }

    return Reference.builder()
      .field(referenceDTO.getField())
      .content(referenceDTO.getContent())
      .dateObtained(referenceDTO.getDateObtained())
      .referenceType(referenceDTO.getReferenceType())
      .comment(referenceDTO.getComment())
      .build();
  }


}
