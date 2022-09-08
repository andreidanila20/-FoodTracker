package com.example.foodTracker.food;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//clasa ce se ocupa cu serviciile, layer intermediar intre cel de date si de control(API)
@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @GetMapping
    public List<Food> getFood(){
        return foodRepository.findAll();
    }


    @GetMapping
    public Optional<Food> getFoodById( Integer id) { return foodRepository.findById(id); }

    public void addFood(Food food) {
        //aici verificam daca numele mancarii a fost luat sau nu
        Optional<Food> foodByName = foodRepository.findFoodByName(food.getName());
        if(foodByName.isPresent())
        {
            throw new IllegalStateException("Email taken");
        }
        //in cazul in care nu este in baza de date atunci se salveaza in BD
        foodRepository.save(food);
    }

    public void deleteFood(Integer foodId){
        //verificam daca exista mancarea cu id-ul respectiv
        //daca nu exista aruncam o exceptie
        if(!foodRepository.existsById(foodId))
        {
            throw new IllegalStateException("food with id "+foodId+" does not exists");
        }
        //daca exista stergem mancarea respectiva
        foodRepository.deleteById(foodId);
    }

    @Transactional
    public void updateFood(Integer foodId, String name, Integer calories, Float proteins, Float lipid, Float carbohydrates){

        Food food = foodRepository.findById(foodId).orElseThrow(() ->new IllegalStateException("food with id "+foodId+" does not exists"));

        if(name != null && name.length() > 0 && !Objects.equals(food.getName(),name))
        {
            Optional<Food> foodByName = foodRepository.findFoodByName(name);
            if(foodByName.isPresent())
            {
                throw new IllegalStateException("Name taken");
            }
            food.setName(name);
        }

        if(calories != null )
        {
            food.setCalories(calories);
        }

        if(proteins != null )
        {
            food.setProteins(proteins);
        }

        if(lipid != null )
        {
            food.setLipid(lipid);
        }

        if(carbohydrates != null )
        {
            food.setCarbohydrates(carbohydrates);
        }

    }
}
