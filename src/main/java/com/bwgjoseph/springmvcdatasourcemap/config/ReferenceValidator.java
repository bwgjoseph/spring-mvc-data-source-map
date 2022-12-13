package com.bwgjoseph.springmvcdatasourcemap.config;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferenceToFieldDTO;
import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesToFieldDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ReferenceValidator implements ConstraintValidator<ValidReference, Object> {
    @Autowired
    ObjectMapper objectMapper;

    Set<String> mandatoryReferences = new HashSet<>();
    Set<String> optionalReferences = new HashSet<>();
    Set<String> supportedReferences = new HashSet<>();

    @Override
    public void initialize(ValidReference constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
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

        ReferencesToFieldDTO<?> refDTO = (ReferencesToFieldDTO<?>) dto;

        mandatoryReferences = refDTO.getMandatoryReferences();
        optionalReferences = refDTO.getOptionalReferences();
        supportedReferences = refDTO.getSupportedReferences();

        boolean isAllSupported = isAllSupported(refDTO, constraintValidatorContext);
        boolean isAllMandatory = isAllMandatoryPresent(refDTO, constraintValidatorContext);

        return isAllSupported && isAllMandatory;
    }


    /**
     * Check if only supported references exist (i.e., both Mandatory and Optional!)
     */
    public boolean isAllSupported(ReferencesToFieldDTO<?> referencesDTO, ConstraintValidatorContext constraintContext) {
        List<String> referenceNotSupportedList
                = referencesDTO.getReferences().stream()
                .map(ReferenceToFieldDTO::getAppliedTo)
                .filter(refField -> !supportedReferences.contains(refField))
                .toList();

        log.error("Unsupported present {}", referenceNotSupportedList);

        if (!referenceNotSupportedList.isEmpty()) {
            String constraintValidation = String.format("Unsupported reference present for %s: %s", referencesDTO.getValue().getClass().getSimpleName(), referenceNotSupportedList);
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate(
                            constraintValidation
                    ).addPropertyNode("references")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    /**
     * Check if mandatory references exists
     */
    public boolean isAllMandatoryPresent(ReferencesToFieldDTO<?> referencesDTO, ConstraintValidatorContext constraintContext) {

        Set<String> refPresent = referencesDTO.getReferences().stream().map(ReferenceToFieldDTO::getAppliedTo).collect(Collectors.toSet());

        Set<String> mandatoryRefMissing = mandatoryReferences
                .stream()
                .filter(s -> !refPresent.contains(s))
                .collect(Collectors.toSet());

        if (!mandatoryRefMissing.isEmpty()) {
            String constraintValidation = String.format("Mandatory reference not present for %s: %s", referencesDTO.getClass().getSimpleName(), mandatoryRefMissing);
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate(
                            constraintValidation
                    ).addPropertyNode("references")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
