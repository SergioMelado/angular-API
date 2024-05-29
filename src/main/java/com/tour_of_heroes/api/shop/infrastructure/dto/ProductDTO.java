package com.tour_of_heroes.api.shop.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import lombok.Value;

@Value
public class ProductDTO {

    @JsonProperty("id")
    int id;

    @JsonProperty("name")
    String name;

    @JsonProperty("price")
    Double price;

    @JsonProperty("description")
    String description;

    public static ProductDTO from(Product product) {

        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getDescription());
    }

    public static Product from(ProductDTO target) {

        return new Product(target.id, target.name, target.description, target.price);
    }
}