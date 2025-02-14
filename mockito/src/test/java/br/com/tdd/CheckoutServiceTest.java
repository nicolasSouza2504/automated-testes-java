package br.com.tdd;

import br.com.tdd.services.CheckoutService;
import br.com.tdd.services.PaymentProcessor;
import org.junit.Test;
import org.mockito.MockedConstruction;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

public class CheckoutServiceTest {

    @Test
    public void testMockObjectContruction() {

        try(MockedConstruction<PaymentProcessor> mockedConstruction
                    = mockConstruction(PaymentProcessor.class, (mock, context) -> {
                        when(mock.chargeCustormer()).thenReturn(BigDecimal.ONE);
                    })
        ) {

            CheckoutService checkoutService =   new CheckoutService();

            BigDecimal result = checkoutService.purchaseProduct("teste", "testes");

            assertEquals(BigDecimal.ONE, result);

        }

    }

}
