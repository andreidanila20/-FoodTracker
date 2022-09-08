package com.example.foodTracker.breakfast;

import com.example.foodTracker.food.Food;
import com.example.foodTracker.nutrition.Nutrition;
import com.example.foodTracker.user.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.ManyToMany;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BreakfastService {
    private final BreakfastRepository breakfastRepository;

    public BreakfastService(BreakfastRepository breakfastRepository) {
        this.breakfastRepository = breakfastRepository;
    }

    @GetMapping
    public List<Breakfast> getBreakfast(){
        return breakfastRepository.findAll();
    }

    @GetMapping
    public Optional<Breakfast> getBreakfastById(Integer id) { return breakfastRepository.findById(id); }

    public void addBreakfast(Breakfast breakfast) {
        //aici verificam daca numele mancarii a fost luat sau nu
        Optional<Breakfast> breakfastByName = breakfastRepository.findBrakfastByName(breakfast.getName());
        if(breakfastByName.isPresent())
        {
            throw new IllegalStateException("Name for breakfast taken");
        }
        //in cazul in care nu este in baza de date atunci se salveaza in BD
        breakfastRepository.save(breakfast);
    }

    public void deleteBreakfast(Integer breakfastId){
        //verificam daca exista user-ul cu id-ul respectiv
        //daca nu exista aruncam o exceptie
        if(!breakfastRepository.existsById(breakfastId))
        {
            throw new IllegalStateException("Breakfast with id "+breakfastId+" does not exists");
        }
        //daca exista stergem user-ul
        breakfastRepository.deleteById(breakfastId);
    }

    @Transactional
    public void updateBreakfast( Integer id,String name, List<Food> foods, String info){

        Breakfast breakfast= breakfastRepository.findById(id).orElseThrow(() ->new IllegalStateException("Breakfast with id "+id+" does not exists"));

        if(name != null && name.length() > 0 && !Objects.equals(breakfast.getName(),name))
        {
            Optional<Breakfast> breakfastByName = breakfastRepository.findBrakfastByName(name);
            if(breakfastByName.isPresent())
            {
                throw new IllegalStateException("Name for breakfast taken");
            }
            breakfast.setName(name);
        }

        if(foods != null )
        {
            breakfast.setFoods(foods);
        }

        if(info!=null)
        {
            breakfast.setInfo(info);
        }



    }


}
