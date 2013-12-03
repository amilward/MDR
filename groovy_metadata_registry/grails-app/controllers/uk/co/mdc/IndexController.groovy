package uk.co.mdc

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class IndexController {
	def springSecurityService

	
	def index() { 
		
		def roles = springSecurityService.getPrincipal().getAuthorities()
		if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")){
			render(view: "/index")
			return true
		}else{
			render(view: "/dashboard")
			return true
		}
	}
}
