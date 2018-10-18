import oslarkserver.Role
import oslarkserver.User
import oslarkserver.UserRole
import oslarkserver.gameObjects.GameCharacter
import oslarkserver.gameObjects.GameObject

class BootStrap {

    def init = { servletContext ->
        def adminRole = Role.findOrSaveWhere(authority: "ROLE_ADMIN")
        def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")

        def admin = User.findOrSaveWhere(username: "admin", password: "admin", emailAddress: "admin@admin.com", firstName: "Admin", lastName: "Admin")
        def user = User.findOrSaveWhere(username: "user", password: "user", emailAddress: "user@user.com", firstName: "User", lastName: "User")

        UserRole.findOrSaveWhere(user: admin, role: adminRole)
        UserRole.findOrSaveWhere(user: user, role: userRole)

        admin.addToCharacters(new GameCharacter(name: "first"))
        admin.addToCharacters(new GameCharacter(name: "second"))

        def randomGameObject = new GameObject()
        randomGameObject.save()


    }
    def destroy = {
    }
}
