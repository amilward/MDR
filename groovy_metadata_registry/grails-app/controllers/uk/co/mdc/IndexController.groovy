package uk.co.mdc

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class IndexController {
	def springSecurityService

	
	def index() { 
		
		
		if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")){
			render(view: "/admin_index")
		} else if(SpringSecurityUtils.ifAllGranted("ROLE_USER")){
			render(view: "/dashboard")
		} else {
			render(view: "/index")
		
		}

	}
}
