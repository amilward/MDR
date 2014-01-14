package uk.co.mdc

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import grails.plugins.springsecurity.Secured
import uk.co.mdc.pathways.PathwaysModel
import uk.co.mdc.forms.FormDesign


@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class IndexController {
	def springSecurityService
	def pathwaysService
	
	def index() { 
		
		if(SpringSecurityUtils.ifAllGranted("ROLE_USER")){
		
		
			def finalizedPathways = pathwaysService.findAll {
				isDraft == false
			}
			def draftPathways = pathwaysService.findAll {
				isDraft == true
			}
			def finalizedForms = FormDesign.findAll {
				isDraft == false
			}
			def draftForms = FormDesign.findAll {
				isDraft == true
			}

			
			render(	view: "/dashboard", 
					model: [ 	finalizedPathways : finalizedPathways, 
								draftPathways : draftPathways,
								finalizedForms : finalizedForms, 
								draftForms : draftForms,])
		} else {
			render(view: "/index")
		
		}

	}
}
