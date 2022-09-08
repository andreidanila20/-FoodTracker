package com.example.foodTracker.menu;

import com.example.foodTracker.breakfast.Breakfast;
import com.example.foodTracker.dinner.Dinner;
import com.example.foodTracker.food.Food;
import com.example.foodTracker.lunch.Lunch;
import com.example.foodTracker.nutrition.Nutrition;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getMenu(){
        return menuRepository.findAll();
    }


    public Optional<Menu> getMenuById(Integer id) { return menuRepository.findById(id); }

    public Integer addMenu(Menu menu) {
        //aici verificam daca numele mancarii a fost luat sau nu
        Optional<Menu> menuByName = menuRepository.findMenuByName(menu.getName());
        if(menuByName.isPresent())
        {
            throw new IllegalStateException("Name for menu taken");
        }


        Nutrition n=calculateNutrition(menu.getBreakfast(),menu.getLunch(),menu.getDinner());

        if(n!=null)
        {
            menu.setNutrition(n);
        }

        //in cazul in care nu este in baza de date atunci se salveaza in BD
        menuRepository.save(menu);
        menuByName = menuRepository.findMenuByName(menu.getName());

        return menuByName.get().getId();
    }

    public void deleteMenu(Integer id){
        //verificam daca exista user-ul cu id-ul respectiv
        //daca nu exista aruncam o exceptie
        if(!menuRepository.existsById(id))
        {
            throw new IllegalStateException("Menu with id "+id+" does not exists");
        }
        //daca exista stergem user-ul
        menuRepository.deleteById(id);
    }

    @Transactional
    public void updateMenu(Integer id, String name, Breakfast breakfast, Dinner dinner, Lunch lunch){

        Menu menu= menuRepository.findById(id).orElseThrow(() ->new IllegalStateException("Menu with id "+id+" does not exists"));

        if(name != null && name.length() > 0 && !Objects.equals(menu.getName(),name))
        {
            Optional<Menu> menuByName = menuRepository.findMenuByName(name);
            if(menuByName.isPresent())
            {
                throw new IllegalStateException("Name for menu taken");
            }
            menu.setName(name);
        }

        if(breakfast != null )
        {
            menu.setBreakfast(breakfast);
        }

        if(lunch!=null)
        {
            menu.setLunch(lunch);
        }

        if(dinner!=null)
        {
            menu.setDinner(dinner);
        }

        Nutrition n=calculateNutrition(menu.getBreakfast(),menu.getLunch(),menu.getDinner());

        if(n!=null)
        {
            menu.setNutrition(n);
        }


    }

    public Nutrition calculateNutrition(Breakfast b,Lunch l, Dinner d)
    {
        Nutrition n=new Nutrition();
        Integer cal=0;
        Float proteins=0.0f;
        Float carbohydrates=0.0f;
        Float lipid=0.0f;

        cal+=b.getCalories()+l.getCalories()+d.getCalories();
        proteins+=b.getProteins()+l.getProteins()+d.getProteins();
        carbohydrates+=b.getCarbohydrates()+l.getCarbohydrates()+d.getCarbohydrates();
        lipid+=b.getLipid()+l.getLipid()+d.getLipid();


        n.setCalories(cal);
        n.setProteins(proteins);
        n.setLipid(lipid);
        n.setCarbohydrates(carbohydrates);
        return n;
    }
}
