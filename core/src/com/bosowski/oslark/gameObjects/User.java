package com.bosowski.oslark.gameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.bosowski.oslarkDomains.AbstractUser;
import com.google.gson.Gson;

import java.util.ArrayList;

public class User extends AbstractUser {

    public User(String user){
        Gson gson = new Gson();
        User thisUser = gson.fromJson(user, User.class);
        System.out.println(thisUser.toString());
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
