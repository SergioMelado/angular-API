package com.tour_of_heroes.api.shop.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class ProductOrderPK {

    @Column(name = "PRODUCT_ID")
    private int productId;

    @Column(name = "ORDER_ID")
    private UUID orderId;

    public ProductOrderPK() {

    }

    public ProductOrderPK(int productId, UUID orderId) {

        this.productId = productId;
        this.orderId = orderId;
    }
}