/**
 * Author: Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
 * 		   Adam Milward (adam.milward@outlook.com)
 */
package uk.co.mdc.spec.pathways;
import geb.spock.GebReportingSpec

import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.DashboardPage
import uk.co.mdc.pages.pathways.PathwayShowPage

import org.openqa.selenium.Dimension

class PathwayCreationSpec extends GebReportingSpec {

    def setup(){
        driver.manage().window().setSize(new Dimension(1028, 768))
        to LoginPage
        loginRegularUser()
        at DashboardPage

    }
	def "Creating a new pathway using the nav menu as admin"() {

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
            pathwayDescription.text() == varDescription
            pathwayUserVersion.text() == varVersionNo
            //pathway.isDraft.text() == varIsDraft

			pathwayCanvas.height == 3000
			pathwayCanvas.width == 3000
		}

        // FIXME go to list and confirm it's right there too
	}
}
