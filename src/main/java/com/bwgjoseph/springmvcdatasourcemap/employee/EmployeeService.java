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

  public Employee addRecord(EmployeeDTO employeeDTO) {
    Employee employee = employeeMapper.toEmployee(employeeDTO);
    return employeeRepository.save(employee);
  }
  public List<Employee> getAllRecords() {
    return employeeRepository.findAll();
  }
  public Optional<Employee> getRecordById(String id) {
    return employeeRepository.findById(id);
  }

  public Employee updateRecord(String id, EmployeeDTO newEmployeeDTO) {
    Optional<Employee> employee = getRecordById(id);
    if(employee.isEmpty()) throw new IllegalArgumentException("Not found");

    Employee newEmployee = employeeMapper.toEmployee(newEmployeeDTO);
    return employeeRepository.save(newEmployee);
  }
}
