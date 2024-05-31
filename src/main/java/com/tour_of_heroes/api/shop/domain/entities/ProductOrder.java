package com.tour_of_heroes.api.shop.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;

@Entity
@Table(name = "PRODUCT_ORDER")
public class ProductOrder implements Serializable {

    @EmbeddedId
    private ProductOrderPK id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false, insertable = false, updatable = false)
    private Order order;

    @Getter
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false, insertable = false, updatable = false)
    private Product product;

    public ProductOrder() {

    }

    public ProductOrder(Order order, Product product) {

        this.id = new ProductOrderPK(product.getId(), order.getId());
        this.order = order;
        this.product = product;
    }
}
