package com.tour_of_heroes.api.shop.domain.contracts.services;

import com.tour_of_heroes.api.shared.core.services.DomainService;
import com.tour_of_heroes.api.shop.domain.entities.Order;

import java.util.UUID;

public interface OrderService extends DomainService<Order, UUID> {
}
