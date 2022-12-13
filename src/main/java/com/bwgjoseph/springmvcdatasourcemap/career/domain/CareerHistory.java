package com.bwgjoseph.springmvcdatasourcemap.career.domain;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesToField;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@TypeAlias("careerHistory")
@Document(collection = "career")
@Builder
public class CareerHistory {
    @MongoId
    String id;
    ReferencesToField<Company> company;
    ReferencesToField<Appointment> appointment;
    String duration;
    String lastDrawnSalary;
    List<ReferencesToField<Skill>> skills;
    //    List<Certification> certsToField;
    List<ReferencesToField<Certification>> certs;
}
