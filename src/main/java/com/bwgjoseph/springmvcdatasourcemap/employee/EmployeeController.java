package com.bwgjoseph.springmvcdatasourcemap.employee;

import com.bwgjoseph.springmvcdatasourcemap.employee.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDTO> getAllRecords() {
        return employeeService.getAllRecords();
    }

    @GetMapping("/{id}")
    public Optional<EmployeeDTO> getAllRecords(@PathVariable String id) {
        return employeeService.getRecordById(id);
    }

    @PostMapping
    public EmployeeDTO addRecord(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeService.addRecord(employeeDTO);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateRecord(@PathVariable String id, @RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeService.updateRecord(id, employeeDTO);
    }

}
