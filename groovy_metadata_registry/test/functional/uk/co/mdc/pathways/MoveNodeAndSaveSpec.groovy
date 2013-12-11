/**
 * Author: Adam Milward (adam.milward@outlook.com)
 */
package uk.co.mdc.pathways;
import geb.spock.GebReportingSpec

import  uk.co.mdc.authentication.pages.LoginPage
import  uk.co.mdc.authentication.pages.DashboardPage
import uk.co.mdc.pages.pathways.PathwayListPage
import uk.co.mdc.pages.pathways.PathwayShowPage

import org.openqa.selenium.Dimension

class MoveNodeAndSaveSpec extends GebReportingSpec {
	def "Move a node when viewing a pathway with new position saved"() {
		
			given:"I am on the dashboard view in a 1024x768 browser window and login as admin"
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
					dataTableFirstRowLink.click()
					
					then: "I am taken to the show pathway page for it"
					waitFor{
						at PathwayShowPage
					}
			
					and: "it displays the name of the pathway"
					waitFor{
						pathwayName.text() == "Transplanting and Monitoring Pathway"
					}
					
					when: "I drag and drop the first row of the form list"
					def node2Y = node2.y
					interact {
						dragAndDropBy(node2, 0, 100)
					}
					
					then: "The node position has moved by 100px down"
					node2.y == node2Y + 100
					
					
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
					dataTableFirstRowLink.click()
					
					then: "I am taken to the show pathway page for it"
					waitFor{
						at PathwayShowPage
					}
			
					and: "it displays the name of the pathway"
					waitFor{
						pathwayName.text() == "Transplanting and Monitoring Pathway"
						node2.y == node2Y + 100
					}
					
					
					
					
		}
	
}