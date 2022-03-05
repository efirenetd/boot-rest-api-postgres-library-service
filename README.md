# Library API Sample using Spring-Boot
- [x] Spring Data, REST, Lombok
- [x] PostgresSQL 
- [x] JPA/Hibernate 
- [x] OpenAPI 3.0 
- [x] Docker
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


###References:
* https://javatodev.com/spring-boot-mysql/?utm_source=pocket_mylist
* https://www.baeldung.com/spring-rest-openapi-documentation
* https://springdoc.org/
* https://www.dariawan.com/tutorials/spring/documenting-spring-boot-rest-api-springdoc-openapi-3/
* https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion


