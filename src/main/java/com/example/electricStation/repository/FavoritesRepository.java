package com.example.electricStation.repository;

import com.example.electricStation.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    Optional<Favorites> findByUserIdAndStationId(Long userId, Long stationId);
}
