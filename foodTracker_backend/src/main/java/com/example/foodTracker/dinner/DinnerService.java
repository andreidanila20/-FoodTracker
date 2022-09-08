package com.example.foodTracker.dinner;

import com.example.foodTracker.breakfast.Breakfast;
import com.example.foodTracker.food.Food;
import com.example.foodTracker.nutrition.Nutrition;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DinnerService {
    private final DinnerRepository dinnerRepository;

    public DinnerService(DinnerRepository dinnerRepository) {
        this.dinnerRepository = dinnerRepository;
    }

    @GetMapping
    public List<Dinner> getDinner(){
        return dinnerRepository.findAll();
    }

    @GetMapping
    public Optional<Dinner> getDinnerById(Integer id) { return dinnerRepository.findById(id); }

    public void addDinner(Dinner dinner) {
        //aici verificam daca numele mancarii a fost luat sau nu
        Optional<Dinner> dinnerByName = dinnerRepository.findDinnerByName(dinner.getName());
        if(dinnerByName.isPresent())
        {
            throw new IllegalStateException("Name for dinner taken");
        }
        //in cazul in care nu este in baza de date atunci se salveaza in BD

        dinnerRepository.save(dinner);
    }

    public void deleteDinner(Integer dinnerId){
        //verificam daca exista user-ul cu id-ul respectiv
        //daca nu exista aruncam o exceptie
        if(!dinnerRepository.existsById(dinnerId))
        {
            throw new IllegalStateException("Dinner with id "+dinnerId+" does not exists");
        }
        //daca exista stergem user-ul

        dinnerRepository.deleteById(dinnerId);
    }

    @Transactional
    public void updateDinner(Integer id, String name, List<Food> foods, String info){

        Dinner dinner= dinnerRepository.findById(id).orElseThrow(() ->new IllegalStateException("Dinner with id "+id+" does not exists"));

        if(name != null && name.length() > 0 && !Objects.equals(dinner.getName(),name))
        {
            Optional<Dinner> dinnerByName = dinnerRepository.findDinnerByName(name);
            if(dinnerByName.isPresent())
            {
                throw new IllegalStateException("Name for dinner taken");
            }
            dinner.setName(name);
        }

        if(foods != null )
        {
            dinner.setFoods(foods);
        }

        if(info!=null)
        {
            dinner.setInfo(info);
        }

    }

}
