package com.tour_of_heroes.api.apps.shop.domain;

import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import com.tour_of_heroes.api.shop.domain.contracts.repositories.ProductRepository;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import com.tour_of_heroes.api.shop.domain.services.ProductServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productServiceMock;

    @Mock
    private ProductRepository repository;

    @Nested
    class OK {

        @Test
        void whenGetAll_thenReturnProductList() {

            List<Product> productList = List.of(

                    new Product(1, "Product1", 150.0, "Product1 Description"),
                    new Product(2, "Product2", 200.0, "Product2 Description")
            );

            when(repository.findAll()).thenReturn(productList);
            List<Product> products = productServiceMock.getAll();
            assertArrayEquals(new List[]{products}, new List[]{productList});
        }

        @Test
        void whenGetOneById_thenReturnProduct() {

            Product productMockExpected = new Product();
            when(repository.findById(productMockExpected.getId())).thenReturn(Optional.of(productMockExpected));
            Optional<Product> productActual = productServiceMock.getOne(productMockExpected.getId());
            assertSame(productMockExpected, productActual.get());
        }

        @Test
        void whenAddProductSaveProduct_thenReturnProduct() throws InvalidDataException {

            Product product = new Product(1, "Product", 150.0, "Product Description");
            when(repository.save(product)).thenReturn(product);
            when(productServiceMock.add(product)).thenReturn(product);
            assertEquals(productServiceMock.add(product), repository.save(product));
        }

        @Test
        void whenModifyOrder_thenReturnOrder() throws NotFoundException, InvalidDataException {

            Product productOne = new Product(1, "Product1", 150.0, "Product1 Description");
            Product productTwo = new Product(2, "Product2", 200.0, "Product2 Description");

            lenient().when(repository.findById(productOne.getId())).thenReturn(Optional.of(productOne));
            lenient().when(repository.save(productOne.merge(productTwo))).thenReturn(productOne);
            lenient().when(productServiceMock.modify(productOne)).thenReturn(productTwo);
            assertEquals(productServiceMock.modify(productOne), productTwo);
        }

        @Test
        void deleteProductWhenProductNotNull() throws InvalidDataException {

            Product product = new Product();
            doNothing().when(repository).delete(product);
            productServiceMock.delete(product);
            verify(repository, times(1)).delete(product);
        }

        @Test
        void deleteProductByIdWhenIdNotNull() {

            int id = 1;
            doNothing().when(repository).deleteById(id);
            productServiceMock.deleteById(id);
            verify(repository, times(1)).deleteById(id);
        }
    }

    @Nested
    class KO {

        @Test
        void whenAddNullProduct_thenThrowInvalidDataException() {

            Product product = null;
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> productServiceMock.add(product));
            assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
        }

        @Test
        void whenModifyNullProduct_thenThrowInvalidDataException() {

            Product product = null;
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> productServiceMock.modify(product));
            assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
        }

        @Test
        void whenModifyProductNotSaved_thenThrowNotFoundException() {

            Product product = new Product();
            NotFoundException exception = assertThrows(NotFoundException.class, () -> productServiceMock.modify(product));
            assertEquals(exception.getMessage(), NotFoundException.MESSAGE_STRING);
        }

        @Test
        void deleteOrderWhenOrderIsNull() {

            Product product = null;
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> productServiceMock.delete(product));
            assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
        }
    }
}