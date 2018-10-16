package oslarkserver

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.userdetails.GrailsUser
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic
import com.bosowski.oslarkDomains.AbstractUser
import oslarkserver.gameObjects.Character

@Secured("ROLE_ADMIN")
@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User extends AbstractUser implements Serializable {

	private static final long serialVersionUID = 1

	static SpringSecurityService springSecurityService

	String password
	boolean enabled = true
	boolean accountExpired = false
	boolean accountLocked = false
	boolean passwordExpired = false
    Date dateCreated
    Date lastUpdated

	static hasMany = [characters: Character]

	Set<Role> getAuthorities() {
		(UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService']

	static constraints = {
		emailAddress email: true
		password blank: false, password: true
		username blank: false, unique: true
	}

	static mapping = {
		password column: '`password`'
	}


	static User getCurrentUser(){
		GrailsUser principal = (GrailsUser)springSecurityService.principal
		println("Trying to find user with id ${principal.id}")
		return findById((int)principal.id)
	}


	@Override
	String toString() {
		return "User{" +
				"enabled=" + enabled +
				", accountExpired=" + accountExpired +
				", accountLocked=" + accountLocked +
				", passwordExpired=" + passwordExpired +
				", username='" + username + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", emailAddress='" + emailAddress + '\'' +
				'}'
	}
}
