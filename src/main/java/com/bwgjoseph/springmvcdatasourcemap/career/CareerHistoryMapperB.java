package com.bwgjoseph.springmvcdatasourcemap.career;

import com.bwgjoseph.springmvcdatasourcemap.career.domain.Appointment;
import com.bwgjoseph.springmvcdatasourcemap.career.domain.CareerHistory;
import com.bwgjoseph.springmvcdatasourcemap.career.domain.Certification;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.*;

import com.bwgjoseph.springmvcdatasourcemap.common.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class CareerHistoryMapperB {
  CareerHistory toCareerHistory(CareerHistoryDTO careerHistoryDTO) {
    CareerHistory careerHistory = CareerHistory.builder()
      .appointment(toAppointment(careerHistoryDTO.getAppointment()))
      .certsToObj(toCertificates(careerHistoryDTO.getCerts()))
      .company(toCompany(careerHistoryDTO.getCompany()))
      .skills(toSkills(careerHistoryDTO.getSkills()))
      .duration(careerHistoryDTO.getDuration())
      .lastDrawnSalary(careerHistoryDTO.getLastDrawnSalary())
      .build();

    careerHistoryDTO.getSkills().forEach(s ->
      mapRefGenerically(s, careerHistory)
    );

    mapRefGenerically(careerHistoryDTO.getCompany(), careerHistory);

    return careerHistory;
  }

  <T extends Referenceable, S extends References> void mapRefGenerically(ReferencesToFieldDTO<T> s, S refernceable) {
    List<ReferenceToFieldDTO> ref = s.getReferences();
    var val = s.getValue();
    List<Reference> references = ref.stream().map(r -> {

        String appliedTo = r.getAppliedTo();
        if (appliedTo.equals("*")) {
          return this.mapRefDTOtoRef(r, r.getAppliedTo(), "*");
        }

        Class<? extends Referenceable> contentClass = val.getClass();
        log.error(contentClass.toString());
        try {
          // Terrible anti-pattern
          var contentField = contentClass.getDeclaredField(appliedTo);
          contentField.setAccessible(true);
          String content = (String) contentField.get(val);
          return this.mapRefDTOtoRef(r, r.getAppliedTo(), content);
        } catch (NoSuchFieldException | IllegalAccessException e) {
          log.error("Invalid appliedTo {} for referencedDTO {}", appliedTo, s);
          return this.mapRefDTOtoRef(r, r.getAppliedTo(), "*");
        }
      }
    ).toList();
    refernceable.addReferences(references);
  }

  List<String> toSkills(List<ReferencesToFieldDTO<SkillDTO>> skillsDTO) {
    return skillsDTO.stream().map(s -> s.getValue().getSkill()).toList();
  }

  String toCompany(ReferencesToFieldDTO<CompanyDTO> companyDTO) {
    return companyDTO.getValue().getCompany();
  }


  Appointment toAppointment(ReferencesToFieldDTO<AppointmentDTO> appointmentDTO) {
    var a = Appointment.builder()
      .rank(appointmentDTO.getValue().getRank())
      .position(appointmentDTO.getValue().getPosition())
      .build();

    mapRefGenerically(appointmentDTO, a);

    return a;
  }

  List<Certification> toCertificates(List<ReferencesToFieldDTO<CertificationToObjDTO>> certsDTO) {
    return certsDTO.stream().map(c -> {
      var certs = (Certification) Certification.builder()
        .name(c.getValue().getName())
        .issuedBy(c.getValue().getIssuedBy())
        .build();
      mapRefGenerically(c, certs);
      return certs;
    }).toList();
  }


  Reference mapRefDTOtoRef(ReferenceToFieldDTO ref, String field, String content) {
    return Reference.builder()
      .field(field)
      .content(content)
      .sources(List.of((Source.builder()
        .referenceType(ref.getReferenceType())
        .comment(ref.getComment()))
        .dateObtained(ref.getDateObtained())
        .build()))
      .build();
  }

  ReferenceToFieldDTO mapRefToRefDTO(Reference ref) {
    Source source = ref.getSources().get(0);
    return ReferenceToFieldDTO.builder()
      .dateObtained(source.getDateObtained())
      .appliedTo(ref.getField())
      .comment(source.getComment())
      .referenceType(source.getReferenceType())
      .build();
  }

  <T extends Referenceable, S extends References> ReferencesToFieldDTO<T> mapWrappedEntity(T dto, S domain) {
    List<Reference> refs = domain.getReferences();
    Set<String> supportedRefs = dto.getSupportedReferences();

    List<ReferenceToFieldDTO> filteredRefs = refs
      .stream().filter(r -> {
        String refField = r.getField();
        String refContent = r.getContent();

        try {
          if (refField.equals("*")) {
            return supportedRefs.contains(refField);
          }

          Field field = domain.getClass().getDeclaredField(refField);
          field.setAccessible(true);
          String content = (String) field.get(domain);

          return supportedRefs.contains(refField) && content.equals(refContent);

        } catch (NoSuchFieldException | IllegalAccessException e) {
          return false;
        }

      })
      .map(this::mapRefToRefDTO)
      .toList();

    return wrapEntity(dto, filteredRefs);

  }

  <T extends Referenceable> ReferencesToFieldDTO<T> wrapEntity(T dto, List<ReferenceToFieldDTO> refs) {
    return (ReferencesToFieldDTO<T>) ReferencesToFieldDTO.builder()
      .value(dto)
      .references(refs)
      .build();
  }

  ReferencesToFieldDTO<CompanyDTO> toCompanyDTO(String company, CareerHistory careerHistory) {
    CompanyDTO companyDTO = CompanyDTO.builder().company(company).build();
    return mapWrappedEntity(companyDTO, careerHistory);
  }


  List<ReferencesToFieldDTO<SkillDTO>> toSkillsDTO(List<String> skills, CareerHistory careerHistory) {
    return skills.stream().map(s -> {
      SkillDTO skillDTO = SkillDTO.builder().skill(s).build();
      Set<String> supportedRef = skillDTO.getSupportedReferences();
      List<ReferenceToFieldDTO> filteredRefs = careerHistory.getReferences()
        .stream().filter(r -> supportedRef.contains(r.getField()) && s.equals(r.getContent()))
        .map(this::mapRefToRefDTO)
        .toList();
      return wrapEntity(skillDTO, filteredRefs);
    }).toList();
  }

  ReferencesToFieldDTO<AppointmentDTO> toAppointmentDTO(Appointment appointment) {
    AppointmentDTO appointmentDTO = AppointmentDTO
      .builder()
      .position(appointment.getPosition())
      .rank(appointment.getRank())
      .build();

    return mapWrappedEntity(appointmentDTO, appointment);
  }

  List<ReferencesToFieldDTO<CertificationToObjDTO>> toCertsDTO(List<Certification> certs) {
    return certs.stream().map(c -> {

      CertificationToObjDTO certificationToObjDTO = CertificationToObjDTO.builder()
        .name(c.getName())
        .issuedBy(c.getIssuedBy())
        .build();

      return mapWrappedEntity(certificationToObjDTO, c);

    }).toList();
  }


  CareerHistoryDTO toCareerHistoryDTO(CareerHistory careerHistory) {
    return CareerHistoryDTO.builder()
      .id(careerHistory.getId())
      .company(toCompanyDTO(careerHistory.getCompany(), careerHistory))
      .appointment(toAppointmentDTO(careerHistory.getAppointment()))
      .skills(toSkillsDTO(careerHistory.getSkills(), careerHistory))
      .certs(toCertsDTO(careerHistory.getCertsToObj()))
      .duration(careerHistory.getDuration())
      .lastDrawnSalary(careerHistory.getLastDrawnSalary())
      .build();
  }

}
