package com.bwgjoseph.springmvcdatasourcemap.config;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferenceDTO;
import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReferenceValidator implements ConstraintValidator<ValidReference, Object> {

    Set<String> mandatoryReferences = new HashSet<>();
    Set<String> optionalReferences = new HashSet<>();
    Set<String> supportedReferences = new HashSet<>();

    @Override
    public void initialize(ValidReference constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        mandatoryReferences = Set.of(constraintAnnotation.mandatoryRefs());
        optionalReferences = Set.of(constraintAnnotation.optionalRefs());

        supportedReferences.addAll(mandatoryReferences);
        supportedReferences.addAll(optionalReferences);
    }

    /**
     * TODO: add custom error messages
     *
     * @param dto
     * @param constraintValidatorContext
     * @return
     */

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext constraintValidatorContext) {

        ReferencesDTO refDTO = ReferencesDTO.class.cast(dto);

        return isAllSupported(refDTO.getReferences()) && isMandatoryPresent(refDTO.getReferences());
    }


    /**
     * Check if only supported references exist (i.e., both Mandatory and Optional!)
     */
    public boolean isAllSupported(List<ReferenceDTO> references) {
        List<String> referenceNotSupportedList
                = references.stream()
                .map(ReferenceDTO::getField)
                .filter(refField -> !supportedReferences.contains(refField))
                .toList();
        
        return referenceNotSupportedList.isEmpty();
    }

    /**
     * Check if mandatory references (matching content) exists
     */
    public boolean isMandatoryPresent(List<ReferenceDTO> references) {
        return true;
    }
}
