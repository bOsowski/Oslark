package oslarkserver

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import oslarkserver.admin_only.UserController

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

@Secured(["ROLE_ADMIN"])
class ProfileController{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        def user = User.getCurrentUser()
        return [user: user]
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
