package com.bwgjoseph.springmvcdatasourcemap.employee;

import com.bwgjoseph.springmvcdatasourcemap.employee.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
