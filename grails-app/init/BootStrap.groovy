import oslarkserver.Role
import oslarkserver.User
import oslarkserver.UserRole
import oslarkserver.gameObjects.GameCharacter
import oslarkserver.gameObjects.GameObject
import oslarkserver.gameObjects.enums.CharacterClass
import oslarkserver.gameObjects.enums.Gender

class BootStrap {

    def init = { servletContext ->
        def adminRole = Role.findOrSaveWhere(authority: "ROLE_ADMIN")
        def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")

        def admin = User.findOrSaveWhere(username: "admin", password: "admin", emailAddress: "admin@admin.com", firstName: "Admin", lastName: "Admin")
        def user = User.findOrSaveWhere(username: "user", password: "user", emailAddress: "user@user.com", firstName: "User", lastName: "User")

        UserRole.findOrSaveWhere(user: admin, role: adminRole)
        UserRole.findOrSaveWhere(user: user, role: userRole)

        admin.addToCharacters(new GameCharacter(name: "maleKnight", gender: Gender.MALE, characterClass: CharacterClass.KNIGHT))
        admin.addToCharacters(new GameCharacter(name: "femaleKnight", gender: Gender.FEMALE, characterClass: CharacterClass.KNIGHT))
        admin.addToCharacters(new GameCharacter(name: "maleElf", gender: Gender.MALE, characterClass: CharacterClass.ELF))
        admin.addToCharacters(new GameCharacter(name: "femaleElf", gender: Gender.FEMALE, characterClass: CharacterClass.ELF))
        admin.addToCharacters(new GameCharacter(name: "maleWizard", gender: Gender.MALE, characterClass: CharacterClass.WIZARD))
        admin.addToCharacters(new GameCharacter(name: "femaleWizard", gender: Gender.FEMALE, characterClass: CharacterClass.WIZARD))


        def randomGameObject = new GameObject()
        randomGameObject.save()


    }
    def destroy = {
    }
}
