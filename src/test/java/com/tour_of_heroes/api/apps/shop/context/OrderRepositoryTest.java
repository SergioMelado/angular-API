package com.tour_of_heroes.api.apps.shop.context;

import com.tour_of_heroes.api.shop.domain.contracts.repositories.OrderRepository;
import com.tour_of_heroes.api.shop.domain.contracts.repositories.ProductRepository;
import com.tour_of_heroes.api.shop.domain.entities.Order;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import com.tour_of_heroes.api.shop.domain.entities.ProductOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    private Order order;

    @BeforeEach
    public void setupTestData() {

        order = new Order();
    }

    @Test
    @DisplayName("Test for save order operation")
    public void givenOrderObject_whenSave_thenReturnSaveOrder() {

        Order saveOrder = orderRepository.save(order);

        assertThat(saveOrder).isNotNull();
        assertThat(saveOrder.getId()).isEqualByComparingTo(order.getId());
    }

    @Test
    @DisplayName("test for get Order List")
    public void givenOrderList_whenFindAll_thenOrderList() {

        Order orderOne = new Order();
        Order orderTwo = new Order();

        orderRepository.save(orderOne);
        orderRepository.save(orderTwo);

        // When : Action of behavious that we are going to test
        List<Order> orderList = orderRepository.findAll();

        // Then : Verify the output
        assertThat(orderList).isNotEmpty();
        assertThat(orderList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("test for get Order By Id")
    public void givenOrderObject_whenFindById_thenReturnOrderObject() {

        // Given : Setup object or precondition
        order = orderRepository.save(order);

        // When : Action of behavious that we are going to test
        Order getOrder = orderRepository.findById(order.getId()).get();

        // Then : Verify the output
        assertThat(getOrder).isNotNull();
    }

    @Test
    @DisplayName("test for get all ProductOrder given an Order")
    public void givenOrderObject_whenFindAllProductOrder_thenReturnProductList() {

        Product productOne = new Product("Product1", "Product1 Description", 150.0);
        Product productTwo = new Product("Product2", "Product2 Description", 200.0);

        productRepository.save(productOne);
        productRepository.save(productTwo);

        List<ProductOrder> settingProductOrderList = List.of(
                new ProductOrder(order, productOne),
                new ProductOrder(order, productTwo)
        );

        order.setProductOrderList(settingProductOrderList);
        order = orderRepository.save(order);

        List<Product> productOrderList = orderRepository.findById(order.getId()).get().getProductOrderList();

        assertThat(productOrderList).isNotEmpty();
        assertThat(productOrderList.size()).isEqualTo(2);
    }
}
