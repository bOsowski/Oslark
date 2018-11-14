package com.bosowski.oslark.main;

import com.badlogic.gdx.Gdx;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Session {

    public static final String TAG = Session.class.getName();
    public static Session instance = new Session();

    private HttpURLConnection connection;
    private CookieManager cookieManager = new CookieManager();

    private Session(){
        CookieHandler.setDefault(cookieManager);
    }

    public void login(String username, String password) throws IOException {
        String message = "username="+username+"&password="+password;
        POST("http://localhost:8080/login/authenticate", message);
        //
    }

    public String loadUser() throws IOException {
        return GET("http://localhost:8080/profile/profile");
    }

    public String loadWorld(String characterName) throws IOException {
        return POST("http://localhost:8080/profile/world", characterName);
    }

    private String GET(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        Gdx.app.debug(TAG, "Sending 'GET' request to URL : " + url);
        Gdx.app.debug(TAG, "Connection redirected to : " + connection.getURL());
        return getResponseAsString(connection);
    }

    private String POST(String urlAddress, String message) throws IOException {
        URL url = new URL(urlAddress);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.getOutputStream().write(message.getBytes());
        Gdx.app.debug(TAG, "Post parameters : " + message);
        connection.setReadTimeout(100000);
        connection.getHeaderFields();
        return getResponseAsString(connection);
    }

    private String getResponseAsString(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null){
            sb.append(inputLine);
        }
        in.close();
        return sb.toString();
    }




}
