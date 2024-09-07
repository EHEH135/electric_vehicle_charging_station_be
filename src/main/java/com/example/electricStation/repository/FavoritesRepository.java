package com.example.electricStation.repository;

import com.example.electricStation.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    Optional<Favorites> findByUserIdAndStationId(Long userId, Long stationId);

    @Query("SELECT f FROM Favorites f WHERE f.user.id = :userId")
    List<Favorites> findFavoritesByUserId(@Param("userId") Long userId);
}
