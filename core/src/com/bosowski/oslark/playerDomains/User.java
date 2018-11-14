package com.bosowski.oslark.playerDomains;

import com.google.gson.Gson;

import java.lang.reflect.Field;

public class User {

    public String username;
    public String firstName;
    public String lastName;
    public String emailAddress;

    public User(String user){
        System.out.println(user);
        Gson gson = new Gson();
        User thisUser = gson.fromJson(user, User.class);
        System.out.println(thisUser.toString());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
}
