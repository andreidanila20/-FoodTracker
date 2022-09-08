package com.example.foodTracker.lunch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LunchRepository extends JpaRepository<Lunch,Integer> {
        Optional<Lunch> findLunchByName(String name);
}
