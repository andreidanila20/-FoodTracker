package com.example.foodTracker.breakfast;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/breakfast")
public class BreakfastController {

    private final BreakfastService breakfastService;

    public BreakfastController(BreakfastService breakfastService) {
        this.breakfastService = breakfastService;
    }

    //get mapping este folosita pentru a lua datele din baza de date si pentru a le afisa clientului
    //acest lucru este posibil prin intermediul clasei FoodService
    @GetMapping
    public List<Breakfast> getBreakfast(){
        List<Breakfast> breakfasts= breakfastService.getBreakfast();
        Collections.sort(breakfasts, new Comparator<Breakfast>() {
            @Override
            public int compare(Breakfast f1, Breakfast f2) {
                return f1.getId().compareTo(f2.getId());
            }
        });
        return breakfasts;
    }

    @GetMapping(path="{breakfastId}")
    public Optional<Breakfast> getBreakfastById(@PathVariable("breakfastId") Integer id) { return breakfastService.getBreakfastById(id); }

    //PostMapping utilizat pentru a putea face operatii de tip post cu clientul
    //luam request body de la client si apoi il mapam la Food, pentru a obtineun obiect nou de tipul user
    @PostMapping
    public void addNewBreakfast(@RequestBody Breakfast breakfast){
        breakfastService.addBreakfast(breakfast);
    }

    //deleteMapping ofera functionalitatea clientului de a sterge o linie din BD
    @DeleteMapping(path="{breakfastId}")//path folosit pentru a sti ce mancare stergem
    public void deleteBreakfast(@PathVariable("breakfastId") Integer id){
        breakfastService.deleteBreakfast(id);
    }


    @PutMapping(path="{breakfastId}")
    public void updateBreakfast(@PathVariable("breakfastId") Integer breakfastId,
                           @RequestBody Breakfast breakfast) {

        breakfastService.updateBreakfast( breakfastId, breakfast.getName(), breakfast.getFoods(),breakfast.getInfo());
    }
}
