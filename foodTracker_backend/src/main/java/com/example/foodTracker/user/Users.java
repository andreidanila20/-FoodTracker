package com.example.foodTracker.user;

import com.example.foodTracker.menu.Menu;
import com.example.foodTracker.nutrition.Nutrition;
import com.example.foodTracker.observer.Subscriber;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Users implements Subscriber {

    @Id
    @SequenceGenerator(
            name = "users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_sequence"
    )
    private Integer id;
    private Boolean admin;
    private String name;
    private String email;
    private String password;
    private String gender;
    private Integer age;
    private Integer height;
    private Float pounds;
    private String activity;
    @ManyToMany
    private List<Menu> menu;
    @ManyToMany
    private List<Menu> unseenMenus;
    @OneToOne(cascade = CascadeType.ALL)
    private Nutrition nutrition;
    private Boolean subscribe;


    public Users() {
    }

    public Users(Boolean admin, String name, String email, String password, String gender, Integer age, Integer height, Float pounds,String activity) {
        this.admin = admin;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.pounds = pounds;
        this.activity=activity;
    }


    public Users(Integer id, Boolean admin, String name, String email, String password, String gender, Integer age, Integer height, Float pounds,String activity) {
        this.id = id;
        this.admin = admin;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.pounds = pounds;
        this.activity=activity;
    }
    /*
        Pentru Barbati:

        RMB = 10 x Greutate (kg) + 6.25 x Inaltime (cm) – 5 x Varsta (ani) + 5

        Pentru Femei:

        RMB = 10 x Greutate (kg) + 6.25 x Inaltime (cm) – 5 x Varsta (ani) – 161

        Then, this BMR count is multiplied, depending on your activity level:

        Sedentary = 1.2
        Lightly active = 1.375
        Moderately active = 1.550
        Very active = 1.725
        Extra active = 1.9


         */


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Float getPounds() {
        return pounds;
    }

    public void setPounds(Float pounds) {
        this.pounds = pounds;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public List<Menu> getUnseenMenus() {
        return unseenMenus;
    }

    public void setUnseenMenus(List<Menu> unseenMenus) {
        this.unseenMenus = unseenMenus;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", pounds=" + pounds +
                '}';
    }
    public void subscribe() {
        this.subscribe = true;
    }

    public void unSubscribe() {
        this.subscribe = false;
    }

    @Override
    public void update(Menu menu) {

        unseenMenus.add(menu);

    }
}
