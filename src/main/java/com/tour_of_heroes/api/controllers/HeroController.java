package com.tour_of_heroes.api.controllers;

import com.tour_of_heroes.api.heroes.domain.contracts.HeroService;
import com.tour_of_heroes.api.heroes.domain.entities.Hero;
import com.tour_of_heroes.api.heroes.infrastructure.dto.InHeroDTO;
import com.tour_of_heroes.api.heroes.infrastructure.dto.OutHeroDTO;
import com.tour_of_heroes.api.shared.exceptions.BadRequestException;
import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/heroes")
public class HeroController {

    @Autowired
    HeroService heroService;

    @GetMapping("/{id}")
    public OutHeroDTO getHero(@PathVariable int id) throws NotFoundException {

        Optional<Hero> hero = heroService.getOne(id);
        if(hero.isEmpty()) throw new NotFoundException();
        return OutHeroDTO.from(hero.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public OutHeroDTO createHero(@Valid @RequestBody InHeroDTO inHeroDTO) throws InvalidDataException {

        Hero hero = InHeroDTO.from(inHeroDTO);
        return OutHeroDTO.from(heroService.add(hero));
    }

    @GetMapping
    public List<OutHeroDTO> getAllHeroes() {

        List<Hero> heroList = heroService.getAll();
        return new ArrayList<>(heroList.stream().map(OutHeroDTO::from).toList());
    }

    @PatchMapping("/{id}")
    @Transactional
    public OutHeroDTO updateHero(@PathVariable int id, @Valid @RequestBody InHeroDTO inHeroDTO) throws NotFoundException, BadRequestException, InvalidDataException {

        return OutHeroDTO.from(heroService.modify(InHeroDTO.from(id, inHeroDTO)));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHero(@PathVariable int id) throws NotFoundException {

        if(heroService.getOne(id).isEmpty()) throw new NotFoundException();
        heroService.deleteById(id);
    }
}