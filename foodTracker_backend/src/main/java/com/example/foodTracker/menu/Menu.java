package com.example.foodTracker.menu;

import com.example.foodTracker.breakfast.Breakfast;
import com.example.foodTracker.dinner.Dinner;
import com.example.foodTracker.lunch.Lunch;
import com.example.foodTracker.nutrition.Nutrition;
import com.example.foodTracker.user.Users;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
public class Menu {

    @Id
    @SequenceGenerator(
            name = "menu_sequence",
            sequenceName = "menu_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "menu_sequence"
    )
    private Integer id;
    private String name;
    @OneToOne
    private Breakfast breakfast;
    @OneToOne
    private Lunch lunch;
    @OneToOne
    private Dinner dinner;
    @OneToOne(cascade = CascadeType.ALL)
    private Nutrition nutrition;

    public Menu() {
    }

    public Menu(Breakfast breakfast, Lunch lunch, Dinner dinner) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public Menu(Integer id, Breakfast breakfast, Lunch lunch, Dinner dinner) {
        this.id = id;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public Menu(String name, Breakfast breakfast, Lunch lunch, Dinner dinner) {
        this.name = name;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Breakfast getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Breakfast breakfast) {
        this.breakfast = breakfast;
    }

    public Lunch getLunch() {
        return lunch;
    }

    public void setLunch(Lunch lunch) {
        this.lunch = lunch;
    }

    public Dinner getDinner() {
        return dinner;
    }

    public void setDinner(Dinner dinner) {
        this.dinner = dinner;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }


    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", breakfast=" + breakfast +
                ", lunch=" + lunch +
                ", dinner=" + dinner +
                '}';
    }

}
