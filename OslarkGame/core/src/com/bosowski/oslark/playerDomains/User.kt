package com.bosowski.oslark.playerDomains

import com.google.gson.Gson

class User(user: String) {

  var username: String
  var firstName: String
  var lastName: String
  var emailAddress: String

  init {
    println(user)
    val gson = Gson()
    val thisUser = gson.fromJson(user, User::class.java)
    username = thisUser.username
    firstName = thisUser.firstName
    lastName = thisUser.lastName
    emailAddress = thisUser.emailAddress
  }

  override fun toString(): String {
    val toString = StringBuilder()
    for (field in javaClass.fields) {
      try {
        toString.append(field.name).append("'").append(field.get(this)).append("'")
      } catch (e: IllegalAccessException) {
        e.printStackTrace()
      }
    }
    return toString.toString()
  }
}
