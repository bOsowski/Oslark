package oslarkserver

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
class Role implements Serializable {

	private static final long serialVersionUID = 1

	String authority
	Date dateCreated;
	Date lastUpdated;

	static constraints = {
		authority blank: false, unique: true
	}

	static mapping = {
		cache true
	}
}
