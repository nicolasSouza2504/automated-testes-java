# first-steps-junit5

## Overview

This project is part of a course designed to demonstrate the basic usage of JUnit 5 for testing in Java. It covers fundamental unit testing concepts and practices.

## Libraries and Tools Used

1. **JUnit 5**
    - **Annotations**:
        - `@Test`: Marks a method as a test method.
        - `@BeforeEach`: Executed before each test.
        - `@AfterEach`: Executed after each test.
        - `@BeforeAll`: Executed once before all tests.
        - `@AfterAll`: Executed once after all tests.
        - `@DisplayName`: Provides a custom name for the test.

2. **Maven**
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

## Practices Demonstrated

1. **Unit Testing**
    - Testing individual components in isolation to ensure they work as expected.

## How to Run the Tests

1. **Unit Tests**:
    - Run with your IDE or using the command: `mvn test`.

## Example Test Cases

### JUnit Example

```java
@Test
@DisplayName("Test 1 + 1 = 2")
public void testSum() {
    // Arrange
    SimpleMathOperations simpleMathOperations = new SimpleMathOperations();
    Double firstNum = 1d;
    Double secondNum = 1d;
    Double expected = 2.0;
    
    // Act
    Double result = simpleMathOperations.sum(firstNum, secondNum);
    
    // Assert
    assertEquals(expected, result, () -> "1 + 1 did not produce 2 as value");
}
```



### TIMEOUT Example

```java
@Test
@Timeout(value = 1, unit = TimeUnit.SECONDS)
public void testTimeout_RandomArray() {
    int[] numbers = {12, 3, 4, 1};

    for (int i = 0; i < 1000000000; i++) {
        numbers[0] = i;
        Arrays.sort(numbers);
    }
}

```

### Parameterized Test Example

```java
@ParameterizedTest
@DisplayName("Test 1 / 1 = 1")
@MethodSource("testeDivideParameters")
public void testDivide(Double firstNum, Double secondNum, Double expectedResult) {
    SimpleMathOperations simpleMathOperations = new SimpleMathOperations();
    Double result = simpleMathOperations.divide(firstNum, secondNum);
    assertEquals(expectedResult, result, 2d, () -> "1 / 1 did not produce 1 as value");
}

public static Stream<Arguments> testeDivideParameters() {
    return Stream.of(
            Arguments.of(1d, 1d, 1d),
            Arguments.of(2d, 2d, 1d),
            Arguments.of(3d, 3d, 1d),
            Arguments.of(4d, 4d, 1d),
            Arguments.of(5d, 5d, 1d)
    );
}
```

