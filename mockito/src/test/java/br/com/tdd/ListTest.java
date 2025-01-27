package br.com.tdd;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ListTest {

    @Test
    void testMockingListy_When_SizeIsCalled_ShouldReturn10() {

        List listMock = mock(List.class);

        given(listMock.size()).willReturn(10).willReturn(20);

        assertThat(10, is(listMock.size()));

        assertThat(20, is(listMock.size()));
        assertThat(20, is(listMock.size()));

    }

    @Test
    void testMockingListy_When_get0IsCalled_ShouldReturnTeste() {

        List listMock = mock(List.class);

        given(listMock.get(0)).willReturn("Teste");

        assertThat("Teste", is(listMock.get(0)));

    }

    @Test
    void testMockingListy_When_getIsCalledWithArgumentMatcher_ShouldReturnTeste() {

        List listMock = mock(List.class);

        given(listMock.get(anyInt())).willReturn("Teste");

        assertThat("Teste", is(listMock.get(0)));

        assertThat("Teste", is(listMock.get(32)));

        assertThat("Teste", is(listMock.get(43)));


    }


    @Test
    void testMockingListy_When_getIsCalled_ShouldThrowRuntimeWithMessageTeste() {

        List listMock = mock(List.class);

        when(listMock.get(anyInt())).thenThrow(new RuntimeException("Teste"));

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> listMock.get(0));

        assertThat("Teste", is(runtimeException.getMessage()));

    }

}
