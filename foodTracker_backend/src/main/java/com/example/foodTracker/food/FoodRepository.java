package com.example.foodTracker.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//clasa disponibila pentru accesul direct la BD
//DATA ACCESS LAYER
//aici introducem tiate intorogarile pentrub baza de date

@Repository
public interface FoodRepository extends JpaRepository<Food,Integer> {

   // @Query("SELECT f FROM Food f WHERE f.name= ?1")
    Optional<Food> findFoodByName(String name);
}
