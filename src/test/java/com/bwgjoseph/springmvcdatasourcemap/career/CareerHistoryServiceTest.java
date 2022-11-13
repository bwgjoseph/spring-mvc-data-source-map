package com.bwgjoseph.springmvcdatasourcemap.career;

import com.bwgjoseph.springmvcdatasourcemap.career.domain.CareerHistory;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.AppointmentDTO;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.CareerHistoryDTO;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.CertificationDTO;
import com.bwgjoseph.springmvcdatasourcemap.common.ReferenceDTO;
import com.bwgjoseph.springmvcdatasourcemap.common.ReferenceType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

//@Transactional
@SpringBootTest
class CareerHistoryServiceTest {
  @Autowired
  CareerHistoryService careerHistoryService;
  @Autowired
  CareerHistoryMapper careerHistoryMapper;

  @Test
  void addRecord() {

    ReferenceDTO referenceDTO = ReferenceDTO
      .builder()
      .field("company")
      .content("Michelin Star Restaurant")
      .dateObtained(LocalDateTime.now())
      .referenceType(ReferenceType.GLASSDOOR)
      .comment("Applied via glassdoor")
      .build();

    CareerHistoryDTO careerHistoryDTO = CareerHistoryDTO
      .builder()
      .company("Michelin Star Restaurant")
      .appointment(new AppointmentDTO("Chef", "Professional A"))
      .duration("1Y")
      .lastDrawnSalary("4K")
      .skills(List.of("Cooking", "Slicing", "Coding"))
      .certs(List.of(new CertificationDTO("Cooking", "Cooking School A"), new CertificationDTO("Cooking", "Cooking School B")))
      .references(List.of(referenceDTO))
      .build();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    try {
      String output = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(careerHistoryDTO);
      System.out.println(output);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    careerHistoryService.addRecord(careerHistoryDTO);

    var field = CareerHistory.builder()
      .company("ASH")
      .references(null);
    System.out.println();

  }
}
