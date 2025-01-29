package br.com.tdd;

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
