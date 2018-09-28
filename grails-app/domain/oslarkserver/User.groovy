package oslarkserver

class User {

    String login
    String firstName
    String lastName
    ArrayList<Character> characters


    static constraints = {
        login nullable: false, minSize: 2, maxSize: 15
        firstName nullable: false
        lastName nullable: false
    }
}
