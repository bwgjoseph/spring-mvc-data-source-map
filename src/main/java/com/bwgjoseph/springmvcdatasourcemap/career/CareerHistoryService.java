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
    private final CareerHistoryMapper careerHistoryMapper;

    public CareerHistoryDTO addRecord(CareerHistoryDTO careerHistoryDTO) {
        CareerHistory careerHistory = careerHistoryMapper.toCareerHistory(careerHistoryDTO);
        CareerHistory careerHistoryCreated = careerHistoryRepository.save(careerHistory);
        return careerHistoryMapper.toCareerHistoryDTO(careerHistoryCreated);
    }

    public List<CareerHistoryDTO> getAllRecords() {
        return careerHistoryRepository
                .findAll()
                .stream()
                .map(careerHistoryMapper::toCareerHistoryDTO)
                .toList();
    }

    public Optional<CareerHistoryDTO> getRecordById(String id) {
        Optional<CareerHistory> careerHistory = careerHistoryRepository.findById(id);
        return careerHistory.map(careerHistoryMapper::toCareerHistoryDTO);
    }

    public CareerHistoryDTO updateRecord(String id, CareerHistoryDTO newCareerHistoryDTO) {
        Optional<CareerHistory> careerHistory = careerHistoryRepository.findById(id);

        if (careerHistory.isEmpty()) throw new IllegalArgumentException("Not found");

        CareerHistory newCareerHistory = careerHistoryMapper.toCareerHistory(newCareerHistoryDTO);
        CareerHistory createdCareerHistory = careerHistoryRepository.save(newCareerHistory);

        return careerHistoryMapper.toCareerHistoryDTO(createdCareerHistory);
    }


}
