package com.tour_of_heroes.api.shop.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tour_of_heroes.api.shop.domain.entities.Order;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InOrderDTO {

    @JsonProperty("products")
    private List<Product> productList = new ArrayList<>();

    public static Order from(InOrderDTO source){

        Order result = new Order();
        source.productList.forEach(result::addProduct);
        return result;
    }
}
