package com.bwgjoseph.springmvcdatasourcemap.employee;

import com.bwgjoseph.springmvcdatasourcemap.employee.domain.Employee;
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
    public List<Employee> getAllRecords() {
        return employeeService.getAllRecords();
    }

    @GetMapping("/{id}")
    public Optional<Employee> getAllRecords(@PathVariable String id) {
        return employeeService.getRecordById(id);
    }

    @PostMapping
    public Employee addRecord(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeService.addRecord(employeeDTO);
    }

    @PutMapping("/{id}")
    public Employee updateRecord(@PathVariable String id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateRecord(id, employeeDTO);
    }

}
