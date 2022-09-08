package com.example.foodTracker.dinner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DinnerRepository extends JpaRepository<Dinner,Integer> {
    Optional<Dinner> findDinnerByName(String name);
}
