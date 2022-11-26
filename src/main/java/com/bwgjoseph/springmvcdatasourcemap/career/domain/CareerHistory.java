package com.bwgjoseph.springmvcdatasourcemap.career.domain;

import com.bwgjoseph.springmvcdatasourcemap.common.References;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@TypeAlias("careerHistory")
@SuperBuilder(toBuilder = true)
@Document(collection = "career")
@NoArgsConstructor
public class CareerHistory extends References {
    @MongoId
    String id;
    String company;
    Appointment appointment;
    String duration;
    String lastDrawnSalary;
    List<String> skills;
    List<Certification> certs;
}
