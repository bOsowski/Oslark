import oslarkserver.Role
import oslarkserver.User
import oslarkserver.UserRole

class BootStrap {

    def init = { servletContext ->
        def adminRole = Role.findOrSaveWhere(authority: "ROLE_ADMIN")
        def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")

        def admin = User.findOrSaveWhere(username: "admin", password: "admin", emailAddress: "test@test.test", firstName: "Admin", lastName: "Admin")
        def user = User.findOrSaveWhere(username: "user", password: "user", emailAddress: "test@test.test", firstName: "User", lastName: "User")

        UserRole.findOrSaveWhere(user: admin, role: adminRole)
        UserRole.findOrSaveWhere(user: user, role: userRole)
    }
    def destroy = {
    }
}
