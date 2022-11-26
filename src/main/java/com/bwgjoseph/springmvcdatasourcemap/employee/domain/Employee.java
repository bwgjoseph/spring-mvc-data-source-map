package com.bwgjoseph.springmvcdatasourcemap.employee.domain;

import com.bwgjoseph.springmvcdatasourcemap.common.References;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@TypeAlias("employee")
@SuperBuilder(toBuilder = true)
@Document(collection = "employee")
@NoArgsConstructor
public class Employee extends References {
    @MongoId
    String id;
    com.bwgjoseph.springmvcdatasourcemap.employee.dto.EmployeeDTO.Gender gender;
    String employeeName;
    String bioNotes;
    Geolocation address;
    Country nationality;
}
