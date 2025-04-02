# REST with Spring Boot and Java - Erudio

## Overview

This project demonstrates a comprehensive setup for RESTful APIs using Spring Boot and Java. It incorporates various testing libraries and practices, including Behavior-Driven Development (BDD), Test-Driven Development (TDD), JUnit for unit tests, Mockito for integration tests, RestAssured for API testing, and TestContainers for setting up Docker environments for tests.

## Dependencies

The project uses the following dependencies:

- **Spring Boot Starter Web**: For building web applications, including RESTful services.
- **Spring Boot Starter Data JPA**: For working with Spring Data JPA and Hibernate.
- **MySQL Connector**: For connecting to MySQL databases.
- **H2 Database**: In-memory database for testing.
- **Spring Boot Starter Validation**: For validating request parameters.
- **Spring Boot Starter Test**: For testing Spring Boot applications.
- **SpringDoc OpenAPI**: For integrating OpenAPI 3 with Spring Boot.
- **RestAssured**: For testing REST APIs.
- **TestContainers**: For running Docker containers for testing purposes.
- **Spring Boot DevTools**: For development and debugging.

## Testing Libraries and Practices

### 1. Behavior-Driven Development (BDD)

BDD is a testing approach that focuses on the behavior of the application from the perspective of its stakeholders. It uses natural language constructs to describe the expected behavior of the application.

### 2. Test-Driven Development (TDD)

TDD is a software development process where tests are written before the code. The cycle follows three steps: write a test, write code to pass the test, and refactor the code.

### 3. JUnit

JUnit is a widely used testing framework for Java. It provides annotations to identify test methods and assertions to test the expected outcomes.

#### Common JUnit Annotations:
- `@Test`: Marks a method as a test method.
- `@BeforeEach`: Executed before each test method.
- `@AfterEach`: Executed after each test method.
- `@DisplayName`: Provides a custom name for the test method.

### 4. Mockito

Mockito is a mocking framework used for unit testing in Java. It allows you to create mock objects and define their behavior.

#### Common Mockito Annotations:
- `@Mock`: Creates a mock object.
- `@InjectMocks`: Injects mock objects into the tested object.
- `@ExtendWith(MockitoExtension.class)`: Integrates Mockito with JUnit 5.

### 5. RestAssured

RestAssured is a Java library for testing RESTful web services. It provides a domain-specific language (DSL) to write tests for REST APIs.

#### Example Usage:
```java
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Test
void testGetPersonById() {
   given()
           .pathParam("id", 1)
           .when()
           .get("/person/{id}")
           .then()
           .assertThat()
           .statusCode(200)
           .body("firstName", equalTo("Leandro"));
}
```

### 6. TestContainers

TestContainers is a Java library that provides lightweight, disposable instances of common databases, Selenium web browsers, or anything else that can run in a Docker container. It is used for integration testing by starting a Docker container with the required dependencies.

#### Example Usage:
```java
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MySQLTest {

   @Container
   public MySQLContainer mysql = new MySQLContainer("mysql:8.0.28");

   @Test
   void testDatabaseConnection() {
      // Test code that uses the container
   }
}
```

### Integration Test Setup with TestContainers

The project includes integration tests that use TestContainers to set up a MySQL database in a Docker container. This ensures that the tests run in an environment that closely resembles the production environment.

#### Example Integration Test with TestContainers:
```java
package br.com.erudio.integrationtests.controller;

import br.com.erudio.config.TestConfig;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.model.Person;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonControllerIntegrationTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;
    private static Person person;

    @BeforeAll
    public void setup() {

        //Given

        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBasePath("/person")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new ResponseLoggingFilter(LogDetail.BODY))
                .build();

        this.person = new Person();

        person.setFirstName("Leandro");
        person.setLastName("Costa");
        person.setAddress("Uberlândia - Minas Gerais - Brasil");
        person.setGender("Male");
        person.setEmail("leandrocosta@gmail.com");

    }


    @Test
    @Order(1)
    @DisplayName("Integration test given a personObject when create one person should return a person object")
    void integrationTestGivenPersonObjectWhenCreateOnePersonShouldReturnAPersonObject() {

        // when
        Person createdPerson = given()
                .spec(specification)
                .contentType("application/json")
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Person.class);

        person.setId(createdPerson.getId());

        // then
        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertEquals(person.getFirstName(), createdPerson.getFirstName());
        assertEquals(person.getLastName(), createdPerson.getLastName());
        assertEquals(person.getAddress(), createdPerson.getAddress());
        assertEquals(person.getGender(), createdPerson.getGender());
        assertEquals(person.getEmail(), createdPerson.getEmail());

    }

    @Test
    @Order(2)
    @DisplayName("Integration test given a personObject when update one person should return a updated person object")
    void integrationTestGivenPersonObjectWhenUpdateOnePersonShouldReturnAUpdatedPersonObject() {

        // when
        person.setEmail("update-email");
        person.setLastName("update-last-name");


        Person updatedPerson = given()
                .spec(specification)
                .contentType("application/json")
                .body(person)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Person.class);

        // then
        assertNotNull(updatedPerson);
        assertNotNull(updatedPerson.getId());
        assertEquals(person.getFirstName(), updatedPerson.getFirstName());
        assertEquals(person.getLastName(), updatedPerson.getLastName());
        assertEquals(person.getAddress(), updatedPerson.getAddress());
        assertEquals(person.getGender(), updatedPerson.getGender());
        assertEquals(person.getEmail(), updatedPerson.getEmail());

    }

    @Test
    @Order(3)
    @DisplayName("Integration test when given a personID when findById should return a person object")
    void integrationTestWhenGivenPersonIDWhenFindByIdShouldReturnAPersonObject() {

        // when
        Person foundPerson = given()
                .spec(specification)
                .pathParam("id", person.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Person.class);

        // then
        assertNotNull(foundPerson);
        assertNotNull(foundPerson.getId());
        assertEquals(person.getFirstName(), foundPerson.getFirstName());
        assertEquals(person.getLastName(), foundPerson.getLastName());
        assertEquals(person.getAddress(), foundPerson.getAddress());
        assertEquals(person.getGender(), foundPerson.getGender());
        assertEquals(person.getEmail(), foundPerson.getEmail());

    }

    @Test
    @Order(4)
    @DisplayName("Integration test when findall should return a list of person objects")
    void integrationTestWhenFindAllShouldReturnAListOfPersonObjects() {

        // when
        Person[] people = given()
                .spec(specification)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Person[].class);

        // then
        assertNotNull(people);
        assertEquals(1, people.length);

    }

    @Test
    @Order(5)
    @DisplayName("Integration test when given a personID when delete should remove a person object")
    void integrationTestWhenGivenPersonIDWhenDeleteShouldRemoveAPersonObject() {

        // when
        given()
                .spec(specification)
                .pathParam("id", person.getId())
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);

        // then
        Person[] people = given()
                .spec(specification)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Person[].class);

        assertNotNull(people);
        assertEquals(0, people.length);

    }

}
```

#### TestContainers Configuration:
```java
package br.com.erudio.integrationtests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    public AbstractIntegrationTest() {
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static MySQLContainer mysql = new MySQLContainer("mysql:8.0.28");

        public Initializer() {
        }

        private static void startContainers() {
            Startables.deepStart(Stream.of(mysql)).join();
        }

        private static Map<String, String> createConnectionConfiguration() {

            Map<String, String> map = new HashMap<>();

            map.put("spring.datasource.url", mysql.getJdbcUrl());
            map.put("spring.datasource.username", mysql.getUsername());
            map.put("spring.datasource.password", mysql.getPassword());

            return map;

        }

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            startContainers();

            ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();

            MapPropertySource testContainers = new MapPropertySource("testcontainers", (Map) createConnectionConfiguration());

            environment.getPropertySources().addFirst(testContainers);

        }

    }

}
```

### TestContainers Dependency in `pom.xml`

```xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>mysql</artifactId>
    <version>1.19.1</version>
</dependency>
```

## Test Layers

### Unit Tests

Unit tests focus on testing individual components or methods in isolation. The project uses JUnit and Mockito for writing unit tests.

#### Example Unit Test:
```java
@Test
@DisplayName("Test given person when save person should return person object.")
void testGivenPersonObjectWhenSavePersonThenReturnPersonObject() {
    when(personRepository.save(person)).thenReturn(person);
    Person savedPerson = personService.create(person);
    assertNotNull(savedPerson);
    verify(personRepository).save(person);
}
```

### Integration Tests

Integration tests verify the interactions between different components. The project uses RestAssured for API testing and TestContainers for setting up Docker environments.

#### Example Integration Test:
```java
@Test
@DisplayName("Integration test given a personObject when create one person should return a person object")
void integrationTestGivenPersonObjectWhenCreateOnePersonShouldReturnAPersonObject() {
    Person createdPerson = given()
        .spec(specification)
        .contentType("application/json")
        .body(person)
    .when()
        .post()
    .then()
        .statusCode(200)
        .extract()
        .body()
        .as(Person.class);

    assertNotNull(createdPerson);
    assertEquals(person.getFirstName(), createdPerson.getFirstName());
}
```

### Repository Tests

Repository tests focus on testing the data access layer. The project uses JUnit for writing repository tests.

#### Example Repository Test:
```java
@Test
@DisplayName("JUnit test for Given Person Object when Save then Return Saved Person")
void testGivenPersonObject_whenSave_thenReturnSavedPerson() {
    Person savedPerson = repository.save(person);
    assertNotNull(savedPerson);
    assertTrue(savedPerson.getId() > 0);
}
```

## Conclusion

This project demonstrates a comprehensive setup for RESTful APIs using Spring Boot and Java. It effectively integrates various testing libraries and practices to ensure the reliability and correctness of the application. By following the instructions and examples provided in this README, you can understand and extend the testing capabilities of this project.
