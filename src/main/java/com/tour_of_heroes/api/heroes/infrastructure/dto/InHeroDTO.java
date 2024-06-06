package com.tour_of_heroes.api.heroes.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tour_of_heroes.api.heroes.domain.entities.Hero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InHeroDTO {

    @JsonProperty("name")
    private String name;

    public static Hero from(InHeroDTO source) {

        return new Hero(source.getName());
    }

    public static Hero from(int id, InHeroDTO source) {

        return new Hero(id, source.getName());
    }
}