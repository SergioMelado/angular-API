package com.tour_of_heroes.api.shop.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import lombok.Value;

@Value
public class InProductDTO {

    @JsonProperty("name")
    String name;

    @JsonProperty("price")
    Double price;

    @JsonProperty("description")
    String description;

    public static Product from(InProductDTO target) {

        return new Product(target.name, target.description, target.price);
    }

    public static Product from(int id, InProductDTO target) {

        return new Product(id, target.name, target.price, target.description);
    }
}