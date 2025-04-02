# Mockito

## Overview

This project is part of a course designed to demonstrate the usage of Mockito for unit testing in Java. It covers mocking dependencies, using spies, and verifying interactions.

## Libraries and Tools Used

1. **JUnit 5**
    - **Annotations**:
        - `@Test`: Marks a method as a test method.
        - `@DisplayName`: Provides a custom name for the test.

2. **Mockito**
    - **Annotations**:
        - `@Mock`: Creates a mock instance.
        - `@InjectMocks`: Injects mocks into the tested instance.
        - `@Captor`: Captures argument values for further assertions.
        - `@Spy`: Creates a spy instance.
    - **Static Methods**:
        - `mock()`: Creates a mock instance of a class.
        - `spy()`: Creates a spy instance of a class.
        - `when()`: Sets up a mock's behavior.
        - `verify()`: Verifies interactions with a mock.
        - `mockStatic()`: Mocks static methods.

3. **Hamcrest**
    - Used for writing assertions in a more readable and fluent style.
    - Common matchers include `assertThat`, `is`, `equalTo`, `hasItems`, etc.

4. **Maven**
    - **Plugins**:
        - `maven-clean-plugin`: Cleans the project.
        - `maven-resources-plugin`: Handles resources.
        - `maven-compiler-plugin`: Compiles the project.
        - `maven-surefire-plugin`: Runs the tests.
        - `maven-jar-plugin`: Creates a JAR file.
        - `maven-install-plugin`: Installs the project.
        - `maven-deploy-plugin`: Deploys the project.
        - `maven-site-plugin`: Generates the project site.
        - `maven-project-info-reports-plugin`: Generates project information reports.
        - `jacoco-maven-plugin`: Generates code coverage reports.

## Practices Demonstrated

1. **Unit Testing**
    - Testing individual components in isolation to ensure they work as expected.
    - Mocking dependencies to focus on the unit under test.

2. **Mocking**
    - Creating mock objects to simulate the behavior of real objects.
    - Setting up expectations and verifying interactions.

3. **Spying**
    - Creating spies to wrap real objects and monitor their interactions.
    - Setting up expectations and verifying interactions with spies.

4. **Static Mocking**
    - Mocking static methods to control their behavior during tests.

## How to Run the Tests

1. **Unit Tests**:
    - Run with your IDE or using the command: `mvn test`.

## Example Test Cases

### Basic Mockito Example

```java
@Mock
MyService myService;

@InjectMocks
MyController myController;

@Test
public void shouldReturnData() {
    when(myService.getData()).thenReturn("Mock Data");
    String result = myController.getData();
    assertEquals("Mock Data", result);
}
```

### Spy Example

```java
@Mock
MyService myService;

@InjectMocks
MyController myController;

@Test
public void shouldReturnData() {
    when(myService.getData()).thenReturn("Mock Data");
    String result = myController.getData();
    assertEquals("Mock Data", result);
}
```

### Static Mocking Example

```java
@Test
void testMockStaticMethod() {
    try (MockedStatic<MyUtil> mockedStatic = mockStatic(MyUtil.class)) {
        mockedStatic.when(() -> MyUtil.getSomeString("Some String", true)).thenReturn("Mocked String");
        String result = MyUtil.getSomeString("Some String", true);
        assertEquals("Mocked String", result);
    }
}
```

### Hancrest Example

```java
@Test
void testHamcrestMatchers() {
    List<String> items = Arrays.asList("item1", "item2", "item3");
    assertThat(items, hasItems("item1", "item2"));
}
```

### Project Structure

```java
mockito/
        ├── pom.xml
├── src/
        │   ├── main/
        │   │   └── java/
        │   │       └── br/
        │   │           └── com/
        │   │               └── tdd/
        │   │                   ├── App.java
│   │                   └── utils/
        │   │                       └── MyUtil.java
│   └── test/
        │       └── java/
        │           └── br/
        │               └── com/
        │                   └── tdd/
        │                       ├── AppTest.java
│                       ├── ListTest.java
│                       ├── MyUtilsTest.java
│                       └── SpyTest.java
```

