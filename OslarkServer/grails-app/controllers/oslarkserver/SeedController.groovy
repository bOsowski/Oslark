package oslarkserver

import grails.plugin.springsecurity.annotation.Secured
import oslarkserver.gameObjects.GameCharacter

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured(["ROLE_ADMIN", "ROLE_USER"])
class SeedController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Seed.list(params), model:[seedCount: Seed.count()]
    }

    def show(Seed seed) {
        respond seed
    }

    def create() {
        respond new Seed(params)
    }

    def findId(Long value){
        render(status: 200, text: Seed.findByValue(value)?.id?.toString())
    }

    @Transactional
    def save(Seed seed) {
        if (seed == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (seed.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond seed.errors, view:'create'
            return
        }

        seed.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'seed.label', default: 'Seed'), seed.id])
                redirect seed
            }
            '*' { respond seed, [status: CREATED] }
        }
    }

    def edit(Seed seed) {
        respond seed
    }

    @Transactional
    def update(Seed seed) {
        if (seed == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (seed.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond seed.errors, view:'edit'
            return
        }

        seed.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'seed.label', default: 'Seed'), seed.id])
                redirect seed
            }
            '*'{ respond seed, [status: OK] }
        }
    }

    @Transactional
    def delete(Seed seed) {

        if (seed == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        seed.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'seed.label', default: 'Seed'), seed.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'seed.label', default: 'Seed'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
