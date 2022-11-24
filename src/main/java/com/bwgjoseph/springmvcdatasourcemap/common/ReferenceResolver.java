package com.bwgjoseph.springmvcdatasourcemap.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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


        try {
            String jsonString = objectMapper.writeValueAsString(baseDTO);

            for (ReferenceDTO ref : references) {
                String jsonPath = ref.getField();
                log.info("JSON PATH {}", jsonPath);

                Field field = baseDTO.getClass().getDeclaredField(jsonPath);

                if (field.getType().isAssignableFrom(List.class)) {
                    List<String> contentList = JsonPath.parse(jsonString)
                            .read(String.format("$.%s[*]", jsonPath));
                    if (contentList != null && contentList.contains(ref.getContent())) {
                        inSyncReference.add(referenceDTOtoReference(ref));
                    }
                } else {

                    String content = JsonPath.parse(jsonString)
                            .read(String.format("$.%s", jsonPath));

                    if (content != null && content.equals(ref.getContent())) {
                        inSyncReference.add(referenceDTOtoReference(ref));
                    }
                }
            }

        } catch (JsonProcessingException | NoSuchFieldException e) {
            e.printStackTrace();
            inSyncReference = references.stream().map(this::referenceDTOtoReference).toList();
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
                .sources(sourceDTOListSourceList(referenceDTO.sources))
                .build();
    }

    public List<Source> sourceDTOListSourceList(List<SourceDTO> list) {
        if (list == null) {
            return null;
        } else {
            List<Source> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while (var3.hasNext()) {
                SourceDTO sourceDTO = (SourceDTO) var3.next();
                list1.add(this.map(sourceDTO));
            }

            return list1;
        }
    }

    public Source map(SourceDTO sourceDTO) {
        return Source
                .builder()
                .referenceType(sourceDTO.referenceType)
                .dateObtained(sourceDTO.dateObtained)
                .comment(sourceDTO.comment)
                .build();
    }

}
