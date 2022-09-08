package com.example.foodTracker.observer;

import com.example.foodTracker.menu.Menu;

import javax.persistence.*;


public interface Subscriber {



    public void update(Menu menu);
}
