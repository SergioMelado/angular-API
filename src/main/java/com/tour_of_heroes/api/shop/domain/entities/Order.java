package com.tour_of_heroes.api.shop.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

    @Id
    @Column(name = "ORDER_ID")
    private UUID id;

    @Column(name = "EXPEDITION_DATE")
    private Timestamp expeditionDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrder> productOrderList;

    public Order() {

        id = UUID.randomUUID();
        expeditionDate = new Timestamp(System.currentTimeMillis());
    }

    public List<Product> getProductOrderList() {

        return productOrderList.stream().map(ProductOrder::getProduct).toList();
    }

    public void addProduct(Product product) {

        ProductOrder productOrder = new ProductOrder(this, product);
        productOrderList.add(productOrder);
    }

    public Order merge(Order target) {

        return target;
    }
}
