# SOI, How it evolved

## Problem Statement

We need to attribute each information to a source, in short, we call it Source Of Info (SOI).

### Requirement 1:

For each field in the payload, there are varying requirements:

1. SOI Mandatory:
    1. If field is present, SOI must be present
2.  SOI Optional:
    1. If a field is present, SOI can be present
3. SOI not supported:
    1. SOI must not be present

### Requirement 2:

SOI must be tagged to the current contents of the field, and not previous versions of the document. We need to ensure sync between SOI and actual content: If a SOI was tagged to a previous content of the document, we need to “drop” it.

## Principles

In modelling the SOI, the goal is for it to be:

1. Generic - It should tailor to:
    - All data types, not just `String`
    - All schemas
2. Ease of use & Sustainable - Since SOI should be applied on almost every resource, we should:
    - Reduce code-duplication
    - Abstract it appropriately
    - Reasonable amount of complexity to
        - Validate  (Requirement 1)
        - Sync (Requirement 2)
        - Transform between DTO ↔ DO
3. Intuitive
4.  Efficient
5.  Easy interpretation by clients:
    1. Easy to build
    2. Consistency in payload

## Challenges Foreseen

These are the challenges we foresee: (Take from internal network)

## Example

This is a sample document that contains different variations fields that has to be attributed:

```jsx
{
  "id": "636dfc781bdbf02672ce98d3",
  "company": "Michelin Star Restaurant",
  "appointment": {
    "position": "Chef B",
    "rank": "Professional A",
  },
  "duration": "1Y",
  "lastDrawnSalary": "4K",
  "skills": [
    "Cooking",
    "Slicing"
  ],
  "certs": [
    {
      "name": "Slicing",
      "issuedBy": "Cooking School A"
       }
    },
    {
      "name": "Cooking",
      "issuedBy": "Cooking School B",
        }
      ]
    }
  ]
} 
```

It contains fields of the following types:

1. Single-field (`company`, `duration` , `lastDrawnSalary` )
2. Single-object (`appointment`)
3. Multi-field (`skills`)
4. Multi-object (`certs`)

The following are some ideas that we have explored. The discussions are in the context of API schema, but the database schema will also be largely considered  :

## Idea #1 (JsonPath)

Largely inspired  because the payload would come in a JSON format, and our choice of of a JSON document store means that our data will also be in JSON format.

JSONPath is a query language for JSON, which uses dot notations or bracket notation to specifies a path to an element (or a set of elements) in a JSON structure. JSONPath also supports filtering arrays via Filters.

We can capitalise on using a subset JSONPath expression to indicate which element the SOI is applied to:

```json
{
  "id": "636dfc781bdbf02672ce98d3",
  "company": "Michelin Star Restaurant",
  "appointment": {
    "position": "Chef B",
    "rank": "Professional A",
  },
  "duration": "1Y",
  "lastDrawnSalary": "4K",
  "skills": [
    "Cooking",
    "Slicing"
  ],
  "certs": [
    {
      "name": "Slicing",
      "issuedBy": "Cooking School A"
       }
    },
    {
      "name": "Cooking",
      "issuedBy": "Cooking School B",
        }
      ]
    }
  ],
	"soi": [{
			"appliedTo": "$.company" // taagged to company
			"source": A
		}, {
			"appliedTo": "$.skills[?(@ == 'Cooking')]]" // tagged to Cooking Skill
			"source": A
		},
    {
			"appliedTo": "$.certs[?(@.name == 'Cooking')]]" // tagged to Cooking cert
			"source": A
		}
	]
} 
```

### Evaluation

With regards to our principles:

1. Generic
    1. 👍 JsonPath is proven to be generic enough to handle almost all types in a JSON document
2. Ease of use & Sustainable
    1. 👍  Because we may potentially offload the complexity to JSONPath libraries:
    - Validate  (Requirement 1)  ❓May need to parse `appliedTo` in order to validate Requirement 1
    - Sync (Requirement 2) 👍
    - Transform 👎 : Will definitely not be how it is stored in the db, hard to query. (Imagine asking for all certs and its’ SOI): JsonPath expression is not supported out of the box in mongodb, and will require translations in order to do so
3. Intuitive:
    1. 👎 Developers will need to be familiar with JsonPath
4.  Efficient
    1. ❓By offloading it to libraries, we are mostly unable to control the efficiency of interpreting JSON elements
5.  Easy interpretation by clients:
    1. 👎 Exposing too much of our implementation details
    2. 👎 High level of  complexity of the API contract, requiring them to apply another query language
    3. 👎 Especially for the client, how do they interpret and render the SOI and fields?

### Conclusion

Because of the complexity that is introduced to the API contract as well as the infeasibility to adopt this database schema, this idea was rejected.

However, we can still keep this at the back of our mind,  since the flexibility of JsonPath expression can help us do something so powerful as this:

```json

{
  "id": "636dfc781bdbf02672ce98d3",
  "company": "Michelin Star Restaurant",
  "appointment": {
    "position": "Chef B",
    "rank": "Professional A",
  },
  "duration": "1Y",
  "lastDrawnSalary": "4K",
  "skills": [
    "Cooking",
    "Slicing"
  ],
  "certs": [
    {
      "name": "Slicing",
      "issuedBy": "Cooking School A"
       }
    },
    {
      "name": "Cooking",
      "issuedBy": "Cooking School B",
        }
      ]
    }
  ],
	"soi": [{
			"appliedTo": "$" // taagged to all "SOI-supported" fields
			"source": A
		}, {
			"appliedTo": "$.certs" // tagged to all certs
			"source": B
		}
	]
}
```

## Idea #2 (Tag to Field + Content)

Moving from our previous idea, we realised that another way we can attribute all types of fields, is by tagging to both the field and content. Especially for this case of a list of String, we are able to attribute to the right element in the array:

```json
{
	"skills": [
	    "Cooking",
	    "Slicing"
	  ],
   "soi": [{
			"field": "skills" 
			"content": "Cooking",
			"source": A
		}
  ]
}
```

However, for the consistency of payload, it will be better to keep it consistent for all data types. Notice for embedded objects, we store the SOI at the embedded document level, so that we know which element it belongs to.

```json
{
  "id": "636dfc781bdbf02672ce98d3",
  "company": "Michelin Star Restaurant",
  "appointment": {
    "position": "Chef B",
    "rank": "Professional A",
		"soi": [{
			"field": "position" 
			"content": "Chef B",
			"source": A
		}]
  },
  "duration": "1Y",
  "lastDrawnSalary": "4K",
  "skills": [
    "Cooking",
    "Slicing"
  ],
  "certs": [
    {
      "name": "Slicing",
      "issuedBy": "Cooking School A",
			"soi": [{
				"field": "name" 
				"content": "Sicing",
				"source": A
		  }]
     }
    },
    {
      "name": "Cooking",
      "issuedBy": "Cooking School B",
        }
      ]
    }
  ],
	"soi": [{
			"field": "company" 
			"content": "Michelin Star Restaurant",
			"source": A
		}, {
			"field": "skills" 
			"content": "Cooking",
			"source": A
		}
	]
}
```

## Evaluation

With regards to our principles:

1. Generic
    1. 👍 Applicable to all data types
2. Ease of use & Sustainable
    1. 👍 Lies most closely to how the database schema and API schema ideally should be.
    - Validate  (Requirement 1) 👍
    - Sync (Requirement 2) 👍 Able to use generics
    - Transform 👍 Little transformation required
3. Intuitive:
    1. ❓It may be hard to understand why it is required to pass both field and content…?
4.  Efficient
    1. 👍 Applicable to all data types
5.  Easy interpretation by clients:
    1. 👎 Clients will require to pass in both field and content
    2. 👍  The SOI model is consistent across all fields. If the field is an embedded document, it should contain its own SOI

## Idea #3 (Wrap each field)

Because we were still uncertain with the idea of having client passing the “content” which may be considered redundant in many cases, an alternative solution was proposed.

Each wrapped field will contain the value and the SOI it was tagged to:

```json
{
    "company": {
        "value": {
            "company": "aun"
        },
        "soi": [
            {
                "appliedTo": "company",
                "source": A
            }
        ]
    },
    "appointment": {
        "value": {
            "position": "head",
            "rank": "general"
        },
        "soi": [{       
                "appliedTo": "head",
                "source": A
            }]
    },
    "skills": [
        {
            "value": {
                "skill": "marching"
            },
            "soi": [
                {
                    "appliedTo": "skill",
                     "source": A
                },
                  {
                    "appliedTo": "skill",
                    "source": A
                }
            ]
        }, 
         {
            "value": {
                "skill": "laughing"
            },
            "soi": [
                {
                    "appliedTo": "skill",
                     "source": A
                }
            ]
        }
    ],
    "certs": [
        {
            "value": {
                "name": "dancing",
                "issuedBy": "Dance school"
            },
            "soi": [
                {
                    "appliedTo": "*",
                    "source": A
                }
            ]
        },
        {
            "value": {
                "name": "Cert 2",
                "issuedBy": "School 2"
            },
            "soi": [
                {
                    "appliedTo": "*",
                    "source": A
                },
                {
                    "appliedTo": "*",
                    "source": A
                }
            ]
        }
    ]
}
```

## Evaluation

With regards to our principles:

1. Generic
    1. 👍 Applicable to all data types
2. Ease of use & Sustainable
    - Validate  (Requirement 1) 👍
    - Sync (Requirement 2) 👍
    - Transform 👎 Since our database schema will most likely be flat, similar to Idea #2, it requires promoting or demoting the SOI to the corresponding levels. This is of high complexity if we  aim to make it generic.
3. Intuitive:
    1. 👎 The payload looks complex
4.  Efficient
5.  Easy interpretation by clients
    1. 👎 The payload is complex and API consumers have to wrap each field.

## Decision

We have chosen to adopt Idea #1 until we have found a new way of approaching this.

## Implementation details

### Validation

**Requirement 1 defined in each class**

```java
@FieldNameConstants
@ValidReference
public class AppointmentDTO extends ReferencesDTO {
    String position;
    String rank;

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of(Fields.position);
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of(Fields.rank);
    }
}
```

**Validated via ConstraintValidator**

Throw exception if:

- If SOI mandatory but SOI with in-sync content not present
- If unsupported SOI is present

### Sync

Since we will be using `mapstruct` to map from DTO ↔ Domain, we used generics to be able to sync the content and SOI regardless of the data type:

```java
@AfterMapping
  public <T extends ReferencesDTO, S extends References.ReferencesBuilder<?, ?>> void syncReference(T baseDTO, @MappingTarget S builder) {
     // For each SOI, check if content matches. If no match, drop.
}
```

- To check if the content matches, we can utilise Reflection to fetch the document’s content.

### Transform

- Since DTO is most likely not going to differ from the DO, no transformation is needed.

## Further Exploration

Some further questions:

1. Can we make SOI requirement 1 easily made known to API consumers?
    1. Swagger documentations?
2. Is there a way to design the API contract such that we can simplify the payload:
    1. Multiple fields attributed: array of `appliedTo`?
    2. Some default semantics?