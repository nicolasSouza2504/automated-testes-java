package br.com.tdd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ArraysCompareTest {

    @Test
    public void testArraySort_RandomArray() {

        int[] numbers = {12, 3, 4, 1};
        int[] expected = {1, 3, 4, 12};

        Arrays.sort(numbers);

        assertArrayEquals(expected, numbers);

    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testTimeout_RandomArray() {

        int[] numbers = {12, 3, 4, 1};

        for (int i = 0; i < 1000000000; i++) {
            numbers[0] = i;
            Arrays.sort(numbers);
        }

    }
}
