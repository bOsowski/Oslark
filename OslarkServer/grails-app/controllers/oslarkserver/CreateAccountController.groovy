package oslarkserver

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED

class CreateAccountController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        def user = new User()
        [user : user]
    }

    def constraints = {

    }

//    def save(){
//        println("Trying to save new user.. ${params}")
//        respond new User(username: params.username, firstName: params.firstName, lastName: params.lastName, emailAddress: params.emailAddress)
//    }

    @Transactional
    def save() {
        println(request.getParameterMap())
        String username = request.getParameter("username")
        String email = request.getParameter("emailAddress")

        User user = new User(
                username: username,
                firstName: request.getParameter("firstName"),
                lastName: request.getParameter("lastName"),
                password: request.getParameter("password"),
                emailAddress: email
        )

        user.save flush:true

        if(user.hasErrors()){
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'index'
            return
        }

        Role role = Role.findByAuthority("ROLE_USER")
        UserRole userRole = new UserRole(user: user, role: role)

        userRole.save flush:true



        if(userRole.hasErrors()){
            log.warn("Failed to save the userRole. $userRole: $userRole.errors")
        }

        //render "Successfully created user ${User.findByUsername(user.username).toString()}"
        redirect(controller: "login", action: "auth")
    }
}
