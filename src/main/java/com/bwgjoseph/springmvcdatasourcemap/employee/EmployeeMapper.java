package com.bwgjoseph.springmvcdatasourcemap.employee;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferenceResolver;
import com.bwgjoseph.springmvcdatasourcemap.employee.domain.Country;
import com.bwgjoseph.springmvcdatasourcemap.employee.domain.Employee;
import com.bwgjoseph.springmvcdatasourcemap.employee.domain.Geolocation;
import com.bwgjoseph.springmvcdatasourcemap.employee.dto.EmployeeDTO;
import com.bwgjoseph.springmvcdatasourcemap.employee.dto.GeolocationDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Mapper(uses = {ReferenceResolver.class})
public abstract class EmployeeMapper {

    @Autowired
    ReferenceResolver referenceResolver;

    @Mapping(target = "references", ignore = true)
    abstract Employee toEmployee(EmployeeDTO employeeDTO);

    Geolocation map(GeolocationDTO geolocationDTO) {
        Geolocation.GeolocationBuilder<?, ?> geolocationBuilder = Geolocation.builder()
                .address(new GeoJsonPoint(geolocationDTO.getLatitude(), geolocationDTO.getLongitude()));
        referenceResolver.syncReference(geolocationDTO, geolocationBuilder);
        return geolocationBuilder.build();
    }

    GeolocationDTO map(Geolocation geolocation) {
        GeolocationDTO.GeolocationDTOBuilder<?, ?> geolocationBuilder = GeolocationDTO.builder()
                .latitude(geolocation.getAddress().getX())
                .longitude(geolocation.getAddress().getY());
        referenceResolver.syncReferenceDTO(geolocation, geolocationBuilder);
        return geolocationBuilder.build();
    }

    Country map(String country) {
        return new Country(country);
    }

    String map(Country country) {
        return country.getCountry();
    }

    @InheritInverseConfiguration
    abstract EmployeeDTO toEmployeeDTO(Employee employee);
}
