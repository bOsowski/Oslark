package oslarkserver

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED

class CreateAccountController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
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
        if(User.findByUsername(username)){
            throw new Exception("This username already exists!")
        }
        if(User.findByEmailAddress(email)){
            throw new Exception("This email address is alrady taken!")
        }

        User user = new User(
                username: username,
                firstName: request.getParameter("firstName"),
                lastName: request.getParameter("lastName"),
                password: request.getParameter("password"),
                emailAddress: email
        )
        user.save flush:true

        UserRole userRole = new UserRole(user: user, role: Role.findByAuthority("ROLE_USER"))
        userRole.save flush:true

        //render "Successfully created user ${User.findByUsername(user.username).toString()}"
        redirect(controller: "login", action: "auth")
    }
}
