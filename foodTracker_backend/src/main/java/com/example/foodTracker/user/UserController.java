package com.example.foodTracker.user;

import com.example.foodTracker.food.Food;
import com.example.foodTracker.menu.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //get mapping este folosita pentru a lua datele din baza de date si pentru a le afisa clientului
    //acest lucru este posibil prin intermediul clasei FoodService
    @GetMapping
    public List<Users> getUser(){
        List<Users> user= userService.getUser();
        Collections.sort(user, new Comparator<Users>() {
            @Override
            public int compare(Users f1, Users f2) {
                return f1.getId().compareTo(f2.getId());
            }
        });
        return user;
    }

    @GetMapping(path="{userId}")
    public Optional<Users> getUserById(@PathVariable("userId") Integer id) { return userService.getUserById(id); }

    @GetMapping(path="/getSubscribe/{userId}")
    public Boolean getUserSubscribe(@PathVariable("userId") Integer id) { return userService.getUserSubscribe(id); }

    @GetMapping(path="/unseenMenus/{userId}")
    public List<Menu> getUnseenMenusById(@PathVariable("userId") Integer id) {

        return userService.getUnseenMenusById(id);
    }

    //in momentul in care se va apasa pe unul din meniuri, celelalte se vor selecta ca fiind vazute si vor fi scoase din lista

    @GetMapping(path="/subscribe/{userId}")
    public void subscribeUser(@PathVariable("userId") Integer id) {

        userService.subscribeUser(id);
    }

    @GetMapping(path="/unSubscribe/{userId}")
    public void unSubscribeUser(@PathVariable("userId") Integer id) {

        userService.unSubscribeUser(id);
    }


    @PostMapping(path="/login")
    public Optional<Users> getUserByEmailAndPass(@RequestBody Users user) {

        Optional<Users> u=userService.getUserByEmailAndPass(user);

        return u;

    }

    @PostMapping(path="/randomMenu/{userId}")
    public Menu randomMenuSelect(@PathVariable("userId") Integer userId,@RequestBody List<Menu> menu){
        return userService.randomMenuSelect(userId,menu);
    }

    //PostMapping utilizat pentru a putea face operatii de tip post cu clientul
    //luam request body de la client si apoi il mapam la Food, pentru a obtineun obiect nou de tipul user
    @PostMapping
    public void addNewUser(@RequestBody Users user){
        userService.addUser(user);
    }

    //deleteMapping ofera functionalitatea clientului de a sterge o linie din BD
    @DeleteMapping(path="{userId}")//path folosit pentru a sti ce mancare stergem
    public void deleteUser(@PathVariable("userId") Integer id){
        userService.deleteUser(id);
    }

    @PutMapping(path="/deleteMenu/{userId}")
    public void deleteMenuFromUser(@PathVariable("userId") Integer userId,@RequestBody Menu menu) {
        userService.deleteMenuFromUser(userId,menu);
    }

    @PutMapping(path="/addMenu/{userId}")
    public void addMenuToUser(@PathVariable("userId") Integer userId,@RequestBody Menu menu) {
        userService.addMenuToUser(userId,menu);
    }


    @PutMapping(path="/cleanUnseen/{userId}")
    public void cleanUnseenMenus(@PathVariable("userId") Integer id,@RequestBody Menu menu){
        userService.cleanUnseenMenus(id,menu);
    }
    @PutMapping(path="/deleteUnseenForAll/{userId}")
    public void deleteUnseenMenusForAll(@PathVariable("userId") Integer id,@RequestBody Menu menu) {
        userService.deleteUnseenMenusForAll(id,menu);
    }

    @PutMapping(path="/notifyObservers/{userId}")
    public void updateObservers(@PathVariable("userId") Integer userId,@RequestBody Menu menu){
        userService.notifyObservers(userId,menu);
    }

    @PutMapping(path="{userId}")
    public void updateUser(@PathVariable("userId") Integer userId,
                           @RequestBody Users user) {

        userService.updateUser( userId,user.getAdmin(), user.getName(),user.getEmail(),user.getPassword(),user.getGender(),user.getAge(),user.getHeight(),user.getPounds(),user.getActivity());
    }
}
