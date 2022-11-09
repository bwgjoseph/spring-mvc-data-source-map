# Spring Boot MVC Data Source Map

This project explore ways to design a model (schema) where it allows to map / attribute each field + content to a particular data source.

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

`docker compose` script will setup `mongo express` service which is a web-based UI. It is accessible via `http://localhost:8081`

---

Project is created via [start.spring.io](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.5&packaging=jar&jvmVersion=17&groupId=com.bwgjoseph&artifactId=spring-mvc-data-source-map&name=spring-mvc-data-source-map&description=Spring%20Boot%20MVC%20Data%20Source%20Map&packageName=com.bwgjoseph.spring-mvc-data-source-map&dependencies=devtools,lombok,configuration-processor,web,data-mongodb,validation)

