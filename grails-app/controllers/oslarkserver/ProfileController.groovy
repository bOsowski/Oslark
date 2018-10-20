package oslarkserver

import com.bosowski.oslarkDomains.AbstractUser
import com.google.gson.Gson
import com.google.gson.JsonElement
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import oslarkserver.admin_only.UserController
import oslarkserver.gameObjects.GameCharacter

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

@Secured(["ROLE_ADMIN", "ROLE_USER"])
class ProfileController{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        def user = User.getCurrentUser()
        return [user: user]
    }

    def profile(){
        User user = User.getCurrentUser()
        String charactersJson = ""
        user.characters.eachWithIndex { it, index ->
            charactersJson += it.toJson()
            if(index < user.characters.size()-1){
                charactersJson += ", "
            }
        }
        String text = """{username:${user.username}, characters:[${charactersJson}]}"""
        render(status: 200, text: text)
    }

    @Transactional
    def update() {
        User currentUser = User.getCurrentUser()
        currentUser.firstName = request.getParameter("firstName")
        currentUser.lastName = request.getParameter("lastName")
        currentUser.password = request.getParameter("password")
        currentUser.username = request.getParameter("username")
        currentUser.save()
        redirect action: 'index'
    }

}
