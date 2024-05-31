package com.tour_of_heroes.api.controllers;

import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import com.tour_of_heroes.api.shop.domain.contracts.services.OrderService;
import com.tour_of_heroes.api.shop.domain.entities.Order;
import com.tour_of_heroes.api.shop.infrastructure.dto.InOrderDTO;
import com.tour_of_heroes.api.shop.infrastructure.dto.OutOrderDTO;
import com.tour_of_heroes.api.shop.infrastructure.dto.OutProductDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/shop/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/{id}")
    public OutOrderDTO getOrder(@PathVariable UUID id) throws NotFoundException {

        Optional<Order> order = orderService.getOne(id);
        if(order.isEmpty()) throw new NotFoundException();
        return OutOrderDTO.from(order.get());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public OutOrderDTO createOrder(@Valid @RequestBody InOrderDTO inOrderDTO) throws InvalidDataException {

        Order order = InOrderDTO.from(inOrderDTO);
        System.out.println(order);
        return OutOrderDTO.from(
                orderService.add(order)
        );
    }

    @GetMapping
    public List<OutOrderDTO> getAllOrders() {

        List<Order> orderList = orderService.getAll();
        return new ArrayList<>(orderList.stream().map(OutOrderDTO::from).toList());
    }

    @GetMapping("{id}/products")
    public List<OutProductDTO> getProducts(@PathVariable UUID id) throws NotFoundException {

        Optional<Order> order = orderService.getOne(id);
        if(order.isEmpty()) throw new NotFoundException();
        return order.get().getProductOrderList().stream().map(OutProductDTO::from).toList();
    }
}
