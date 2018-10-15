package oslarkserver

class CreateAccountController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {

    }

    def save(){
        respond new User(params)
    }
}
