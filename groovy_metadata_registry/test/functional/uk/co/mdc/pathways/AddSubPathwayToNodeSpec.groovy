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

class AddSubPathwayToNodeSpec extends GebReportingSpec {
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
					waitFor{
						dataTableRows.size() > 0
					}
					
					
					when: "I click on the first pathway link"
					def pName = dataTableTMLink.text()
					dataTableTMLink.click()
					
					then: "I am taken to the show pathway page for it"
					waitFor{
						at PathwayShowPage
					}
			
					and: "it displays the name of the pathway"
					waitFor{
						pathwayName.text() == pName
					}
					
					when: "I click on a node"
					node2.click()
				
					then: "the add sub pathway button is visible in the properties panel"
					addSubPathwayButton.@type=="button"
					
					when: "I click on the add sub pathway button"
					addSubPathwayButton.click()
					
					then: "a subPathway is added, the view model is updated and the view subPathway button is displayed"
					waitFor{
						viewSubPathwayButton.displayed
						viewSubPathwayButton.@type=="button"
					}
					
					when: "I click on the view sub pathway button"
					viewSubPathwayButton.click()

					then: "I am taken to the show pathway page for it"
					waitFor{
						at PathwayShowPage
					}
					
					and: "it displays the name of the pathway"
					waitFor{
						pathwayName.text() == "Anaesthesia and Operating Patient."
					}
					
		}
	
}
