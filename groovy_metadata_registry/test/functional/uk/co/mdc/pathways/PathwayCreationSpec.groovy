/**
 * Author: Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
 * 		   Adam Milward (adam.milward@outlook.com)
 */
package uk.co.mdc.pathways;
import geb.spock.GebReportingSpec

import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.DashboardPage
import uk.co.mdc.pages.pathways.PathwayShowPage

import org.openqa.selenium.Dimension

class PathwayCreationSpec extends GebReportingSpec {

	def "Creating a new pathway using the nav menu as admin"() {

		given:"I am on the dashboard view in a 1024x768 browser window"
		driver.manage().window().setSize(new Dimension(1028, 768))
		to LoginPage
		username = "admin"
		password = "admin123"
		submitButton.click(DashboardPage)

		waitFor{
			at DashboardPage
		}
		
		when: "I click the pathways dropdown menu"
		nav.expandPathwayMenuLink.click()
		
		then: "The create pathways link is visible"
		waitFor{
			nav.createPathwayLink.displayed
		}
		
		when:"I click pathways -> create pathway" 
		nav.createPathwayLink.click()
		
		then: "I am presented with a modal which I can enter name and description"
		waitFor{
			nav.pathwayCreationModal.displayed
		}
		nav.newPathwayName.@type == "text"
		nav.newPathwayDescription.@type == "textarea"
		nav.newPathwayVersionNo.@type == "text"
		nav.newPathwayIsDraft.@type == "select-one"
		
		when: "I enter a name and description and click submit"
		def varPathwayName = "Sample Pathway"
		def varDescription = "This is a sample pathway"
		def varVersionNo = "1a"
		def varIsDraft = "false"
		nav.newPathwayName = varPathwayName
		nav.newPathwayDescription = varDescription
		nav.newPathwayVersionNo = varVersionNo
		nav.newPathwayIsDraft = varIsDraft
		nav.newPathwaySubmit.click()
		
		then: "a new pathway is created and I am taken to the show page for it"
		waitFor{
			at PathwayShowPage
		}

		and: "it displays the name of the pathway"
		waitFor{
			pathwayName.text() == varPathwayName
		}
		
		when: "I click on the edit info"
		editInfoButton.click()
		
		
		then: "the update pathway info modal is displayed"
		waitFor{
			updatePathwayModal.displayed
		}
		
		and: "the pathways Info is the same as the info entered when we created the pathway"
		
		
		waitFor{
			pathwayInfoName == varPathwayName
			pathwayInfoDescription == varDescription
			pathwayInfoVersionNo == varVersionNo
			pathwayInfoIsDraft == varIsDraft
		}
		
	}
	
//	def "Login screen standard elements"() {
//		when: 'I go to the login screen'
//		to LoginPage
//
//		then: ' The title of the page is "Login" and there are the standard components present'
//		at LoginPage
//		username.@type == "text"
//		password.@type == "password"
//		rememberMe.@type == "checkbox"
//		forgottenPasswordLink.@text == "Forgot Password"
//		registerLink.@text == "Signup"
//	}
}