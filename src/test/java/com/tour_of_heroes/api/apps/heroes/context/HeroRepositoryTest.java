package com.tour_of_heroes.api.apps.heroes.context;

import com.tour_of_heroes.api.heroes.domain.contracts.HeroRepository;
import com.tour_of_heroes.api.heroes.domain.entities.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class HeroRepositoryTest {

    @Autowired
    HeroRepository repository;

    private Hero hero;

    @BeforeEach
    public void setupTestData() {

        hero = new Hero("Pepa la Trepa");
    }

    @Test
    @DisplayName("Test for save hero operation")
    public void givenHeroObject_whenSave_thenReturnSaveHero() {

        Hero saveHero = repository.save(hero);

        assertThat(saveHero).isNotNull();
        assertThat(saveHero.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("test for get Hero List")
    public void givenHeroList_whenFindAll_thenHeroList(){

        Hero heroOne = new Hero("Pepo");
        Hero heroTwo = new Hero("Pepa");

        
    }
}
