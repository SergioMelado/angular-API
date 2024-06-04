package com.tour_of_heroes.api.heroes.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tour_of_heroes.api.heroes.domain.entities.Hero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutHeroDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    public static OutHeroDTO from(Hero source) {

        return new OutHeroDTO(source.getId(), source.getName());
    }
}
