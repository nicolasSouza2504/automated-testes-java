package br.com.erudio.controllers;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonServices personServices;

    @Autowired
    ObjectMapper objectMapper;

    Person person;

    @BeforeEach
    public void setUp() {

        //Given
        this.person = new Person();

        person.setFirstName("Leandro");
        person.setLastName("Costa");
        person.setAddress("Uberlândia - Minas Gerais - Brasil");
        person.setGender("Male");
        person.setEmail("leandrocosta@gmail.com");

    }

    @Test
    @DisplayName("Test post create person when called should return created person")
    void testPostCreatePersonWhenCalledShouldReturnCreatedPerson() throws Throwable {

        // given
        when(personServices.create(any(Person.class)))
                .thenAnswer((invocation) -> {

                    Person person = invocation.getArgument(0);

                    person.setId(1L);

                    return person;

                });

        // when
        ResultActions response = mockMvc.perform(
            post("/person")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(person))
        );

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value(person.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(person.getLastName()))
                .andExpect(jsonPath("$.email").value(person.getEmail()));;

    }

    @Test
    @DisplayName("Test given findAll when called should return list of created people")
    void testGivenFindallWhenCalledShouldReturnCreatedPeople() throws Throwable {

        // given

        Integer peopleSize = 10;

        List<Person> people = new ArrayList<>();

        people.add(person);

        for (Integer i = 0; i < peopleSize; i++) {

            Person random = new Person();

            random.setFirstName("Random " + i);
            random.setLastName("Random " + i);
            random.setAddress("Uberlândia - Minas Gerais - Brasil " + i);
            random.setGender("Male");
            random.setEmail("ramdom@gmail.com");

            people.add(random);

        }

        when(personServices.findAll())
                .thenReturn(people);

        // when
        ResultActions response = mockMvc.perform(
                get("/person"));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(peopleSize + 1)))
                .andExpect(jsonPath("$.[0].firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.[1].firstName", is("Random 0")))
                .andExpect(jsonPath("$.[2].firstName", is("Random 1")));

        verify(personServices).findAll();

    }

    @Test
    @DisplayName("Test given id when findById should return people with the given id")
    void testGivenIdWhenFindByIdShouldReturnPeopleWithGivenId() throws Throwable {

        // given
        long id = 1;

        person.setId(id);

        when(personServices.findById(id))
                .thenReturn(person);

        // when
        ResultActions response = mockMvc.perform(get("/person/{id}", id));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value(person.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(person.getLastName()))
                .andExpect(jsonPath("$.email").value(person.getEmail()));

        verify(personServices).findById(id);

    }

    @Test
    @DisplayName("Test given id that does not exists when call get findById should return error ")
    void testGivenIdThatDoesNotExistsWhenCallGetFindByIdShouldReturnError() throws Throwable {

        // given
        long nonExistingId = 999;

        when(personServices.findById(nonExistingId))
                .thenThrow(new ResourceNotFoundException("No records found for this ID!"));

        // when
        ResultActions response = mockMvc.perform(get("/person/{id}", nonExistingId));

        // then
        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No records found for this ID!")));

        verify(personServices).findById(nonExistingId);

    }

    @Test
    @DisplayName("Test given updated person when call put update should return updated person")
    void testGivenUpdatedPersonWhenCallPutUpdateShouldReturnUpdatedPerson() throws Throwable {

        // given
        long id = 1;

        person.setId(id);
        person.setFirstName("Leandro Updated");
        person.setLastName("Costa Updated");
        person.setEmail("email updated");

        when(personServices.update(person))
                .thenAnswer((invocation) -> {

                    Person person = invocation.getArgument(0);

                    return person;

                });

        // when
        ResultActions response = mockMvc.perform(
                put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person))
        );

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value(person.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(person.getLastName()))
                .andExpect(jsonPath("$.email").value(person.getEmail()));

    }

    @Test
    @DisplayName("Test given updated person that does not exists when call put update should throw error person does not exists")
    void testGivenUpdatedPersonThatDoesNotExistsWhenCallPutUpdateShouldThrowError() throws Throwable {

        // given
        long id = 999;

        person.setId(id);

        when(personServices.update(person))
                .thenThrow(new ResourceNotFoundException("No records found for this ID!"));

        // when
        ResultActions response = mockMvc.perform(
                put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person))
        );

        // then
        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No records found for this ID!")));

        verify(personServices).update(person);

    }

    @Test
    @DisplayName("Test given id when call delete should delete person with given id")
    void testGivenIdWhenCallDeleteShouldDeletePersonWithGivenId() throws Throwable {

        // given
        long id = 1;

        // when
        ResultActions response = mockMvc.perform(delete("/person/{id}", id));

        // then
        response.andDo(print())
                .andExpect(status().isNoContent());

        verify(personServices).delete(id);

    }

    @Test
    @DisplayName("Test given id does not exists when call delete should throw error")
    void testGivenIdDoesNotExistsWhenCallDeleteShouldThrowError() throws Throwable {

        // given
        long id = 999;

        doThrow(new ResourceNotFoundException("No records found for this ID!")).when(personServices).delete(id);

        // when
        ResultActions response = mockMvc.perform(delete("/person/{id}", id));

        // then
        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No records found for this ID!")));

        verify(personServices).delete(id);

    }

}
