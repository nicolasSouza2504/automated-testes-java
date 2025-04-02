# TDD with Java

## Overview

This project demonstrates a comprehensive setup for Test-Driven Development (TDD) using Java. It includes various testing libraries and practices, focusing on TDD, Mockito for mocking, and JUnit for unit testing. The project provides examples of using spies and other testing utilities.

## Dependencies

The project uses the following dependencies:

- **JUnit Jupiter**: For writing and running tests.
- **Mockito**: For creating mock objects and verifying interactions.

### pom.xml

The `pom.xml` file includes the necessary dependencies for JUnit and Mockito:

```xml
<dependencies>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>${junit.version}</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.0.0</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

## Test-Driven Development (TDD)

TDD is a software development process where tests are written before the code. The cycle follows three steps: write a test, write code to pass the test, and refactor the code.

### Example of TDD Implementation

#### Person Class

The `Person` class represents a simple model with fields such as `id`, `firstName`, `lastName`, `address`, `gender`, and `email`.

#### PersonService Interface

The `PersonService` interface defines a method to create a person.

```java
public interface IPersonService {
    Person createPerson(Person person);
}
```

#### PersonService Implementation

The `PersonService` class implements the `IPersonService` interface and includes validation logic.

```java
public class PersonService implements IPersonService {
    @Override
    public Person createPerson(Person person) {
        if (person.getEmail() == null || person.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        return new Person(person.getFirstName(), person.getLastName(), person.getAddress(), person.getGender(), person.getEmail());
    }
}
```

### Example Unit Tests

#### PersonServiceTest Class

The `PersonServiceTest` class demonstrates writing unit tests for the `PersonService` class.

```java
import br.com.tdd.model.Person;
import br.com.tdd.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {

    private final PersonService personService = new PersonService();

    @Test
    @DisplayName("Test creating a person with valid data")
    void testCreatePersonWithValidData() {
        Person person = new Person("John", "Doe", "123 Main St", "Male", "john.doe@example.com");
        Person createdPerson = personService.createPerson(person);
        assertNotNull(createdPerson);
        assertEquals("John", createdPerson.getFirstName());
        assertEquals("Doe", createdPerson.getLastName());
        assertEquals("john.doe@example.com", createdPerson.getEmail());
    }

    @Test
    @DisplayName("Test creating a person with missing email should throw exception")
    void testCreatePersonWithMissingEmail() {
        Person person = new Person("John", "Doe", "123 Main St", "Male", null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.createPerson(person);
        });
        assertEquals("Email is required", exception.getMessage());
    }
}
```

## Mockito

Mockito is a mocking framework used for unit testing in Java. It allows you to create mock objects and define their behavior.

### Example of Mockito Usage

#### SpyTest Class

The `SpyTest` class demonstrates the usage of spies to track interactions with real objects.

```java
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SpyTest {
    @Test
    void test() {
        List<String> mockArray = spy(ArrayList.class);
        assertEquals(mockArray.size(), 0);
        when(mockArray.size()).thenReturn(5);
        mockArray.add("Test");
        assertEquals(mockArray.size(), 5);
    }

    @Test
    void testV2() {
        List<String> spyArray = spy(ArrayList.class);
        assertEquals(0, spyArray.size());
        spyArray.add("Test");
        assertEquals(1, spyArray.size());
        spyArray.add("Test");
        when(spyArray.size()).thenReturn(15);
        spyArray.add("Test");
        assertEquals(15, spyArray.size());
    }
}
```

#### OrderServiceTest Class

The `OrderServiceTest` class demonstrates the usage of `@Spy` and `MockedStatic` to mock static methods and verify interactions.

```java
import br.com.tdd.services.Order;
import br.com.tdd.services.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    Object defaultUUID = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    LocalDate defaultLocalDate = LocalDate.of(2021, 10, 10);

    @Spy
    OrderService orderService;

    @Test
    @DisplayName("Should include random ID when no order ID exists")
    void testCreateOrderShouldIncludeRandomID_When_NoOrderIDExists() {
        try (MockedStatic<UUID> mockedUUID = mockStatic(UUID.class)) {
            mockedUUID.when(UUID::randomUUID).thenReturn(defaultUUID);
            Order orderResult = orderService.createOrder("Macbook", 1L);
            assertEquals(defaultUUID.toString(), orderResult.getId().toString());
        }
    }

    @Test
    @DisplayName("Should include mock LocalDate when new order is created")
    void testCreateOrderShouldIncludeMockLocalDate_When_NewOrderIsCreated() {
        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(defaultLocalDate);
            Order orderResult = orderService.createOrder("Macbook", 1L);
            assertEquals(defaultLocalDate, orderResult.getCreationDate());
        }
    }
}
```

## JUnit

JUnit is a widely used testing framework for Java. It provides annotations to identify test methods and assertions to test the expected outcomes.

### Example of JUnit Test

The `AppTest` class demonstrates a simple JUnit test.

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
}
```

## Conclusion

This project demonstrates a comprehensive setup for Test-Driven Development (TDD) using Java. It effectively integrates various testing libraries and practices to ensure the reliability and correctness of the application. By following the instructions and examples provided in this README, you can understand and extend the testing capabilities of this project.
