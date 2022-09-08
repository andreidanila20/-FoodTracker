package com.example.foodTracker.nutrition;


import com.example.foodTracker.user.Users;
import org.apache.catalina.User;

import javax.persistence.*;

@Entity
@Table
public class Nutrition {

    @Id
    @SequenceGenerator(
            name = "nutrition_sequence",
            sequenceName = "nutrition_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "nutrition_sequence"
    )
    private Integer id;
    private Integer calories;
    private Float proteins;
    private Float lipid;
    private Float carbohydrates;

    public Nutrition() {
    }

    public Nutrition(Integer calories, Float proteins, Float lipid, Float carbohydrates) {
        this.calories = calories;
        this.proteins = proteins;
        this.lipid = lipid;
        this.carbohydrates = carbohydrates;
    }

    public Nutrition(Integer id, Integer calories, Float proteins, Float lipid, Float carbohydrates) {
        this.id = id;
        this.calories = calories;
        this.proteins = proteins;
        this.lipid = lipid;
        this.carbohydrates = carbohydrates;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Nutrition{" +
                "calories=" + calories +
                ", proteins=" + proteins +
                ", lipid=" + lipid +
                ", carbohydrates=" + carbohydrates +
                '}';
    }
}
