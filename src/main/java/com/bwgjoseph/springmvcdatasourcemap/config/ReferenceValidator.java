package com.bwgjoseph.springmvcdatasourcemap.config;

import com.bwgjoseph.springmvcdatasourcemap.common.ReferenceDTO;
import com.bwgjoseph.springmvcdatasourcemap.common.ReferencesDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    return isAllSupported(refDTO, constraintValidatorContext) && isMandatoryPresent(refDTO, constraintValidatorContext);
  }


  /**
   * Check if only supported references exist (i.e., both Mandatory and Optional!)
   */
  public boolean isAllSupported(ReferencesDTO referencesDTO, ConstraintValidatorContext constraintContext) {
    List<String> referenceNotSupportedList
      = referencesDTO.getReferences().stream()
      .map(ReferenceDTO::getField)
      .filter(refField -> !supportedReferences.contains(refField))
      .toList();

    log.error("Unsupported present {}", referenceNotSupportedList);

    return referenceNotSupportedList.isEmpty();
  }

  /**
   * Check if mandatory references (matching content) exists
   */
  public boolean isMandatoryPresent(ReferencesDTO referencesDTO, ConstraintValidatorContext constraintContext) {

    for (String mandatoryRef : mandatoryReferences) {
      Set<String> contentFromRef = referencesDTO.getContentSetByField(mandatoryRef);

      if (contentFromRef.isEmpty()) {
        constraintContext.disableDefaultConstraintViolation();
        constraintContext.buildConstraintViolationWithTemplate(
            "Reference not present {}"
          ).addPropertyNode("references")
          .addConstraintViolation();
        log.error("Reference not present {}", mandatoryRef);
        return false;
      }

      try {
        String jsonString = objectMapper.writeValueAsString(referencesDTO);
        log.info(jsonString);

        Field field = referencesDTO.getClass().getDeclaredField(mandatoryRef);

        if (field.getType().isAssignableFrom(List.class)) {
          List<String> contentList = JsonPath.parse(jsonString)
            .read(String.format("$.%s[*]", mandatoryRef));

          boolean mandatoryContentAbsentInMulti = contentList
            .stream()
            .anyMatch(content -> !contentFromRef.contains(content));

          if (mandatoryContentAbsentInMulti) {
            log.error("Reference not present {} in multi", mandatoryRef);
            return false;
          }

        } else {
          String content = JsonPath.parse(jsonString)
            .read(String.format("$.%s", mandatoryRef));

          boolean mandatoryContentAbsentInSingle = !contentFromRef.contains(content);

          if (mandatoryContentAbsentInSingle) {
            log.error("Reference not present {} in single", mandatoryRef);
            return false;
          }
        }

      } catch (JsonProcessingException | NoSuchFieldException e) {
        return false;
      }
    }
    return true;
  }
}
