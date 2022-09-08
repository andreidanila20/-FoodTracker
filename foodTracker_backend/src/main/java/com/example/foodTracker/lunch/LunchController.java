package com.example.foodTracker.lunch;

import com.example.foodTracker.dinner.Dinner;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/lunch")
public class LunchController {

    private final LunchService lunchService;

    public LunchController(LunchService lunchService) {
        this.lunchService = lunchService;
    }

    @GetMapping
    public List<Lunch> getDinner(){
        List<Lunch> lunchs= lunchService.getLunch();
        Collections.sort(lunchs, new Comparator<Lunch>() {
            @Override
            public int compare(Lunch f1, Lunch f2) {
                return f1.getId().compareTo(f2.getId());
            }
        });
        return lunchs;
    }

    @GetMapping(path="{lunchId}")
    public Optional<Lunch> getLunchById(@PathVariable("dinnerId") Integer id) { return lunchService.getLunchById(id); }

    //PostMapping utilizat pentru a putea face operatii de tip post cu clientul
    //luam request body de la client si apoi il mapam la Food, pentru a obtineun obiect nou de tipul user
    @PostMapping
    public void addNewLunch(@RequestBody Lunch lunch){
        lunchService.addLunch(lunch);
    }

    //deleteMapping ofera functionalitatea clientului de a sterge o linie din BD
    @DeleteMapping(path="{lunchId}")//path folosit pentru a sti ce mancare stergem
    public void deleteLunch(@PathVariable("lunchId") Integer id){
        lunchService.deleteLunch(id);
    }


    @PutMapping(path="{lunchId}")
    public void updateLunch(@PathVariable("lunchId") Integer lunchId,
                             @RequestBody Lunch lunch) {

        lunchService.updateLunch( lunchId, lunch.getName(), lunch.getFoods(),lunch.getInfo());
    }
}
