package oslarkserver.gameObjects

import grails.plugin.springsecurity.annotation.Secured
import oslarkserver.gameObjects.GameCharacter
import oslarkserver.gameObjects.Highscore
import oslarkserver.gameObjects.Seed

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

    def characterHighscores(String characterName){
        String highscoreJson = ""
        def highscores = Highscore.findAllByGameCharacter(GameCharacter.findByName(characterName))
        highscores.eachWithIndex { it, index ->
            highscoreJson += it.toJson()
            if(index < highscores.size()-1){
                highscoreJson += ", "
            }
        }
        render(status:200, text: "{highscores:["+highscoreJson+"]}")
    }



    @Transactional
    def save(Highscore highscore) {
        if (highscore == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if(highscore.gameCharacter == null){
            def newHighscore = new Highscore()
            newHighscore.gameCharacter = GameCharacter.findByName(request.getParameter("characterName"))
            newHighscore.score = highscore.score
            newHighscore.seed = highscore.seed
            highscore = newHighscore
        }

        if(Seed.findByValue(highscore.seed?.value) == null){
            Seed newSeed = new Seed(value: Seed.findAll().size())
            println("Creating seed with value = ${newSeed.value}")
            newSeed.validate()
            newSeed.save(flush:true, failOnError: true)
            def newHighscore = new Highscore()
            newHighscore.gameCharacter = highscore.gameCharacter
            newHighscore.score = highscore.score
            newHighscore.seed = newSeed
            highscore = newHighscore
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
