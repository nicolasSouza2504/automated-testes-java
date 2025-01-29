package br.com.tdd;

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
    void testCreateOrderShouldIncludRandomID_When_NoOrderIDExists() {
        // Given
        try (MockedStatic<UUID> mockedUUID = mockStatic(UUID.class)) {

            mockedUUID.when(UUID::randomUUID).thenReturn(defaultUUID);

            Order orderResult = orderService.createOrder("Macbook", 1L);

            assertEquals(defaultUUID.toString(), orderResult.getId().toString());

        }
        //When

    }

    @Test
    @DisplayName("Should include mock LocalDate when new order is created")
    void testCreateOrderShouldIncludeMockLocalDate_When_NewOrderIsCreated() {
        // Given
        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class)) {

            mockedLocalDate.when(LocalDate::now).thenReturn(defaultLocalDate);

            Order orderResult = orderService.createOrder("Macbook", 1L);

            assertEquals(defaultLocalDate, orderResult.getCreationDate());

        }
        //When

    }

}
