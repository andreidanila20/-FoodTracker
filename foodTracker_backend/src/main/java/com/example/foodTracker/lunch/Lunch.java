package com.example.foodTracker.lunch;

import com.example.foodTracker.food.Food;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Lunch {

    @Id
    @SequenceGenerator(
            name = "lunch_sequence",
            sequenceName = "lunch_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lunch_sequence"
    )
    private Integer id;
    private String name;
    @ManyToMany
    private List<Food> foods;
    private Integer calories;
    private Float proteins;
    private Float lipid;
    private Float carbohydrates;
    private String info;

    public Lunch() {
    }

    public Lunch(String name, List<Food> foods, String info) {
        this.name = name;
        this.foods = foods;
        this.info = info;
    }

    public Lunch(Integer id, String name, List<Food> foods, Integer calories, Float proteins, Float lipid, Float carbohydrates, String info) {
        this.id = id;
        this.name = name;
        this.foods = foods;
        this.calories = calories;
        this.proteins = proteins;
        this.lipid = lipid;
        this.carbohydrates = carbohydrates;
        this.info = info;
    }

    public Lunch(Integer id, String name, List<Food> foods, String info) {
        this.id = id;
        this.name = name;
        this.foods = foods;
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Float getProteins() {
        return proteins;
    }

    public void setProteins(Float proteins) {
        this.proteins = proteins;
    }

    public Float getLipid() {
        return lipid;
    }

    public void setLipid(Float lipid) {
        this.lipid = lipid;
    }

    public Float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Lunch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", foods=" + foods +
                ", info='" + info + '\'' +
                '}';
    }
}
