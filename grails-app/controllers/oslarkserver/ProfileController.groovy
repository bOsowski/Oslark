package oslarkserver

import dungeonGeneration.DungeonGenerator
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import oslarkserver.gameObjects.GameCharacter
import oslarkserver.gameObjects.Terrain
import oslarkserver.gameObjects.components.Rectangle

@Secured(["ROLE_ADMIN", "ROLE_USER"])
class ProfileController {

  static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

  def index() {
    def user = User.getCurrentUser()
    return [user: user]
  }

  def profile() {
    User user = User.getCurrentUser()
    String charactersJson = ""
    user.characters.eachWithIndex { it, index ->
      charactersJson += GameCharacter.findById(it.id).toJson()
      if (index < user.characters.size() - 1) {
        charactersJson += ", "
      }
    }
    String text = """{username:${user.username}, characters:[${charactersJson}]}"""
    println(text.toString())
    render(status: 200, text: text)
  }

  def world() {
    String charName = request.getParameter("characterName")
    println("Trying to find character ${charName}")
    GameCharacter character = GameCharacter.findByName(charName)
    if (character.user != User.getCurrentUser()) {
      throw new Exception("This user does not have this character!")
    }
    StringBuilder sb = new StringBuilder("{terrain:[")
    DungeonGenerator dg = new DungeonGenerator(new Rectangle(-5f, -5f, 10, 10), 2, 4, 4)
    dg.create()
    ArrayList<Terrain> terrain = dg.generation()
    println(terrain.toString())
    terrain.eachWithIndex { Terrain it, index ->
      sb.append(it.toJson())
      if (index < terrain.size() - 1) {
        sb.append(", ")
      }
    }
    sb.append("]}")
    render(status: 200, text: sb.toString())
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