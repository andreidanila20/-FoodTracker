package com.example.foodTracker.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findUserByEmail(String email);
    Optional<Users> findUsersByEmailAndPassword(String email,String password);
}
