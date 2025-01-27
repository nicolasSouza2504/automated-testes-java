package br.com.tdd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Simple Math Operations Class Test")
public class SimpleMathOperationsTest {

    @Test
    @DisplayName("Test 1 + 1 = 2")
    public void testSum() {

        //given / Arrange
        SimpleMathOperations simpleMathOperations = new SimpleMathOperations();

        Double firstNum = 1d;
        Double secondNum = 1d;
        Double expected = 2.0;

        //when / Act
        Double result = simpleMathOperations.sum(firstNum, secondNum);

        //then / Assert
        assertEquals(expected, result, () -> "1 + 1 did not produce 2 as value");

    }

    @Test
    @DisplayName("Test 1 - 1 = 0")
    public void testSubtract() {

        // given / arrange
        SimpleMathOperations simpleMathOperations = new SimpleMathOperations();
        Double num1 = 1d;
        Double num2 = 1d;
        Double expected = 0.0;

        //when / Act
        Double result = simpleMathOperations.subtract(num1, num2);

        //then / Assert
        assertEquals(expected, result, () -> "1 - 1 did not produce 0 as value");

    }

    @Test
    @DisplayName("Test 1 * 1 = 1")
    public void testMultiply() {

        SimpleMathOperations simpleMathOperations = new SimpleMathOperations();

        Double result = simpleMathOperations.multiply(1d, 1d);
        Double expected = 1.0;

        assertEquals(expected, result, () -> "1 * 1 did not produce 1 as value");

    }

    @Test
    @DisplayName("Test 1 / 1 = 1")
    public void testDivide() {

        SimpleMathOperations simpleMathOperations = new SimpleMathOperations();

        Double result = simpleMathOperations.divide(1d, 1d);
        Double expected = 1.0;

        assertEquals(expected, result, () -> "1 / 1 did not produce 1 as value");

    }

    //test[System under test]_[Condition or state chane]_[Expected Result]
    @Test
    @DisplayName("TemplateTest")
    public void testABCD_When_XYZ_Then_Result() {
        //given / Arrange
        //when / Act
        //then / Assert
    }


    public static void main(String[] args) {
        Double test = 1 / 0.0;
        System.out.println(test);
    }
}
