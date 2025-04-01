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
