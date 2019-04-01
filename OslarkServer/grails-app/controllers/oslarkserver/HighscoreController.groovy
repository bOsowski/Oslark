package oslarkserver

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured(["ROLE_ADMIN", "ROLE_USER"])
class HighscoreController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Highscore.list(params), model:[highscoreCount: Highscore.count()]
    }

    def show(Highscore highscore) {
        respond highscore
    }

    def create() {
        respond new Highscore(params)
    }

    @Transactional
    def save(Highscore highscore) {
        if (highscore == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (highscore.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond highscore.errors, view:'create'
            return
        }

        highscore.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'highscore.label', default: 'Highscore'), highscore.id])
                redirect highscore
            }
            '*' { respond highscore, [status: CREATED] }
        }
    }

    def edit(Highscore highscore) {
        respond highscore
    }

    @Transactional
    def update(Highscore highscore) {
        if (highscore == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (highscore.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond highscore.errors, view:'edit'
            return
        }

        highscore.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'highscore.label', default: 'Highscore'), highscore.id])
                redirect highscore
            }
            '*'{ respond highscore, [status: OK] }
        }
    }

    @Transactional
    def delete(Highscore highscore) {

        if (highscore == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        highscore.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'highscore.label', default: 'Highscore'), highscore.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'highscore.label', default: 'Highscore'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
