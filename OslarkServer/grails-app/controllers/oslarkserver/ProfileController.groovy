package oslarkserver

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import oslarkserver.gameObjects.GameCharacter
import oslarkserver.gameObjects.Terrain

@Secured(["ROLE_ADMIN", "ROLE_USER"])
class ProfileController{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        def user = User.getCurrentUser()
        return [user: user]
    }

    def profile(){
        User user = User.getCurrentUser()
        String charactersJson = ""
        user.characters.eachWithIndex { it, index ->
            charactersJson += it.toJson()
            if(index < user.characters.size()-1){
                charactersJson += ", "
            }
        }
        String text = """{username:${user.username}, characters:[${charactersJson}]}"""
        render(status: 200, text: text)
    }

    def world(){
        String charName = request.getParameter("characterName")
        println("Trying to find character ${charName}")
        GameCharacter character = GameCharacter.findByName(charName)
        if(character.user != User.getCurrentUser()){
            throw new Exception("This user does not have this character!")
        }
        StringBuilder sb = new StringBuilder("{terrain:[")
        Set<Terrain> terrain = Terrain.findAllByWorld(character.world)
        terrain.eachWithIndex { Terrain it, index ->
            sb.append(it.toJson())
            if(index < terrain.size()-1){
                sb.append(", ")
            }
        }
        sb.append("]}")
        render(status: 200, text: sb.toString())
    }

    def test(){
        render text: "SOme text"
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