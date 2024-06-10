package com.tour_of_heroes.api.apps.heroes.context;

import com.tour_of_heroes.api.heroes.domain.contracts.HeroRepository;
import com.tour_of_heroes.api.heroes.domain.entities.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
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
    public void givenHeroList_whenFindAll_thenHeroList() {
        Hero heroOne = new Hero("Pepe");
        Hero heroTwo = new Hero("Pepa");

        repository.save(heroOne);
        repository.save(heroTwo);

        // When : Action of behavious that we are going to test
        List<Hero> heroList = repository.findAll();

        // Then : Verify the output
        assertThat(heroList).isNotEmpty();
        assertThat(heroList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("test for get Hero By Id")
    public void givenHeroObject_whenFindById_thenReturnHeroObject() {
        // Given : Setup object or precondition
        hero = repository.save(hero);

        // When : Action of behavious that we are going to test
        Hero getHero = repository.findById(hero.getId()).get();

        // Then : Verify the output
        assertThat(getHero).isNotNull();
    }

    @Test
    @DisplayName("test for get Hero update operation")
    public void givenHeroObject_whenUpdate_thenHeroObject() {

        // Given: Setup object or precondition
        hero = repository.save(hero);

        // When: Action or behavior that we are going to test
        Hero getHero = repository.findById(hero.getId()).get();

        String updatedName = "Pepa";
        getHero.setName(updatedName);

        Hero updatedHero = repository.save(getHero);

        // Then: Verify the output or expected result
        assertThat(updatedHero).isNotNull();
        assertThat(updatedHero.getName()).isEqualTo(updatedName);
    }

    @Test
    @DisplayName("test for delete hero operation")
    public void givenHeroObject_whenDelete_thenRemoveHero() {

        hero = repository.save(hero);

        repository.deleteById(hero.getId());
        Optional<Hero> deleteHero = repository.findById(hero.getId());

        assertThat(deleteHero).isEmpty();
    }
}
