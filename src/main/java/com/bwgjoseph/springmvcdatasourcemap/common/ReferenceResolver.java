package com.bwgjoseph.springmvcdatasourcemap.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ReferenceResolver {
    @Autowired
    ObjectMapper objectMapper;

    /**
     * This method syncs the references of a CareerHistory by removing references which have stale content
     * <p>
     * We do it in the last step of converter after field has been mapped, and before the final build method.
     * <p>
     * TODO: Consider if it should be done elsewhere:
     * 2. Further upstream: HandlerInterceptor
     * 3. Cross-cut concert: interceptor via AOP?
     **/
    @AfterMapping
    public <T extends ReferencesDTO, S extends References.ReferencesBuilder<?, ?>> void syncReference(T baseDTO, @MappingTarget S builder) {
        List<Reference> inSyncReference = new ArrayList<>();
        List<ReferenceDTO> references = baseDTO.getReferences();

        /**
         * Check if only supported references exist (i.e., both Mandatory and Optional!)
         */
        Set<String> supportedReferenceFields = baseDTO.getAllSupportedReferences();
        List<String> referenceNotSupportedList
                = references.stream()
                .map(ReferenceDTO::getField)
                .filter(refField -> !supportedReferenceFields.contains(refField))
                .toList();

        if (!referenceNotSupportedList.isEmpty()) {
            // TODO: throw specific ReferenceException with which exact field
            throw new IllegalArgumentException(String.format("References not supported: %s", referenceNotSupportedList));
        }

        Map<String, List<String>> fieldToContentMap = new HashMap<>();

        try {
            String jsonString = objectMapper.writeValueAsString(baseDTO);

            for (ReferenceDTO ref : references) {
                String jsonPath = ref.getField();
                log.info("JSON PATH {}", jsonPath);

                if (jsonPath.contains("[*]")) {
                    List<String> contentList = JsonPath.parse(jsonString)
                            .read(String.format("$.%s", jsonPath));
                    if (contentList != null && contentList.contains(ref.getContent())) {
                        inSyncReference.add(referenceDTOtoReference(ref));

                        if (fieldToContentMap.containsKey(jsonPath)) {
                            List<String> contentReferenceList = fieldToContentMap.get(jsonPath);
                            contentReferenceList.add(ref.getContent());
                        } else {
                            fieldToContentMap.put(jsonPath, List.of(ref.getContent()));
                        }
                    }
                } else {

                    String content = JsonPath.parse(jsonString)
                            .read(String.format("$.%s", jsonPath));

                    if (content != null && content.equals(ref.getContent())) {
                        inSyncReference.add(referenceDTOtoReference(ref));
                        fieldToContentMap.put(jsonPath, List.of(ref.getContent()));
                    }
                }
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            inSyncReference = references.stream().map(this::referenceDTOtoReference).toList();
        }

        /**
         * Check if mandatory references exists whose content matches (already synced)
         *
         */
        Set<String> mandatoryReferenceFields = baseDTO.getMandatoryReferences();
        Map<String, List<Reference>> fieldToRefDTOMap = inSyncReference
                .stream()
                .filter(ref -> mandatoryReferenceFields.contains(ref.getField()))
                .collect(Collectors.groupingBy(Reference::getField));

        for (String field : mandatoryReferenceFields) {

            if (!fieldToContentMap.containsKey(field) || !fieldToRefDTOMap.containsKey(field)) {
                throw new IllegalArgumentException(String.format("Mandatory reference not present: field - %s", field));
            }

            List<String> contentList = fieldToContentMap.get(field);

            List<String> referenceContentList = fieldToRefDTOMap.get(field).stream().map(Reference::getContent).toList();


            if (contentList.size() == referenceContentList.size() && !contentList.containsAll(referenceContentList)) {
                throw new IllegalArgumentException(String.format("Mandatory reference not present: field - %s, content - %s", field, contentList));
            }
        }

        builder.references(inSyncReference);
    }

    public Reference referenceDTOtoReference(ReferenceDTO referenceDTO) {
        if (referenceDTO == null) {
            return null;
        }

        return Reference.builder()
                .field(referenceDTO.getField())
                .content(referenceDTO.getContent())
                .dateObtained(referenceDTO.getDateObtained())
                .referenceType(referenceDTO.getReferenceType())
                .comment(referenceDTO.getComment())
                .build();
    }
}
