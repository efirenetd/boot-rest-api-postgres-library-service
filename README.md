# Library API Sample using Spring-Boot
- [x] Spring Data, REST, Lombok
- [x] PostgresSQL 
- [x] JPA/Hibernate 
- [x] OpenAPI 3.0 
- [x] Redis
- [x] Spring Data Envers (AuditTrail)
- [x] Docker Compose
- [ ] TODO: Unit / Integration tests

### Run the service using Docker

```docker-compose up --build```

### Once keycloak is started, go to [Keycloak Admin Console](http://localhost:8080/auth/admin/master/console)

* Create the following Users and map to a specific Role
  * admin1 (ADMIN)
  * user1 (USER)
  * user2 (USER_READ)


### To test, you can use PostMan or Swagger-UI

``http://localhost:8081/swagger-ui.html``

### Stop the service

```docker-compose down```

### Clean up local Volume

``docker volume prune``

### To test, you can use PostMan or Swagger-UI

``http://localhost:8081/swagger-ui.html``

### To run integ test BookServiceTests.java, need to have Redis server on your local

###or

### Run Redis on Docker

``docker run --name library-redis -p 6379:6379 -e ALLOW_EMPTY_PASSWORD=yes -e REDIS_DISABLE_COMMANDS=FLUSHDB,FLUSHALL -d redis``

### References:
* [Spring Boot REST API Using JPA, Hibernate, MySQL Tutorial](https://javatodev.com/spring-boot-mysql)
* [Documenting a Spring REST API Using OpenAPI 3.0](https://www.baeldung.com/spring-rest-openapi-documentation)
* [springdoc-openapi](https://springdoc.org)
* [Documenting Spring Boot REST API with SpringDoc + OpenAPI 3](https://www.dariawan.com/tutorials/spring/documenting-spring-boot-rest-api-springdoc-openapi-3)
* [Jackon - Bidirectional](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)
* [Spring Data Envers](https://docs.spring.io/spring-data/envers/docs/current/reference/html/#reference)


