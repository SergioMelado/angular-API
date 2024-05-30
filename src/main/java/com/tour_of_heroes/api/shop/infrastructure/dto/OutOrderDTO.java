package com.tour_of_heroes.api.shop.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tour_of_heroes.api.shop.domain.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutOrderDTO {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("expedition_date")
    private String expeditionDate;

    @JsonProperty("products")
    private List<Object> productList = new ArrayList<>();

    public static OutOrderDTO from(Order source){
        return new OutOrderDTO(
                source.getId(),
                source.getExpeditionDate().toString(),
                new ArrayList<>(source.getProductOrderList().stream().map(OutProductDTO::from).toList())
        );
    }

}
