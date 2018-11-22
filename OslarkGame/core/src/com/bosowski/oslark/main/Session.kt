package com.bosowski.oslark.main

import com.badlogic.gdx.Gdx

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.CookieHandler
import java.net.CookieManager
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern

class Session private constructor() {

  private var connection: HttpURLConnection? = null
  private val cookieManager = CookieManager()

  init {
    CookieHandler.setDefault(cookieManager)
  }

  @Throws(IOException::class)
  fun login(username: String, password: String) {
    val message = "username=$username&password=$password"
    POST("http://localhost:8080/login/authenticate", message)
    //
  }

  @Throws(IOException::class)
  fun loadUser(): String {
    return GET("http://localhost:8080/profile/profile")
  }

  @Throws(IOException::class)
  fun loadWorld(characterName: String): String {
    return POST("http://localhost:8080/profile/world", characterName)
  }

  @Throws(IOException::class)
  private fun GET(urlAddress: String): String {
    val url = URL(urlAddress)
    connection = url.openConnection() as HttpURLConnection
    connection!!.requestMethod = "GET"
    connection!!.doInput = true
    connection!!.doOutput = true
    Gdx.app.debug(TAG, "Sending 'GET' request to URL : $url")
    Gdx.app.debug(TAG, "Connection redirected to : " + connection!!.url)
    return getResponseAsString(connection!!)
  }

  @Throws(IOException::class)
  private fun POST(urlAddress: String, message: String): String {
    val url = URL(urlAddress)
    connection = url.openConnection() as HttpURLConnection
    connection!!.requestMethod = "POST"
    connection!!.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
    connection!!.useCaches = false
    connection!!.doOutput = true
    connection!!.doInput = true
    connection!!.outputStream.write(message.toByteArray())
    Gdx.app.debug(TAG, "Post parameters : $message")
    connection!!.readTimeout = 100000
    connection!!.headerFields
    return getResponseAsString(connection!!)
  }

  @Throws(IOException::class)
  private fun getResponseAsString(connection: HttpURLConnection): String {
    val inStream = BufferedReader(InputStreamReader(connection.inputStream))
    val sb = StringBuilder()
    var inputLine = inStream.readLine()
    while (inputLine != null) {
      sb.append(inputLine)
      inputLine = inStream.readLine()
    }
    inStream.close()
    return sb.toString()
  }

  companion object {

    val TAG = Session::class.java.name
    var instance = Session()
  }


}
