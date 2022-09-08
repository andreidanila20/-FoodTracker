package com.example.foodTracker.menu;

import com.example.foodTracker.lunch.Lunch;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<Menu> getDinner(){
        List<Menu> menus= menuService.getMenu();
        Collections.sort(menus, new Comparator<Menu>() {
            @Override
            public int compare(Menu f1, Menu f2) {
                return f1.getId().compareTo(f2.getId());
            }
        });
        return menus;
    }

    @GetMapping(path="{menuId}")
    public Optional<Menu> getMenuById(@PathVariable("menuId") Integer id) { return menuService.getMenuById(id); }

    //PostMapping utilizat pentru a putea face operatii de tip post cu clientul
    //luam request body de la client si apoi il mapam la Food, pentru a obtineun obiect nou de tipul user
    @PostMapping
    public Integer addNewMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    //deleteMapping ofera functionalitatea clientului de a sterge o linie din BD
    @DeleteMapping(path="{menuId}")//path folosit pentru a sti ce mancare stergem
    public void deleteMenu(@PathVariable("menuId") Integer id){
        menuService.deleteMenu(id);
    }


    @PutMapping(path="{menuId}")
    public void updateMenu(@PathVariable("menuId") Integer menuId,
                            @RequestBody Menu menu) {

        menuService.updateMenu( menuId, menu.getName(), menu.getBreakfast(),menu.getDinner(),menu.getLunch());
    }
}
