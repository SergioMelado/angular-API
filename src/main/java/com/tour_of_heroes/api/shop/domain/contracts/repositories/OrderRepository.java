package com.tour_of_heroes.api.shop.domain.contracts.repositories;

import com.tour_of_heroes.api.shared.core.repositories.BaseRepository;
import com.tour_of_heroes.api.shop.domain.entities.Order;

import java.util.UUID;

public interface OrderRepository extends BaseRepository<Order, UUID> {
}
