package com.example.foodTracker.user;

import com.example.foodTracker.menu.Menu;
import com.example.foodTracker.nutrition.Nutrition;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<Users> getUser(){
        return userRepository.findAll();
    }


    public Boolean getUserSubscribe(Integer id){
        Optional<Users> user=userRepository.findById(id);
        return user.get().getSubscribe();
    }


    public Optional<Users> getUserById(Integer id) { return userRepository.findById(id); }

    public List<Menu> getUnseenMenusById(Integer id) {
        Optional<Users> user=userRepository.findById(id);
        return user.get().getUnseenMenus();
    }

    public Optional<Users> getUserByEmailAndPass(Users user){

        Optional<Users> userByEmail = userRepository.findUsersByEmailAndPassword(user.getEmail(),user.getPassword());

        if(userByEmail==null)
            throw new IllegalStateException("We don't have this user!");
        return userByEmail;
    }
    @Transactional
    @Transient
    public void addUser(Users user) {
        //aici verificam daca numele mancarii a fost luat sau nu
        Optional<Users> userByEmail = userRepository.findUserByEmail(user.getEmail());
        if(userByEmail.isPresent())
        {
            throw new IllegalStateException("Email taken");
        }
        //in cazul in care nu este in baza de date atunci se salveaza in BD
        if(user.getNutrition()==null)
        {
            Nutrition n=null;

            n = this.calculateNutrition(user.getGender(),user.getAge(),user.getHeight(),user.getPounds(),user.getActivity());


            if(n!=null) {
                user.setNutrition(n);

            }
            if(user.getSubscribe()==null){
                user.unSubscribe();
            }
        }

        userRepository.save(user);

    }

    public void deleteUser(Integer userId){
        //verificam daca exista user-ul cu id-ul respectiv
        //daca nu exista aruncam o exceptie
        if(!userRepository.existsById(userId))
        {
            throw new IllegalStateException("food with id "+userId+" does not exists");
        }
        //daca exista stergem user-ul
        userRepository.deleteById(userId);
    }


    public Menu randomMenuSelect(Integer userId,List<Menu> menu)
    {
        Users user= userRepository.findById(userId).orElseThrow(() ->new IllegalStateException("User with id "+userId+" does not exists"));
        List<Menu> partialMenus= new ArrayList<Menu>();
        Integer calories=user.getNutrition().getCalories();
        Float proteins=user.getNutrition().getProteins();
        Float carbohydrates=user.getNutrition().getCarbohydrates();
        Float lipid=user.getNutrition().getLipid();
        Float minCal=calories.floatValue()-calories.floatValue()*0.05f;
        Float maxCal=calories.floatValue()+calories.floatValue()*0.05f;
        Float maxProt=(0.2f*calories.floatValue())/4.0f;
        Float maxCarbo=(0.6f*calories.floatValue())/4.0f;
        Float maxLipid=(0.3f*calories.floatValue())/9.0f;


        for(Menu m:menu)
        {
            if((minCal.intValue()<=m.getNutrition().getCalories()) && (maxCal.intValue()>=m.getNutrition().getCalories()) &&
                    (proteins<=m.getNutrition().getProteins()) && (maxProt>=m.getNutrition().getProteins()) &&
                    (carbohydrates<=m.getNutrition().getCarbohydrates()) && (maxCarbo>=m.getNutrition().getCarbohydrates()) &&
                    (lipid<=m.getNutrition().getLipid()) && (maxLipid>=m.getNutrition().getLipid())){
                partialMenus.add(m);
            }

        }

        Random rand = new Random();


        int rand_int = rand.nextInt(partialMenus.size());

        Menu m=partialMenus.get(rand_int);
        Integer i=0;
        while(this.findMenuById(user.getMenu(),m)){
            rand_int = rand.nextInt(partialMenus.size());
            m=partialMenus.get(rand_int);
            i++;
            if(i==20)
            {
                break;
            }
        }

        return m;//aici derezolvat

    }

    @Transactional
    public void deleteMenuFromUser(Integer userId,Menu menu)
    {
        Users user= userRepository.findById(userId).orElseThrow(() ->new IllegalStateException("User with id "+userId+" does not exists"));

        List<Menu> m=user.getMenu();

        for(Menu men:m)
        {
            if(men.getId()==menu.getId())
            {
                m.remove(men);
                break;
            }
        }

    }

    @Transactional
    public void addMenuToUser(Integer userId,Menu menu)
    {
        Users user= userRepository.findById(userId).orElseThrow(() ->new IllegalStateException("User with id "+userId+" does not exists"));

        List<Menu> m=user.getMenu();
        Boolean gasit;

        gasit=this.findMenuById(m,menu);

        if(gasit==false)
        {
            m.add(menu);
            user.setMenu(m);
        }

    }


    @Transactional
    public void updateUser(Integer userId, Boolean admin, String name, String email, String password,String gender, Integer age, Integer height, Float pounds,String activity){

        Users user= userRepository.findById(userId).orElseThrow(() ->new IllegalStateException("User with id "+userId+" does not exists"));

        if(email != null && email.length() > 0 && !Objects.equals(user.getEmail(),email))
        {
            Optional<Users> userByEmail = userRepository.findUserByEmail(email);
            if(userByEmail.isPresent())
            {
                throw new IllegalStateException("Email taken");
            }
            user.setEmail(email);
        }

        if(admin == null )
        {
            user.setAdmin(false);
        }
        if(user.getSubscribe()==null){
            user.unSubscribe();
        }

        if(name!=null)
        {
            user.setName(name);
        }
        if(password!=null)
        {
            user.setPassword(password);
        }
        if(age!=null)
        {
            user.setAge(age);
        }
        if(pounds!=null)
        {
            user.setPounds(pounds);
        }
        if(gender!=null)
        {
            user.setGender(gender);
        }
        if(height!=null) {
            user.setHeight(height);
        }
        if(activity!=null)
        {
            user.setActivity(activity);
        }

        Nutrition n= new Nutrition();
        n=this.calculateNutrition(user.getGender(),user.getAge(),user.getHeight(),user.getPounds(), user.getActivity());


        if(n!=null){
            user.setNutrition(n);
        }

    }
    /*
       Pentru Barbati:

       RMB = 10 x Greutate (kg) + 6.25 x Inaltime (cm) – 5 x Varsta (ani) + 5

       Pentru Femei:

       RMB = 10 x Greutate (kg) + 6.25 x Inaltime (cm) – 5 x Varsta (ani) – 161
       Sedentary = 1.2
        Lightly active = 1.375
        Moderately active = 1.550
        Very active = 1.725
        Extra active = 1.9
        */
    public Boolean findMenuById(List<Menu> menuList, Menu menu){
        for(Menu m:menuList)
        {
            if(m.getId()==menu.getId())
            {
                return true;
            }
        }

        return false;
    }
    public Nutrition calculateNutrition(String gender, Integer age, Integer height, Float pounds,String activity)
    {
        Nutrition n= new Nutrition();
        Float cal=0.0f;
        Float proteins;
        Float carbohydrates;
        Float lipid;

        if(gender.equals("M")){
            cal=10*pounds+6.25f*height-5*age+5;
        }else if(gender.equals("F")){
            cal=10*pounds+6.25f*height-5*age-161;
        }

        if(activity.equals("Sedentary")){
            cal*=1.2f;
        }else if(activity.equals("Lightly active")){
            cal*=1.375f;
        }else if(activity.equals("Moderately active")){
            cal*=1.550f;
        }else if(activity.equals("Very active")){
            cal*=1.725f;
        }else if(activity.equals("Extra active")){
            cal*=1.9f;
        }


        proteins=(0.15f*cal)/4.0f;
        carbohydrates=(0.5f*cal)/4.0f;
        lipid=(0.25f*cal)/9.0f;

        n.setCalories(cal.intValue());
        n.setProteins(proteins);
        n.setLipid(lipid);
        n.setCarbohydrates(carbohydrates);


        return n;

    }
    //metoda este folosita pentru situatia in care se doreste stergerea unui meniu, insa acesta se afla ca si notificare pentru pentru anumiti useri
    //astfel nu mai avem eroare pe server
    @Transactional
    public void deleteUnseenMenusForAll(Integer userId,Menu menu){
        Users user= userRepository.findById(userId).orElseThrow(() ->new IllegalStateException("User with id "+userId+" does not exists"));
        if(user.getAdmin()==true)
        {
            try{
                List<Users> usersList=userRepository.findAll();
                for(Users u:usersList){
                    if(u.getAdmin()==false && u.getSubscribe()){
                        List<Menu> unseenMenus=u.getUnseenMenus();
                        if(unseenMenus.size()>0)
                        {
                            for(Menu m: unseenMenus){
                                if(menu.getId()==m.getId()){
                                    unseenMenus.remove(m);
                                    break;
                                }
                            }
                            u.setUnseenMenus(unseenMenus);

                        }
                    }
                }
            }catch (Exception e){System.out.println("Something not good");}
        }


    }
    @Transactional
    public void cleanUnseenMenus(Integer userId,Menu menu){
        Users user= userRepository.findById(userId).orElseThrow(() ->new IllegalStateException("User with id "+userId+" does not exists"));

        List<Menu> unseenMenus=user.getUnseenMenus();
        if(unseenMenus.size()>0)
        {
            for(Menu m: unseenMenus){
                if(menu.getId()==m.getId()){
                    unseenMenus.remove(m);
                    break;
                }
            }
            user.setUnseenMenus(unseenMenus);
        }

    }

    @Transactional
    public void unSubscribeUser(Integer userId){
        Users user= userRepository.findById(userId).orElseThrow(() ->new IllegalStateException("User with id "+userId+" does not exists"));
        user.unSubscribe();
    }

    @Transactional
    public void subscribeUser(Integer userId){
        Users user= userRepository.findById(userId).orElseThrow(() ->new IllegalStateException("User with id "+userId+" does not exists"));
        user.subscribe();
    }

    @Transactional
    public void notifyObservers(Integer userId,Menu menu){
        Users user= userRepository.findById(userId).orElseThrow(() ->new IllegalStateException("User with id "+userId+" does not exists"));
        if(user.getAdmin()==true)
        {
            try{
                List<Users> usersList=userRepository.findAll();


            for(Users u:usersList){
                if(u.getAdmin()==false){
                    if(u.getSubscribe()){
                        u.update(menu);
                    }

                }
            }
            }catch (Exception e){System.out.println("Something not good");}
        }

    }

    public Users getAdmin(){
        List<Users> usersList=userRepository.findAll();

        for(Users u:usersList){
            if(u.getAdmin())
            {
                return u;
            }
        }
        return null;
    }
}
