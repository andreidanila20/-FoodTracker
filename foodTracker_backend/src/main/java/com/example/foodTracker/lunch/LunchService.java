package com.example.foodTracker.lunch;

import com.example.foodTracker.breakfast.Breakfast;
import com.example.foodTracker.food.Food;
import com.example.foodTracker.nutrition.Nutrition;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class LunchService {
    private final LunchRepository lunchRepository;

    public LunchService(LunchRepository lunchRepository) {
        this.lunchRepository = lunchRepository;
    }

    @GetMapping
    public List<Lunch> getLunch(){
        return lunchRepository.findAll();
    }

    @GetMapping
    public Optional<Lunch> getLunchById(Integer id) { return lunchRepository.findById(id); }

    public void addLunch(Lunch lunch) {
        //aici verificam daca numele mancarii a fost luat sau nu

        Optional<Lunch> lunchByName = lunchRepository.findLunchByName(lunch.getName());
        if(lunchByName.isPresent())
        {
            throw new IllegalStateException("Name for lunch taken");
        }

        //in cazul in care nu este in baza de date atunci se salveaza in BD
        lunchRepository.save(lunch);
    }

    public void deleteLunch(Integer id){
        //verificam daca exista user-ul cu id-ul respectiv
        //daca nu exista aruncam o exceptie
        if(!lunchRepository.existsById(id))
        {
            throw new IllegalStateException("Lunch with id "+id+" does not exists");
        }
        //daca exista stergem user-ul
        lunchRepository.deleteById(id);
    }

    @Transactional
    public void updateLunch(Integer id, String name, List<Food> foods, String info){

        Lunch lunch= lunchRepository.findById(id).orElseThrow(() ->new IllegalStateException("Lunch with id "+id+" does not exists"));

        if(name != null && name.length() > 0 && !Objects.equals(lunch.getName(),name))
        {
            Optional<Lunch> lunchByName = lunchRepository.findLunchByName(name);
            if(lunchByName.isPresent())
            {
                throw new IllegalStateException("Name for lunch taken");
            }
            lunch.setName(name);
        }

        if(foods != null )
        {
            lunch.setFoods(foods);
        }

        if(info!=null)
        {
            lunch.setInfo(info);
        }



    }

}
