import oslarkserver.Role
import oslarkserver.User
import oslarkserver.UserRole
import oslarkserver.World
import oslarkserver.gameObjects.GameCharacter
import oslarkserver.gameObjects.GameObject
import oslarkserver.gameObjects.Terrain
import oslarkserver.gameObjects.components.Vector3
import oslarkserver.gameObjects.enums.CharacterClass
import oslarkserver.gameObjects.enums.Gender

class BootStrap {

    def init = { servletContext ->
        World world = new World()
        world.save()

        def adminRole = Role.findOrSaveWhere(authority: "ROLE_ADMIN")
        def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")

        def admin = User.findOrSaveWhere(username: "admin", password: "admin", emailAddress: "admin@admin.com", firstName: "Admin", lastName: "Admin")
        def user = User.findOrSaveWhere(username: "user", password: "user", emailAddress: "user@user.com", firstName: "User", lastName: "User")

        UserRole.findOrSaveWhere(user: admin, role: adminRole)
        UserRole.findOrSaveWhere(user: user, role: userRole)

        admin.addToCharacters(new GameCharacter(name: "maleKnight", gender: Gender.MALE, characterClass: CharacterClass.KNIGHT, world: world))
        admin.addToCharacters(new GameCharacter(name: "femaleKnight", gender: Gender.FEMALE, characterClass: CharacterClass.KNIGHT, world: world))
        admin.addToCharacters(new GameCharacter(name: "maleElf", gender: Gender.MALE, characterClass: CharacterClass.ELF, world: world))
        admin.addToCharacters(new GameCharacter(name: "femaleElf", gender: Gender.FEMALE, characterClass: CharacterClass.ELF, world: world))
        admin.addToCharacters(new GameCharacter(name: "maleWizard", gender: Gender.MALE, characterClass: CharacterClass.WIZARD, world: world))
        admin.addToCharacters(new GameCharacter(name: "femaleWizard", gender: Gender.FEMALE, characterClass: CharacterClass.WIZARD, world: world))

        (0..10).each{ x ->
            (0..10).each { y->
                Terrain terrain = new Terrain(name: "floor1", terrain: Terrain.TerrainType.NORMAL, position: new Vector3(x, y , -1))
                terrain.save()
                world.addToGameObjects(terrain)
            }
        }

        def randomGameObject = new GameObject()
        randomGameObject.save()


    }
    def destroy = {
    }
}
