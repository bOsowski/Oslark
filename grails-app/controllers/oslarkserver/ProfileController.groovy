package oslarkserver

import com.bosowski.oslarkDomains.AbstractUser
import com.google.gson.Gson
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import oslarkserver.admin_only.UserController

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
        User currentUser = User.getCurrentUser()
        Gson gson = new Gson()
        //response.outputStream << gson.toJson(currentUser, AbstractUser.class).getBytes()
        render(status: 200, text: gson.toJson(currentUser, AbstractUser.class))
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
