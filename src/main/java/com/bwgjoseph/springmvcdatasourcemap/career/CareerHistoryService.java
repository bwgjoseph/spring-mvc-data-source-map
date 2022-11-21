package com.bwgjoseph.springmvcdatasourcemap.career;

import com.bwgjoseph.springmvcdatasourcemap.career.domain.CareerHistory;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.CareerHistoryDTO;
import com.bwgjoseph.springmvcdatasourcemap.common.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CareerHistoryService {

  private final CareerHistoryRepository careerHistoryRepository;
  private final CareerHistoryMapper careerHistoryMapper;
  private final ObjectMapper objectMapper;

  public CareerHistory addRecord(CareerHistoryDTO careerHistoryDTO) {
    CareerHistory careerHistory = careerHistoryMapper.toCareerHistory(careerHistoryDTO);
    return careerHistoryRepository.save(careerHistory);
  }

  public List<CareerHistory> getAllRecords() {
    return careerHistoryRepository.findAll();
  }

  public Optional<CareerHistory> getRecordById(String id) {
    return careerHistoryRepository.findById(id);
  }

  public CareerHistory updateRecord(String id, CareerHistoryDTO newCareerHistoryDTO) {
    Optional<CareerHistory> careerHistory = getRecordById(id);

    if (careerHistory.isEmpty()) throw new IllegalArgumentException("Not found");

    CareerHistory newCareerHistory = careerHistoryMapper.toCareerHistory(newCareerHistoryDTO);
//    CareerHistory careerHistorySync = syncReferenceByContent(newCareerHistory);
    return careerHistoryRepository.save(newCareerHistory);
  }

  /**
   * This method validates if fields considered REQUIRED are referenced
   *
   * @return
   */
  protected boolean isReferencePresentWhenRequired() {
    return true;
  }

  /**
   * This method validates if reference.field is a field within the document.
   * <p>
   * For now, we will rely on {@link #syncReferenceByContent(CareerHistory)} to throw a  {@link com.jayway.jsonpath.PathNotFoundException } if the dot notation path is not found
   *
   * @return
   */
  protected boolean isReferenceValid() {

    // All fields in Reference
    return true;

  }

  /**
   * This method syncs the references of a CareerHistory by removing references which have stale content
   * TODO: Consider the best place to handle:
   * 1. Conversion: Mapper abstract class during conversion
   * 2. Further upstream: HandlerInterceptor
   * 3. Cross-cut concert: interceptor via AOP?
   *
   * @param careerHistory
   * @return CareerHistory containing reference list which are synced to the object
   */
  protected CareerHistory syncReferenceByContent(CareerHistory careerHistory) {
    try {
      String jsonString = objectMapper.writeValueAsString(careerHistory);

      List<Reference> references = careerHistory.getReferences();
      List<Reference> inSyncReference = new ArrayList<>();


      for (Reference ref : references) {
        String jsonPath = ref.getField();
        log.info("JSON PATH {}", jsonPath);

        if (jsonPath.contains("[*]")) {
          List<String> contentList = JsonPath.parse(jsonString)
            .read(String.format("$.%s", jsonPath));
          if (contentList != null && contentList.contains(ref.getContent())) {
            inSyncReference.add(ref);
          }
        } else {

          String content = JsonPath.parse(jsonString)
            .read(String.format("$.%s", jsonPath));

          if (content != null && content.equals(ref.getContent())) {
            inSyncReference.add(ref);
          }
        }
      }

      careerHistory.setReferences(inSyncReference);
      return careerHistory;

    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return careerHistory;
  }

}
