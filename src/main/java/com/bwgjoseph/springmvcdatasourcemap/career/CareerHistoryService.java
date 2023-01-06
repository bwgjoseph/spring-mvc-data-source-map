package com.bwgjoseph.springmvcdatasourcemap.career;

import com.bwgjoseph.springmvcdatasourcemap.career.domain.CareerHistory;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.CareerHistoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CareerHistoryService {

  private final CareerHistoryRepository careerHistoryRepository;
  private final CareerHistoryMapperB careerHistoryMapperB;

  public CareerHistoryDTO addRecord(CareerHistoryDTO careerHistoryDTO) {
    CareerHistory careerHistory = careerHistoryMapperB.toCareerHistory(careerHistoryDTO);
    CareerHistory careerHistoryCreated = careerHistoryRepository.save(careerHistory);
    return careerHistoryMapperB.toCareerHistoryDTO(careerHistoryCreated);
  }

  public List<CareerHistoryDTO> getAllRecords() {
    return careerHistoryRepository
      .findAll()
      .stream()
      .map(careerHistoryMapperB::toCareerHistoryDTO)
      .toList();
  }

  public Optional<CareerHistoryDTO> getRecordById(String id) {
    Optional<CareerHistory> careerHistory = careerHistoryRepository.findById(id);
    return careerHistory.map(careerHistoryMapperB::toCareerHistoryDTO);
  }

  public CareerHistoryDTO updateRecord(String id, CareerHistoryDTO newCareerHistoryDTO) {
    Optional<CareerHistory> careerHistory = careerHistoryRepository.findById(id);

    if (careerHistory.isEmpty()) throw new IllegalArgumentException("Not found");

    CareerHistory newCareerHistory = careerHistoryMapperB.toCareerHistory(newCareerHistoryDTO);
    CareerHistory createdCareerHistory = careerHistoryRepository.save(newCareerHistory);

    return careerHistoryMapperB.toCareerHistoryDTO(createdCareerHistory);
  }


}
