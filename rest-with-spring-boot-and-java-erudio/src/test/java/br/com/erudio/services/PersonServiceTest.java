package br.com.erudio.services;


import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServices personServices;

    private Person person0;

    @BeforeEach
    public void setup() {

        // Given / Arrange

        person0 = new Person(
                "Leandro",
                "Costa",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");

    }

    @Test
    @DisplayName("Teste given person when save person should return person object.")
    public void testGivenPersonObjectWhenSavePersonThenReturnPersonObject() {

        //given
        when(personRepository.save(person0)).thenReturn(person0);

        // when
        Person person = personServices.create(person0);

        //then

        assertTrue(person != null);
        assertEquals(person0.getFirstName(), person.getFirstName());
        verify(personRepository).save(person0);

    }

    @Test
    @DisplayName("Teste create person when exists person with email should thrown runtime exception.")
    public void testCreatePersonWhenExistsByEmailShouldThrowRuntimeException() {

        //given
        when(personRepository.findByEmail(anyString())).thenReturn(Optional.of(new Person("Leandro", "Costa", "leandro@erudio.com.br", "Uberlândia - Minas Gerais - Brasil", "Male")));

        //when
        Executable executable = () -> personServices.create(person0);

        //then
        RuntimeException runtime = assertThrows(RuntimeException.class, executable);

        assertEquals("User Already exists", runtime.getMessage());
        verify(personRepository, never()).save(any());

    }


    @Test
    @DisplayName("Test for given people list when find all should return people list.")
    public void testForGivenPeopleListShouldReturnPeopleList() {

        //given
        when(personRepository.findAll()).thenReturn(List.of(new Person(), new Person(), new Person()));

        //when
        List people = personServices.findAll();

        //then
        assertEquals(3, people.size());

        verify(personRepository).findAll();

    }


    @Test
    @DisplayName("Test for given people list when find all should return empty list.")
    public void testForGivenPeopleListWhenFindAllShouldReturnEmptyList() {

        //given
        when(personRepository.findAll()).thenReturn(new ArrayList<>());

        //when
        List people = personServices.findAll();

        //then
        assertTrue(people.isEmpty());

        verify(personRepository).findAll();

    }

    @Test
    @DisplayName("Test given personId when find by ID should return person.")
    public void testGivenPersonIdWhenFindByIDShouldReturnPerson() {

        //given
        when(personRepository.findById(any())).thenReturn(Optional.of(person0));

        //when
        Person person = personServices.findById(0l);

        //then
        assertEquals(person0.getFirstName(), person.getFirstName());

        verify(personRepository).findById(0l);

    }

    @Test
    @DisplayName("Test given person object when update should return person with updated values.")
    public void testGivenPersonObjectWhenUpdateShouldReturnPersonWithUpdatedValues() {

        //given
        person0.setId(0l);

        when(personRepository.findById(any())).thenReturn(Optional.of(person0));
        when(personRepository.save(person0)).thenReturn(person0);

        person0.setFirstName("Updated");

        //when
        Person person = personServices.update(person0);

        //then
        assertEquals("Updated", person.getFirstName());

        verify(personRepository).findById(any());
        verify(personRepository).save(person0);

    }

    @Test
    @DisplayName("Test given person object when update should return person with updated values.")
    public void testGivenPersonObjectWhenDeleteShouldCallDeleteWithPersonObject() {

        //given
        person0.setId(0l);

        when(personRepository.findById(0l)).thenReturn(Optional.of(person0));

        //when
        personServices.delete(person0.getId());

        //then

        verify(personRepository).delete(person0);

    }


    @Test
    @DisplayName("Test delete person object when person object does not exists should throw an error.")
    public void testDeletePersonWhenPersonObjectDoesNotExistsShouldThrowError() {

        //given
        Executable executable = () -> personServices.delete(person0.getId());

        //when

        assertThrows(ResourceNotFoundException.class, executable);

        //then
        verify(personRepository, never()).delete(person0);

    }

}
