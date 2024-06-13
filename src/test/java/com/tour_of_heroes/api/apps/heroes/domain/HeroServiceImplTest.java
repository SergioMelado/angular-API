package com.tour_of_heroes.api.apps.heroes.domain;

import com.tour_of_heroes.api.heroes.domain.contracts.HeroRepository;
import com.tour_of_heroes.api.heroes.domain.entities.Hero;
import com.tour_of_heroes.api.heroes.domain.services.HeroServiceImpl;
import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HeroServiceImplTest {

    @InjectMocks
    private HeroServiceImpl heroServiceMock;

    @Mock
    private HeroRepository repository;

    @Nested
    class OK {

        @Test
        void whenGetAll_thenReturnHeroList() {

            List<Hero> heroList = List.of(
                    new Hero("Mr Pips"),
                    new Hero("Ms Demon"),
                    new Hero("Mr Yus")
            );

            when(repository.findAll()).thenReturn(heroList);
            List<Hero> heroListServiceActual = heroServiceMock.getAll();
            assertThat(heroListServiceActual, is(heroList));
        }

        @Test
        void whenGetOneById_thenReturnHero() {

            Hero hero = new Hero();
            when(repository.findById(hero.getId())).thenReturn(Optional.of(hero));
            Optional<Hero> heroActual = heroServiceMock.getOne(hero.getId());
            assertSame(hero, heroActual.get());
        }

        @Test
        void whenAddHeroSaveHero_thenReturnHero() throws InvalidDataException {

            Hero hero = new Hero(1, "Mr Fus");
            when(repository.save(hero)).thenReturn(hero);
            when(heroServiceMock.add(hero)).thenReturn(hero);
            assertEquals(heroServiceMock.add(hero), repository.save(hero));
        }

        @Test
        void whenModifyHero_thenReturnHero() throws NotFoundException, InvalidDataException {

            Hero heroOne = new Hero(1, "Ms Demon");
            Hero heroTwo = new Hero(2, "Mr Fus");

            lenient().when(repository.findById(heroOne.getId())).thenReturn(Optional.of(heroOne));
            lenient().when(repository.save(heroOne.merge(heroTwo))).thenReturn(heroOne);
            lenient().when(heroServiceMock.modify(heroOne)).thenReturn(heroTwo);
            assertEquals(heroServiceMock.modify(heroOne), heroTwo);
        }

        @Test
        void deleteHeroWhenHeroNotNull() throws InvalidDataException {

            Hero hero = new Hero(1, "Mr Fus");
            doNothing().when(repository).delete(hero);
            heroServiceMock.delete(hero);
            verify(repository, times(1)).delete(hero);
        }

        @Test
        void deleteHeroByIdWhenIdNotNull() {

            int id = 1;
            doNothing().when(repository).deleteById(id);
            heroServiceMock.deleteById(id);
            verify(repository, times(1)).deleteById(id);
        }

        @Nested
        class KO {

            @Test
            void whenAddNullHero_thenThrowInvalidDataException() {

                Hero hero = null;
                InvalidDataException exception = assertThrows(InvalidDataException.class, () -> heroServiceMock.add(hero));
                assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
            }

            @Test
            void whenModifyNullHero_thenThrowInvalidDataException() {

                Hero hero = null;
                InvalidDataException exception = assertThrows(InvalidDataException.class, () -> heroServiceMock.modify(hero));
                assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
            }

            @Test
            void whenModifyHeroNotSaved_thenThrowNotFoundException() {

                Hero hero = new Hero();
                NotFoundException exception = assertThrows(NotFoundException.class, () -> heroServiceMock.modify(hero));
                assertEquals(exception.getMessage(), NotFoundException.MESSAGE_STRING);
            }

            @Test
            void deleteHeroWhenHeroIsNull() {

                Hero hero = null;
                InvalidDataException exception = assertThrows(InvalidDataException.class, () -> heroServiceMock.delete(hero));
                assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
            }
        }
    }
}
