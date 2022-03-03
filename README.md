# Library API Sample using Spring-Boot
- [x] Spring Data, REST, Lombok
- [x] PostgresSQL 
- [x] JPA/Hibernate 
- [x] OpenAPI 3.0 
- [x] Redis
- [x] Docker
- [ ] TODO: Unit / Integration tests

### Run the service using Docker

```docker-compose up --build```

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

###References:
* https://javatodev.com/spring-boot-mysql/?utm_source=pocket_mylist
* https://www.baeldung.com/spring-rest-openapi-documentation
* https://springdoc.org/
* https://www.dariawan.com/tutorials/spring/documenting-spring-boot-rest-api-springdoc-openapi-3/
* https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion


