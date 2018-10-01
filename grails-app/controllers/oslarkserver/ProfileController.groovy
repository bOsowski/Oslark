package oslarkserver

import grails.plugin.springsecurity.annotation.Secured
import oslarkserver.admin_only.UserController

@Secured(["ROLE_ADMIN"])
class ProfileController extends UserController{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        def user = User.getCurrentUser()
       redirect action:null
//        forward action:'index'
        return [user: user]
    }

}
