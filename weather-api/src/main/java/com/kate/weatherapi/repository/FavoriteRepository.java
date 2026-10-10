package com.kate.weatherapi.repository;

import com.kate.weatherapi.entity.FavoriteLocation;
import com.kate.weatherapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteLocation, Integer> {

    List<FavoriteLocation> findAllByUser(User user);

    Optional<FavoriteLocation> findByIdAndUser(int id, User user);

}
