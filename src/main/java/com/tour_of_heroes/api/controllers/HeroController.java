package com.tour_of_heroes.api.controllers;

import com.tour_of_heroes.api.heroes.domain.contracts.HeroService;
import com.tour_of_heroes.api.heroes.domain.entities.Hero;
import com.tour_of_heroes.api.shared.exceptions.BadRequestException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/heroes")
public class HeroController {

    @Autowired
    HeroService heroService;

    @GetMapping("/{id}")
    public Hero getHero(@PathVariable int id) throws NotFoundException {

        Optional<Hero> hero = heroService.getOne(id);
        if(hero.isEmpty()) throw new NotFoundException();
        return hero.get();
    }

    @GetMapping
    public List<Hero> getAllHeroes() {

        return heroService.getAll();
    }

    @PatchMapping("/{id}")
    @Transactional
    public Hero updateHero(@PathVariable int id, @Valid @RequestBody Hero hero) throws NotFoundException, BadRequestException {

        if(heroService.getOne(id).isEmpty()) throw new BadRequestException("ID does not exist");
        return heroService.modify(hero);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteHero(@PathVariable int id) throws NotFoundException {

        if(heroService.getOne(id).isEmpty()) throw new NotFoundException();
        heroService.deleteById(id);
    }
}