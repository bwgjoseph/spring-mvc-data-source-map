package com.bwgjoseph.springmvcdatasourcemap.career.domain;

import com.bwgjoseph.springmvcdatasourcemap.common.Reference;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@TypeAlias("careerHistory")
@Builder
@Document(collection = "career")
public class CareerHistory {
    @MongoId
    String id;
    String company;

    Appointment appointment;
    String duration;
    String lastDrawnSalary;
    List<String> skills;


    List<Certification> certs;
    List<Reference> references;
}
