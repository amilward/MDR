package uk.co.mdc

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import grails.plugins.springsecurity.Secured
import uk.co.mdc.forms.FormDesign


@Secured(['ROLE_USER'])
class DashboardController {

    def pathwayService

	def index() {
		
	    def finalizedPathways = pathwayService.topLevelPathways([isDraft: false])

	    def draftPathways = pathwayService.topLevelPathways([isDraft: true])

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
