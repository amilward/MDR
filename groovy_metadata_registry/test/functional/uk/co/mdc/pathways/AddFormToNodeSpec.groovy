/**
 * Author: Adam Milward (adam.milward@outlook.com)
 */
package uk.co.mdc.pathways;
import geb.spock.GebReportingSpec

import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.DashboardPage
import uk.co.mdc.pages.pathways.PathwayListPage
import uk.co.mdc.pages.pathways.PathwayShowPage

import org.openqa.selenium.Dimension

class AddFormToNodeSpec extends GebReportingSpec {
	def "View a Pathway and add a form to a Node on the pathway as admin"() {
		
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
			
					then: "The list pathways link is visible"
					waitFor{
						nav.listPathwaysLink.displayed
					}
			
					when: "I click pathways -> list pathway"
					nav.listPathwaysLink.click()
			
					then: "I go to the list pathways page"
					waitFor{
						at PathwayListPage
					}
					and: "it displays some rows in the data table"
					dataTableRows.size() > 0
					
					when: "I click on the first pathway link"
					dataTableFirstRowLink.click()
					
					then: "I am taken to the show pathway page for it"
					waitFor{
						at PathwayShowPage
					}
			
					and: "it displays the name of the pathway"
					waitFor{
						pathwayName.text() == "Transplanting and Monitoring Pathway"
					}
					
					when: "I click on a node"
					node2.click()
				
					then: "the add form button is visible in the properties panel"
					addFormButton.@type=="button"
					"Anaesthesia and Operating Patient."
					
					when: "I click on the add form button"
					addFormButton.click()
					
					then: "the add form modal is displayed"
					waitFor{
						addFormModal.displayed
						formDesignTableRows.size() > 0
					}
					
					when: "I drag and drop the first row of the form list"
					interact {
						dragAndDropBy(formDesignTableFirstRow, 0, -175)
					}
					
					then: "the form name is added to the form list"
					def formName = formDesignTableFRLink.text()
					waitFor{
						formDesignCartListFirstItem.text() == formName
					}
					
					
					
		}
	
}