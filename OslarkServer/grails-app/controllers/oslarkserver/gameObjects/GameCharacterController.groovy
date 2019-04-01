package oslarkserver.gameObjects

import grails.plugin.springsecurity.annotation.Secured
import oslarkserver.User

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured(["ROLE_ADMIN", "ROLE_USER"])
class GameCharacterController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond GameCharacter.list(params).findAll {User.getCurrentUser().characters.contains(it)}, model:[gameCharacterCount: GameCharacter.count()]
    }

    def show(GameCharacter gameCharacter) {
        respond gameCharacter
    }

    def create() {
        if(params.user != User.getCurrentUser().id){
           render code: 500
        }

        respond new GameCharacter(params)
    }

    @Transactional
    def save(GameCharacter gameCharacter) {
        if (gameCharacter == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (gameCharacter.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond gameCharacter.errors, view:'create'
            return
        }
        gameCharacter.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'gameCharacter.label', default: 'GameCharacter'), gameCharacter.id])
                redirect gameCharacter
            }
            '*' { respond gameCharacter, [status: CREATED] }
        }
    }

//    def edit(GameCharacter gameCharacter) {
//        respond gameCharacter
//    }

//    @Transactional
//    def update(GameCharacter gameCharacter) {
//        if (gameCharacter == null) {
//            transactionStatus.setRollbackOnly()
//            notFound()
//            return
//        }
//
//        if (gameCharacter.hasErrors()) {
//            transactionStatus.setRollbackOnly()
//            respond gameCharacter.errors, view:'edit'
//            return
//        }
//
//        gameCharacter.save flush:true
//
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.updated.message', args: [message(code: 'gameCharacter.label', default: 'GameCharacter'), gameCharacter.id])
//                redirect gameCharacter
//            }
//            '*'{ respond gameCharacter, [status: OK] }
//        }
//    }

    @Transactional
    def delete(GameCharacter gameCharacter) {
        if(gameCharacter.user != User.getCurrentUser()){
            throw new Exception("You are not authorised to do this!")
        }

        if (gameCharacter == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        gameCharacter.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'gameCharacter.label', default: 'GameCharacter'), gameCharacter.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'gameCharacter.label', default: 'GameCharacter'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
