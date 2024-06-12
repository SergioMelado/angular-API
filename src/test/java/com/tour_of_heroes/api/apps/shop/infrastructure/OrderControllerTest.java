package com.tour_of_heroes.api.apps.shop.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour_of_heroes.api.controllers.OrderController;
import com.tour_of_heroes.api.shop.domain.contracts.services.OrderService;
import com.tour_of_heroes.api.shop.domain.entities.Order;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import com.tour_of_heroes.api.shop.domain.entities.ProductOrder;
import com.tour_of_heroes.api.shop.infrastructure.dto.InOrderDTO;
import com.tour_of_heroes.api.shop.infrastructure.dto.OutOrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService srv;

    private String urlTemplate;

    @BeforeEach
    void setUp() {
        urlTemplate = "/api/shop/orders";
    }

    @Test
    void whenGetOneOrder_thenControlCorrectFlow() throws Exception {

        Order order = new Order();

        when(srv.getOne(any(UUID.class))).thenReturn(Optional.of(order));

        mockMvc.perform(get(urlTemplate + "/{id}", order.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId().toString()))
                .andExpect(jsonPath("$.expedition_date").value(order.getExpeditionDate().toString()))
                .andDo(print());

        verify(srv, times(1)).getOne(any(UUID.class));
    }

    @Test
    void whenCreateOrder_thenControlCorrectFlow() throws Exception {

        List<Product> productList = List.of(
                new Product("Test", "Product Test", 100.0)
        );

        InOrderDTO inOrderDTO = new InOrderDTO();
        inOrderDTO.setProductList(productList);
        Order order = new Order();
        OutOrderDTO outOrderDTO = OutOrderDTO.from(order);

        when(srv.add(any(Order.class))).thenReturn(order);

        mockMvc.perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inOrderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(outOrderDTO.getId().toString()))
                .andExpect(jsonPath("$.expedition_date").value(outOrderDTO.getExpeditionDate()))
                .andDo(print());

        verify(srv, Mockito.times(1))
                .add(Mockito.any(Order.class));
    }

    @Test
    void whenGetAllOrders_thenControlCorrectFlow() throws Exception {

        List<Order> orderList = List.of(
                new Order(),
                new Order(),
                new Order()
        );

        when(srv.getAll()).thenReturn(orderList);

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());

        verify(srv, Mockito.times(1)).getAll();
    }

    @Test
    void whenGetProductsFromOrder_thenControlCorrectFlow() throws Exception {

        int id = 1;
        Order order = new Order();
        Product product = new Product(id, "ProductTest1", 125.0, "ProductTest1");

        List<ProductOrder> productOrderList = List.of(

                new ProductOrder(order, product)
        );

        order.setProductOrderList(productOrderList);

        when(srv.getOne(any(UUID.class))).thenReturn(Optional.of(order));

        mockMvc.perform(get(urlTemplate + "/{id}" + "/products", order.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print());
    }
}