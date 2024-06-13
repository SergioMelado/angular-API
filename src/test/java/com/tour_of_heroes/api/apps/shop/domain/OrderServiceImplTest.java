package com.tour_of_heroes.api.apps.shop.domain;

import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import com.tour_of_heroes.api.shop.domain.contracts.repositories.OrderRepository;
import com.tour_of_heroes.api.shop.domain.entities.Order;
import com.tour_of_heroes.api.shop.domain.services.OrderServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderServiceMock;

    @Mock
    private OrderRepository repository;

    @Nested
    class OK {

        @Test
        void whenGetAll_thenReturnOrderList() {

            List<Order> orderList = List.of(new Order(), new Order());
            when(repository.findAll()).thenReturn(orderList);
            List<Order> orders = orderServiceMock.getAll();
            assertArrayEquals(new List[]{orders}, new List[]{orderList});
        }

        @Test
        void whenGetOneById_thenReturnOrder() {

            Order orderMockExpected = new Order();
            when(repository.findById(orderMockExpected.getId())).thenReturn(Optional.of(orderMockExpected));
            Optional<Order> orderActual = orderServiceMock.getOne(orderMockExpected.getId());
            assertSame(orderMockExpected, orderActual.get());
        }

        @Test
        void whenAddOrderSaveOrder_thenReturnOrder() throws InvalidDataException {

            Order order = new Order();
            when(repository.save(order)).thenReturn(order);
            when(orderServiceMock.add(order)).thenReturn(order);
            assertEquals(orderServiceMock.add(order), repository.save(order));
        }

        @Test
        void whenModifyOrder_thenReturnOrder() throws NotFoundException, InvalidDataException {

            Order orderOne = new Order();
            Order orderTwo = new Order();

            lenient().when(repository.findById(orderOne.getId())).thenReturn(Optional.of(orderOne));
            lenient().when(repository.save(orderOne.merge(orderTwo))).thenReturn(orderOne);
            lenient().when(orderServiceMock.modify(orderOne)).thenReturn(orderTwo);
            assertEquals(orderServiceMock.modify(orderOne), orderTwo);
        }

        @Test
        void deleteOrderWhenOrderNotNull() throws InvalidDataException {

            Order order = new Order();
            doNothing().when(repository).delete(order);
            orderServiceMock.delete(order);
            verify(repository, times(1)).delete(order);
        }

        @Test
        void deleteOrderByIdWhenIdNotNull() {

            UUID id = UUID.randomUUID();
            doNothing().when(repository).deleteById(id);
            orderServiceMock.deleteById(id);
            verify(repository, times(1)).deleteById(id);
        }
    }

    @Nested
    class KO {

        @Test
        void whenAddNullOrder_thenThrowInvalidDataException() {

            Order order = null;
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> orderServiceMock.add(order));
            assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
        }

        @Test
        void whenModifyNullOrder_thenThrowInvalidDataException(){
            Order order = null;
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> orderServiceMock.modify(order));
            assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
        }

        @Test
        void whenModifyOrderNotSaved_thenThrowNotFoundException() {

            Order order = new Order();
            NotFoundException exception = assertThrows(NotFoundException.class, () -> orderServiceMock.modify(order));
            assertEquals(exception.getMessage(), NotFoundException.MESSAGE_STRING);
        }

        @Test
        void deleteOrderWhenOrderIsNull() {

            Order order = null;
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> orderServiceMock.delete(order));
            assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
        }
    }
}