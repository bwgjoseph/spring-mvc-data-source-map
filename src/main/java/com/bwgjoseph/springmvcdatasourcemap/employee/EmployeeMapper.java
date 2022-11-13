package com.bwgjoseph.springmvcdatasourcemap.employee;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferenceResolver;
import com.bwgjoseph.springmvcdatasourcemap.employee.domain.Employee;
import com.bwgjoseph.springmvcdatasourcemap.employee.dto.EmployeeDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ReferenceResolver.class})
public interface EmployeeMapper {

  @Mapping(target = "references", ignore = true)
  Employee toEmployee(EmployeeDTO employeeDTO);


  @InheritInverseConfiguration
  EmployeeDTO toEmployeeDTO(Employee employee);
}
