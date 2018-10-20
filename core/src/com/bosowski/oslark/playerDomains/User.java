package com.bosowski.oslark.playerDomains;

import com.bosowski.oslarkDomains.AbstractUser;
import com.google.gson.Gson;

import java.lang.reflect.Field;

public class User extends AbstractUser {



    public User(String user){
        System.out.println(user);
        Gson gson = new Gson();
        User thisUser = gson.fromJson(user, User.class);
        System.out.println(thisUser.toString());
    }

    @Override
    public String toString(){
        StringBuilder toString = new StringBuilder();
        for(Field field: getClass().getFields()){
            try {
                toString.append(field.getName()).append("'").append(field.get(this)).append("'");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return toString.toString();
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "username='" + username + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", emailAddress='" + emailAddress + '\'' +
//                '}';
//    }
}
