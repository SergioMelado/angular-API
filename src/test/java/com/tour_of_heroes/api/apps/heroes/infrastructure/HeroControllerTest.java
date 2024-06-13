package com.tour_of_heroes.api.apps.heroes.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour_of_heroes.api.controllers.HeroController;
import com.tour_of_heroes.api.heroes.domain.contracts.HeroService;
import com.tour_of_heroes.api.heroes.domain.entities.Hero;
import com.tour_of_heroes.api.heroes.infrastructure.dto.InHeroDTO;
import com.tour_of_heroes.api.heroes.infrastructure.dto.OutHeroDTO;
import lombok.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HeroController.class)
public class HeroControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HeroService srv;

    private String urlTemplate;

    @BeforeEach
    void setUp() {
        urlTemplate = "/api/heroes";
    }

    @Test
    void whenGetOneHero_thenControlCorrectFlow() throws Exception {
        int id = 1;
        Hero hero = new Hero(id, "Pepito");

        when(srv.getOne(anyInt())).thenReturn(Optional.of(hero));

        mockMvc.perform(get(urlTemplate + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Pepito"))
                .andDo(print());

        verify(srv, times(1)).getOne(anyInt());
    }

    @Test
    void whenUpdatingHero_thenControlCorrectFlow() throws Exception {
        int id = 1;
        InHeroDTO inHeroDTO = new InHeroDTO("Pepito");
        Hero hero = new Hero(id, inHeroDTO.getName());
        OutHeroDTO outHeroDTO = OutHeroDTO.from(hero);

        when(srv.modify(any(Hero.class))).thenReturn(hero);

        mockMvc.perform(patch(urlTemplate + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inHeroDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(outHeroDTO.getId()))
                .andExpect(jsonPath("$.name").value(outHeroDTO.getName()))
                .andDo(print());

        verify(srv, Mockito.times(1))
                .modify(Mockito.any(Hero.class));
    }

    @Test
    void whenCreateHero_thenControlCorrectFlow() throws Exception {
        int id = 1;
        InHeroDTO inHeroDTO = new InHeroDTO("Pepito");
        Hero hero = new Hero(id, inHeroDTO.getName());
        OutHeroDTO outHeroDTO = OutHeroDTO.from(hero);

        when(srv.add(any(Hero.class))).thenReturn(hero);

        mockMvc.perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inHeroDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(outHeroDTO.getId()))
                .andExpect(jsonPath("$.name").value(outHeroDTO.getName()))
                .andDo(print());

        verify(srv, Mockito.times(1))
                .add(any(Hero.class));
    }

    @Test
    void whenGetAllHeroes_thenControlCorrectFlow() throws Exception {
        List<Hero> heroList = List.of(
                new Hero(1, "Pepa"),
                new Hero(2, "Pita"),
                new Hero(3, "Pota"));

        when(srv.getAll()).thenReturn(heroList);

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());

        verify(srv, Mockito.times(1))
                .getAll();
    }

    @Test
    void whenRemoveById_thenControlCorrectFlow() throws Exception {
        int id = 1;
        Hero hero = new Hero(id, "Pepito");

        when(srv.getOne(id)).thenReturn(Optional.of(hero));

        doNothing().when(srv).deleteById(id);

        mockMvc.perform(delete(urlTemplate + "/{id}", id))
                .andExpect(status().isNoContent());

        verify(srv, Mockito.times(1)).getOne(id);
        verify(srv, Mockito.times(1)).deleteById(id);
    }

    @Value
    static class HeroMock {
        int heroId;
        String name;
    }
}
