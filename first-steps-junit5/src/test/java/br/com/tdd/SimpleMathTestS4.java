package br.com.tdd;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleMathTestS4 {

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

}
