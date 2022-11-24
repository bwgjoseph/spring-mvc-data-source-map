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
    return careerHistoryRepository.save(newCareerHistory);
  }


}
