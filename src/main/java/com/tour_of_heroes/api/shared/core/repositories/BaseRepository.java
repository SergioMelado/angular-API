package com.tour_of_heroes.api.shared.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E, K> extends JpaRepository<E, K> {
}
