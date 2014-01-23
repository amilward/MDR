package uk.co.mdc

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import grails.plugins.springsecurity.Secured
import uk.co.mdc.pathways.PathwaysModel
import uk.co.mdc.forms.FormDesign


@Secured(['ROLE_USER'])
class DashboardController {
	
	def index() {
		
	    def finalizedPathways = PathwaysModel.findAllByIsDraftAndParentNode(false,null)

	    def draftPathways = PathwaysModel.findAllByIsDraftAndParentNode(true,null)

	    def finalizedForms = FormDesign.findAll {
	    	isDraft == false
	    }
	    def draftForms = FormDesign.findAll {
	    	isDraft == true
	    }

	    [
            finalizedPathways : finalizedPathways,
	    	draftPathways : draftPathways,
	    	finalizedForms : finalizedForms,
	    	draftForms : draftForms,
        ]

	}
}
