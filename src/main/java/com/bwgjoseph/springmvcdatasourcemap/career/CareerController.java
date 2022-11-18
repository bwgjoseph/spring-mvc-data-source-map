package com.bwgjoseph.springmvcdatasourcemap.career;

import com.bwgjoseph.springmvcdatasourcemap.career.domain.CareerHistory;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.CareerHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/v1/career")
@RequiredArgsConstructor
public class CareerController {

    private final CareerHistoryService careerHistoryService;

    @GetMapping
    public List<CareerHistory> getAllRecords() {
        return careerHistoryService.getAllRecords();
    }

    @GetMapping("/{id}")
    public Optional<CareerHistory> getAllRecords(@PathVariable String id) {
        return careerHistoryService.getRecordById(id);
    }

    @PostMapping
    public CareerHistory addRecord(@RequestBody @Valid CareerHistoryDTO careerHistoryDTO) {
        return careerHistoryService.addRecord(careerHistoryDTO);
    }

    @PutMapping("/{id}")
    public CareerHistory updateRecord(@PathVariable String id, @RequestBody CareerHistoryDTO careerHistoryDTO) {
        return careerHistoryService.updateRecord(id, careerHistoryDTO);
    }

}
