package com.example.foodTracker.breakfast;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BreakfastRepository extends JpaRepository<Breakfast,Integer> {

    Optional<Breakfast> findBrakfastByName(String name);
}
