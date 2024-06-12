package com.tour_of_heroes.api.shop.domain.services;

import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import com.tour_of_heroes.api.shop.domain.contracts.repositories.OrderRepository;
import com.tour_of_heroes.api.shop.domain.contracts.services.OrderService;
import com.tour_of_heroes.api.shop.domain.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository dao;

    @Override
    public List<Order> getAll() {
        return dao.findAll();
    }

    @Override
    public Optional<Order> getOne(UUID id) {
        return dao.findById(id);
    }

    @Override
    public Order add(Order item) throws InvalidDataException {

        if(item == null) throw new InvalidDataException(InvalidDataException.CANT_BE_NULL);

        return dao.save(item);
    }

    @Override
    public Order modify(Order item) throws NotFoundException, InvalidDataException {

        if (item == null) throw new InvalidDataException(InvalidDataException.CANT_BE_NULL);
        Optional<Order> order = dao.findById(item.getId());
        if(order.isEmpty()) throw new NotFoundException();
        return dao.save(item.merge(order.get()));
    }

    @Override
    public void delete(Order item) throws InvalidDataException {

        if (item == null) throw new InvalidDataException(InvalidDataException.CANT_BE_NULL);
        dao.delete(item);
    }

    @Override
    public void deleteById(UUID id) {

        if(id != null) dao.deleteById(id);
    }
}
