package com.example.foodTracker.nutrition;

import com.example.foodTracker.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NutritionRepository extends JpaRepository<Nutrition,Integer> {



}
