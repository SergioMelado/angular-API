package com.tour_of_heroes.api.shop.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import lombok.Value;

@Value
public class OutProductDTO {

    @JsonProperty("id")
    int id;

    @JsonProperty("name")
    String name;

    @JsonProperty("price")
    Double price;

    @JsonProperty("description")
    String description;

    public static OutProductDTO from(Product product) {

        return new OutProductDTO(product.getId(), product.getName(), product.getPrice(), product.getDescription());
    }
}