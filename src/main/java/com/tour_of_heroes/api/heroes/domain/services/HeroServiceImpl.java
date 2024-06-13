package com.tour_of_heroes.api.heroes.domain.services;

import com.tour_of_heroes.api.heroes.domain.contracts.HeroRepository;
import com.tour_of_heroes.api.heroes.domain.contracts.HeroService;
import com.tour_of_heroes.api.heroes.domain.entities.Hero;
import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HeroServiceImpl implements HeroService {

    @Autowired
    HeroRepository dao;

    @Override
    public List<Hero> getAll() {

        return dao.findAll();
    }

    @Override
    public Optional<Hero> getOne(Integer id) {

        return dao.findById(id);
    }

    @Override
    public Hero add(Hero item) throws InvalidDataException {

        if(item == null) throw new InvalidDataException(InvalidDataException.CANT_BE_NULL);

        return dao.save(item);
    }

    @Override
    public Hero modify(Hero item) throws NotFoundException, InvalidDataException {

        if (item == null) throw new InvalidDataException(InvalidDataException.CANT_BE_NULL);
        Optional<Hero> order = dao.findById(item.getId());
        if(order.isEmpty()) throw new NotFoundException();
        return dao.save(item.merge(order.get()));
    }

    @Override
    public void delete(Hero item) throws InvalidDataException {

        if (item == null) throw new InvalidDataException(InvalidDataException.CANT_BE_NULL);
        dao.delete(item);
    }

    @Override
    public void deleteById(Integer id) {

        if(id != null) dao.deleteById(id);
    }
}