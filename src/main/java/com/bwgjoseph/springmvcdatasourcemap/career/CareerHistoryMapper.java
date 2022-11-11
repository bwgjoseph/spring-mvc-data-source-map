package com.bwgjoseph.springmvcdatasourcemap.career;

import com.bwgjoseph.springmvcdatasourcemap.career.domain.Appointment;
import com.bwgjoseph.springmvcdatasourcemap.career.domain.CareerHistory;
import com.bwgjoseph.springmvcdatasourcemap.career.domain.Certification;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.AppointmentDTO;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.CareerHistoryDTO;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.CertificationDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper
public interface CareerHistoryMapper {

    /**
     * From DTO to Domain object
     *
     * @param careerHistoryDTO
     * @return domain object stored in db
     */
    CareerHistory toCareerHistory(CareerHistoryDTO careerHistoryDTO);

    /**
     * From Domain object to DTO
     *
     * @param careerHistory domain object from db
     * @return DTO object returned
     */
    @InheritInverseConfiguration
    CareerHistoryDTO toCareerHistoryrDTO(CareerHistory careerHistory);

    // Mapping inner classes
    Certification map(CertificationDTO value);

    Appointment map(AppointmentDTO value);

}
