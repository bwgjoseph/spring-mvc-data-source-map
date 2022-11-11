# Spring Boot MVC Data Source Map

This project explore ways to design a model (schema) where it allows to map / attribute each field + content to a
particular data source.

## Run Project

Start `MongoDB`

```sh
cd ./.docker
docker-compose up -d
```

Run

```sh
./mvnw spring-boot:run
```

### Mongo Express

`docker compose` script will setup `mongo express` service which is a web-based UI. It is accessible
via `http://localhost:8081`

---

Project is created
via [start.spring.io](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.5&packaging=jar&jvmVersion=17&groupId=com.bwgjoseph&artifactId=spring-mvc-data-source-map&name=spring-mvc-data-source-map&description=Spring%20Boot%20MVC%20Data%20Source%20Map&packageName=com.bwgjoseph.spring-mvc-data-source-map&dependencies=devtools,lombok,configuration-processor,web,data-mongodb,validation)

## Schema

This is our schema of choice:

``` json
{ 
    company: string, 
    appointment: { 
    position: string, 
    rank: string 
 } 
    duration: string, 
    lastDrawnSalary: string, 
    skills: string[], 
    certs:[ 
        { 
     name: string 
      issuedBy: string 
    }], 
    references: [ 
        { 
            field: string, 
            content: string, 
            dateObtained: string, 
            referenceType: enum, 
            comments: string 
        } 
    ] }
```

It contains fields of the following types:

1. Single-field (`company`, `duration` , `lastDrawnSalary` )
2. Single-object (`appointment`)
3. Multi-field (`skills`)
4. Multi-object (`certs`)

## Overview

This POC aims to investigate how and where we can (1) validate and (2) sync a document's contents and its references.

- **Content** refers to all the fields
- **References** are the source of where that piece of content was obtained.

### Concepts

The explanations will be made with reference to this sample document:

```json
{
  "id": "636dfc781bdbf02672ce98d3",
  "company": "Michelin Star Restaurant",
  "appointment": {
    "position": "Chef B",
    "rank": "Professional A"
  },
  "duration": "1Y",
  "lastDrawnSalary": "4K",
  "skills": [
    "Cooking",
    "Slicing",
    "Coding"
  ],
  "certs": [
    {
      "name": "Slicing",
      "issuedBy": "Cooking School A"
    },
    {
      "name": "Cooking",
      "issuedBy": "Cooking School B"
    }
  ],
  "references": [
    {
      "field": "company",
      "content": "Michelin Star Restaurant",
      "dateObtained": "2022-11-11T12:19:54.52",
      "referenceType": "GLASSDOOR",
      "comment": "Applied via glassdoor"
    }
  ]
}
```

This document contains only one reference for the `company` field

### Biz Logic

Assume that the above document has been stored in the db. Imagine that this person started a new restaurant, and a
subsequent request is made to update the existing document:

``` json
{
{
    "id": "636dfc781bdbf02672ce98d3",
    "company": "Michelin Star Restaurant - Junior",
    "appointment": {
        "position": "Big Boss",
        "rank": "Professional A"
    },
    "duration": "1Y",
    "lastDrawnSalary": "4K",
    "skills": [
        "Cooking",
        "Slicing",
        "Coding"
    ],
    "certs": [
        {
            "name": "Cooking",
            "issuedBy": "Cooking School A"
        },
         {
            "name": "Business Entrepreneur 101",
            "issuedBy": "Linkedin"
        }
    ],
    "references": [
        {
            "field": "company",
            "content": "Michelin Star Restaurant",
            "dateObtained": "2022-11-11T12:19:54.52",
            "referenceType": "GLASSDOOR",
            "comment": "Applied via glassdoor"
        },
          {
            "field": "company",
            "content": "Michelin Star Restaurant - Junior",
            "dateObtained": "2022-11-11T12:19:54.52",
            "referenceType": "GLASSDOOR",
            "comment": "Applied via glassdoor"
        },
          {
            "field": "appointment.position",
            "content": "Big Boss",
            "dateObtained": "2022-11-11T12:19:54.52",
            "referenceType": "GLASSDOOR",
            "comment": "Applied via glassdoor"
        },
         {
            "field": "certs[*].name",
            "content": "Slicing",
            "dateObtained": "2022-11-11T12:19:54.52",
            "referenceType": "GLASSDOOR",
            "comment": "Applied via glassdoor"
        }
    ]
}
```

Notice the difference in **Content** of the document:

1. `company`: Changed from `Michelin Star Restaurant` to `Michelin Star Restaurant - Junior`
2. `appointment.position`: Changed from `Chef A` to `Big Boss`
3. `certs`:
    - Added a new cert `Business Entrepreneur 101`
    - Removed a cert `Slicing`

Notice the **Reference** that came with the request:

- Outdated references that tagged to "outdated"/"irrelevant", in particular:
    - `"field": "company" && "content": "Michelin Star Restaurant",`
    - `"field": "certs[*].name" && "content": "Slicing"`

#### Sync

That is why syncing should be done. Syncing is referred to removing outdated references, based on the current document.
If this is done appropriately, our desired response for the updating the document is as such:

```json
{
  "id": "636dfc781bdbf02672ce98d3",
  "company": "Michelin Star Restaurant - Junior",
  "appointment": {
    "position": "Big Boss",
    "rank": "Professional A"
  },
  "duration": "1Y",
  "lastDrawnSalary": "4K",
  "skills": [
    "Cooking",
    "Slicing",
    "Coding"
  ],
  "certs": [
    {
      "name": "Cooking",
      "issuedBy": "Cooking School A"
    },
    {
      "name": "Business Entrepreneur 101",
      "issuedBy": "Linkedin"
    }
  ],
  "references": [
    {
      "field": "company",
      "content": "Michelin Star Restaurant - Junior",
      "dateObtained": "2022-11-11T12:19:54.52",
      "referenceType": "GLASSDOOR",
      "comment": "Applied via glassdoor"
    },
    {
      "field": "appointment.position",
      "content": "Big Boss",
      "dateObtained": "2022-11-11T12:19:54.52",
      "referenceType": "GLASSDOOR",
      "comment": "Applied via glassdoor"
    }
  ]
}
```

#### Validate

- TBA

#### Summary

- TBA
-

## Experimentation

We aim for our implementation to be:

1. Generic - It should tailor to:
    - All data types, not just `String`
    - All schemas
2. Ease of use - Since `Reference` should be applied on almost every resource, we should:
    - Reduce code-duplication
    - Abstract it appropriately
3. Intuitive
4. Sustainable
5. (Stretch) Efficient
6. (Stretch) Easy interpretation by clients

### Logic

There are some ways how we can match the content with the references that came with it:

1. Reflection API:
    - Reflection allows an executing Java program to examine or "introspect" upon itself.
    - Potential approach: In this case, we can get the fields based on the `reference.field` during run-time
    - Cons: Since reflection allows code to perform operations that would be illegal in non-reflective code, such as
      accessing private fields and methods, the use of reflection can result in unexpected side-effects.
2. JSONPath (Current Implementation)
   - 

#### Details

The main logic to sync references with content is in our Service class

```java
class CareerHistoryService {

    /**
     * This method syncs the references of a CareerHistory by removing references which have stale content
     * @param careerHistory
     * @return CareerHistory containing reference list which are synced to the object
     */
    protected CareerHistory syncReferenceByContent(CareerHistory careerHistory) {
        try {
            String jsonString = objectMapper.writeValueAsString(careerHistory);

            List<Reference> references = careerHistory.getReferences();
            List<Reference> inSyncReference = new ArrayList<>();


            for (Reference ref : references) {
                String jsonPath = ref.getField();
                log.info("JSON PATH {}", jsonPath);

                if (jsonPath.contains("[*]")) {
                    List<String> contentList = JsonPath.parse(jsonString)
                            .read(String.format("$.%s", jsonPath));
                    if (contentList != null && contentList.contains(ref.getContent())) {
                        inSyncReference.add(ref);
                    }
                } else {

                    String content = JsonPath.parse(jsonString)
                            .read(String.format("$.%s", jsonPath));

                    if (content != null && content.equals(ref.getContent())) {
                        inSyncReference.add(ref);
                    }
                }
            }

            careerHistory.setReferences(inSyncReference);
            return careerHistory;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return careerHistory;
    }
}
```

- We convert the POJO to a JSON string using Jackson's ObjectMapper
- Iterating through `references`, we infer if it was a `List` or `String`"
    - If contains `[*]`, we infer it as a list and can do class casting accuarately
- We fetch the content of the field by parsing the JSON string and reading the content based on the JSON PathLiteral
  expression
- We define a reference as "in-Sync" and persist it if:
    - List<String>: our `reference.content` is in `field.content`
    - String: our `reference.content` == `field.content`

#### Further improvements

* TODO: Consider the best place to handle:
    *
        1. Conversion: Mapper abstract class during conversion
    *
        2. Further upstream: HandlerInterceptor, do it before deserialising?
    *
        3. Cross-cut concern: interceptor via AOP

- Any way of handling type-safety?

