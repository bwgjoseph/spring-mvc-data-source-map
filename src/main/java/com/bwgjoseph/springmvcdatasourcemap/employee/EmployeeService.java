package com.bwgjoseph.springmvcdatasourcemap.employee;

import com.bwgjoseph.springmvcdatasourcemap.employee.domain.Employee;
import com.bwgjoseph.springmvcdatasourcemap.employee.dto.EmployeeDTO;
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
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeDTO addRecord(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEmployee(employeeDTO);
        Employee createdEmployee = employeeRepository.save(employee);
        return employeeMapper.toEmployeeDTO(createdEmployee);
    }

    public List<EmployeeDTO> getAllRecords() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toEmployeeDTO)
                .toList();
    }

    public Optional<EmployeeDTO> getRecordById(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(employeeMapper::toEmployeeDTO);
    }

    public EmployeeDTO updateRecord(String id, EmployeeDTO newEmployeeDTO) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) throw new IllegalArgumentException("Not found");

        Employee newEmployee = employeeMapper.toEmployee(newEmployeeDTO);
        Employee createdEmployee = employeeRepository.save(newEmployee);
        return employeeMapper.toEmployeeDTO(createdEmployee);
    }
}
