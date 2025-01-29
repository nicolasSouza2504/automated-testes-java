package br.com.tdd;

import br.com.tdd.utils.MyUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class MyUtilsTest {

    @Test
    void testMockStaticMethodWithParams() {

        try (MockedStatic<MyUtil> mockedStatic = mockStatic(MyUtil.class)) {


            mockedStatic.when(() -> MyUtil.getSomeString("Some String", true)).thenReturn("Testes Mock static");

            String testes = MyUtil.getSomeString("Some String", true);

            assertEquals("Testes Mock static", testes);


        }

    }

}
