package com.example.foodTracker.dinner;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/dinner")
public class DinnerController {

    private final DinnerService dinnerService;

    public DinnerController(DinnerService dinnerService) {
        this.dinnerService = dinnerService;
    }

    @GetMapping
    public List<Dinner> getDinner(){
        List<Dinner> dinners= dinnerService.getDinner();
        Collections.sort(dinners, new Comparator<Dinner>() {
            @Override
            public int compare(Dinner f1, Dinner f2) {
                return f1.getId().compareTo(f2.getId());
            }
        });
        return dinners;
    }

    @GetMapping(path="{dinnerId}")
    public Optional<Dinner> getDinnerById(@PathVariable("dinnerId") Integer id) { return dinnerService.getDinnerById(id); }

    //PostMapping utilizat pentru a putea face operatii de tip post cu clientul
    //luam request body de la client si apoi il mapam la Food, pentru a obtineun obiect nou de tipul user
    @PostMapping
    public void addNewDinner(@RequestBody Dinner dinner){
        dinnerService.addDinner(dinner);
    }

    //deleteMapping ofera functionalitatea clientului de a sterge o linie din BD
    @DeleteMapping(path="{dinnerId}")//path folosit pentru a sti ce mancare stergem
    public void deleteDinner(@PathVariable("dinnerId") Integer id){
        dinnerService.deleteDinner(id);
    }


    @PutMapping(path="{dinnerId}")
    public void updateDinner(@PathVariable("dinnerId") Integer dinnerId,
                                @RequestBody Dinner dinner) {

        dinnerService.updateDinner( dinnerId, dinner.getName(), dinner.getFoods(),dinner.getInfo());
    }
}
