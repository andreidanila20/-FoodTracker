package com.example.foodTracker.food;


import javax.persistence.*;

@Entity
@Table
public class Food {

    @Id
    @SequenceGenerator(
            name = "food_sequence",
            sequenceName = "food_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "food_sequence"
    )
    private Integer id;
    private Integer calories;
    private Float proteins;
    private Float lipid;
    private Float carbohydrates;
    private String name;

    public Food() {
    }

    public Food(Integer calories, Float proteins, Float lipid, Float carbohydrates, String name) {
        this.calories = calories;
        this.proteins = proteins;
        this.lipid = lipid;
        this.carbohydrates = carbohydrates;
        this.name = name;
    }

    public Food(Integer id, Integer calories, Float proteins, Float lipid, Float carbohydrates, String name) {
        this.id = id;
        this.calories = calories;
        this.proteins = proteins;
        this.lipid = lipid;
        this.carbohydrates = carbohydrates;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", lipid=" + lipid +
                ", carbohydrates=" + carbohydrates +
                ", name='" + name + '\'' +
                '}';
    }
}
