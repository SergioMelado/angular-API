package com.tour_of_heroes.api.shop.domain.services;

import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import com.tour_of_heroes.api.shop.domain.contracts.repositories.ProductRepository;
import com.tour_of_heroes.api.shop.domain.contracts.services.ProductService;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository dao;

    @Override
    public List<Product> getAll() {
        return dao.findAll();
    }

    @Override
    public Optional<Product> getOne(Integer id) {

        return dao.findById(id);
    }

    @Override
    public Product add(Product item) throws InvalidDataException {

        if(item == null) throw new InvalidDataException(InvalidDataException.CANT_BE_NULL);

        return dao.save(item);
    }

    @Override
    public Product modify(Product item) throws NotFoundException, InvalidDataException {

        if (item == null) throw new InvalidDataException(InvalidDataException.CANT_BE_NULL);
        Optional<Product> product = dao.findById(item.getId());
        if(product.isEmpty()) throw new NotFoundException();
        return dao.save(item.merge(product.get()));
    }

    @Override
    public void delete(Product item) throws InvalidDataException {

        if (item == null) throw new InvalidDataException(InvalidDataException.CANT_BE_NULL);
        dao.delete(item);
    }

    @Override
    public void deleteById(Integer id) {

        if(id != null) dao.deleteById(id);
    }
}