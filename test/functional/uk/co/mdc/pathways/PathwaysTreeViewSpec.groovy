/**
 * Author: Adam Milward (adam.milward@outlook.com)
 */
package uk.co.mdc.pathways;
import geb.spock.GebReportingSpec

import  uk.co.mdc.pages.authentication.LoginPage
import  uk.co.mdc.pages.DashboardPage
import uk.co.mdc.pages.pathways.PathwayListPage
import uk.co.mdc.pages.pathways.PathwayShowPage

import org.openqa.selenium.Dimension

class PathwaysTreeViewSpec extends GebReportingSpec {
	def "Navigate to a pathway view tree view and click on subtree node"() {
		
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
					
					when: "I click on the correct pathway link"
					def pName = dataTableTMLink.text()
					dataTableTMLink.click()
					
					then: "I am taken to the show pathway page for it"
					waitFor{
						at PathwayShowPage
					}
			
					and: "it displays the pathway name and tree view with 3 nodes (1 with a sub pathway)"
					waitFor{
						pathwayName.text() == pName
						checkBox.value() == "on" 
						treeLevel1.size() == 2
					}
					
					when: "I click on the node with a subpathway"
					js.exec("document.getElementById('cb8').click()")
					
					then: "the tree view expands to show the nodes in the subpathway"
					waitFor{
						treeLevel2.size()==3
					}
					
					when: "I click on one of the nodes"
					guardPatientNode.click()
					
					then: "the subpathway for that node is displayed"
					waitFor{
						pathwayName.text() == "Guarding Patient on recovery and transfer to nursing ward"
					}
					
		}
}