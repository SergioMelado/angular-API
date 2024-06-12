package com.tour_of_heroes.api.shared.core.services;

import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface DomainService<E, K> {

    List<E> getAll();

    Optional<E> getOne(K id);

    E add(E entity) throws InvalidDataException;

    E modify(E entity) throws NotFoundException, InvalidDataException;

    void delete(E entity) throws InvalidDataException;

    void deleteById(K id);
}
