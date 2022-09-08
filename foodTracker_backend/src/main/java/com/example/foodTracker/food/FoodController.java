package com.example.foodTracker.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

//Clasa este utilizata pentru a face conexiunea intre servicii si client

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/food")
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    //get mapping este folosita pentru a lua datele din baza de date si pentru a le afisa clientului
    //acest lucru este posibil prin intermediul clasei FoodService
    @GetMapping
    public List<Food> getFood(){
        List<Food> food=foodService.getFood();
        Collections.sort(food, new Comparator<Food>() {
            @Override
            public int compare(Food f1, Food f2) {
                return f1.getId().compareTo(f2.getId());
            }
        });
        return food;
    }



    @GetMapping(path="{foodId}")
    public Optional<Food> getFoodById(@PathVariable("foodId") Integer id) { return foodService.getFoodById(id); }

    //PostMapping utilizat pentru a putea face operatii de tip post cu clientul
    //luam request body de la client si apoi il mapam la Food, pentru a obtineun obiect nou de tipul food
    @PostMapping
    public void addNewFood(@RequestBody Food food){
        foodService.addFood(food);
    }

    //deleteMapping ofera functionalitatea clientului de a sterge o linie din BD
    @DeleteMapping(path="{foodId}")//path folosit pentru a sti ce mancare stergem
    public void deleteFood(@PathVariable("foodId") Integer id){
        foodService.deleteFood(id);
    }


    @PutMapping(path="{foodId}")
    public void updateFood(@PathVariable("foodId") Integer foodId,
                           @RequestBody Food food) {

        foodService.updateFood( foodId, food.getName(), food.getCalories(), food.getProteins(), food.getLipid(), food.getCarbohydrates());
    }

}
