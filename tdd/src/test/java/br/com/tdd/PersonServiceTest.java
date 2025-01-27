package br.com.tdd;

import br.com.tdd.model.Person;
import br.com.tdd.service.IPersonService;
import br.com.tdd.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {

    Person person;
    IPersonService personService;

    @BeforeEach
    public void setUp() {
        personService = new PersonService();
        this.person = new Person("John", "Doe", "Street with number", "male", "john@gmail.com");
    }


    @Test
    @DisplayName("When create a person with success should return a person object")
    public void testCreatePerson_WhenSuccess_ShouldReturnPersonObject() {

        //given / Arrange

        //when / Act
        Person actual = personService.createPerson(person);

        //then / Assert

        assertNotNull(actual, () -> {
            return "The person object should not be null";
        });

    }

    @Test
    @DisplayName("When create a person with success should contains valid fields in returned person object")
    public void testCreatePerson_WhenSuccess_ShouldContainsValidFieldsInReturnedPersonObject() {

        //given / Arrange

        //when / Act
        Person actual = personService.createPerson(person);

        //then / Assert
        assertEquals(person.getFirstName(), actual.getFirstName(), () -> "First name should be the same of the creation object");
        assertEquals(person.getLastName(), actual.getLastName(), () -> "Last name should be the same of the creation object");
        assertEquals(person.getAddress(), actual.getAddress(), () -> "Address should be the same of the creation object");
        assertEquals(person.getGender(), actual.getGender(), () -> "Gender should be the same of the creation object");
        assertEquals(person.getEmail(), actual.getEmail(), () -> "Email should be the same of the creation object");

    }

    @Test
    @DisplayName("When create a person without email should throw illegalargumentException with the message 'Email is required'")
    public void testCreatePerson_WhitoutEmail_ShouldThrowIllegalArgumentExceptionWithEmailMessage() {

        //given / Arrange
        person.setEmail(null);

        //then / Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                //when / Act
                () -> personService.createPerson(person),
                () -> "Create person without email should throw an IllegalArgumentException");

        assertEquals(exception.getMessage(), "Email is required", () -> "The exception message should be 'Email is required'");

    }

}
