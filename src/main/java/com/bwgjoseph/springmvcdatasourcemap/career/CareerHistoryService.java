package com.bwgjoseph.springmvcdatasourcemap.career;

import com.bwgjoseph.springmvcdatasourcemap.career.domain.CareerHistory;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.CareerHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CareerHistoryService {

    private final CareerHistoryRepository careerHistoryRepository;
    private final CareerHistoryMapper careerHistoryMapper;

    public CareerHistory addRecord(CareerHistoryDTO careerHistoryDTO) {
        CareerHistory careerHistory = careerHistoryMapper.toCareerHistory(careerHistoryDTO);
        CareerHistory createdCareerHist = careerHistoryRepository.save(careerHistory);
        return createdCareerHist;
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
