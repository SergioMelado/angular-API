package com.tour_of_heroes.api.heroes.domain.contracts;

import com.tour_of_heroes.api.heroes.domain.entities.Hero;
import com.tour_of_heroes.api.shared.core.repositories.BaseRepository;

public interface HeroRepository extends BaseRepository<Hero, Integer> {
}
