package com.bwgjoseph.springmvcdatasourcemap.career;

import com.bwgjoseph.springmvcdatasourcemap.career.domain.CareerHistory;
import com.bwgjoseph.springmvcdatasourcemap.career.dto.CareerHistoryDTO;
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
    CareerHistoryDTO toCareerHistoryDTO(CareerHistory careerHistory);
}
