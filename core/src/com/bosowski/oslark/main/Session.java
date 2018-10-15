package com.bosowski.oslark.main;

import com.badlogic.gdx.Gdx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Session {

    public static final String TAG = Session.class.getName();
    public static Session instance = new Session();

    private HttpURLConnection connection;

    public boolean login(String username, String password) throws IOException {
        CookieManager cm = new CookieManager();
        CookieHandler.setDefault(cm);
        String message = "username="+username+"&password="+password;

        URL url = new URL("http://localhost:8080/login/authenticate");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.getOutputStream().write(message.getBytes());
        Gdx.app.debug(TAG, "Sending 'POST' request to URL : " + url);
        Gdx.app.debug(TAG, "Post parameters : " + message);
        Gdx.app.debug(TAG, "Connection redirected to : " + connection.getURL());
        if(connection.getURL().toString().toLowerCase().contains("error")){
            return false;
        }
        return true;
    }

    public



}
