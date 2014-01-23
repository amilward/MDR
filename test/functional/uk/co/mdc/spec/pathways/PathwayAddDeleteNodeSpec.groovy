/**
 * Author: Adam Milward (adam.milward@outlook.com)
 */
package uk.co.mdc.spec.pathways;
import geb.error.RequiredPageContentNotPresent
import geb.error.UnresolvablePropertyException
import geb.spock.GebReportingSpec

import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.DashboardPage
import uk.co.mdc.pages.pathways.PathwayListPage
import uk.co.mdc.pages.pathways.PathwayShowPage

import org.openqa.selenium.Dimension

class PathwayAddDeleteNodeSpec extends GebReportingSpec {
	def "View a Pathway add a new node and then delete as admin"() {
		
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
					
					when: "I click on add node"
					addNodeButton.click()
					
					then: "the create node modal pops up"
					waitFor{
						modalLabel.text() == "Create Node"
					}
					
					when: "I fill in the information"
					createNodeName = "testNode"
					createNodeDescription = "testDesc"
					createNodeButton.click()
					
					then: "the node appears in the interface with the testNode title"

					waitFor{
						newNodeTitleDiv.displayed
					}

					when: "I click on the node I have just created"
					newNodeTitleDiv.click()
				
					then: "the delete node button is visible in the properties panel"
					waitFor{
						deleteSelectedElementButton.@type=="button"	
						propertiesName == "testNode"
					}
					
					when: "I click on the delete node button"
					deleteSelectedElementButton.click()
					
					then: "the node is deleted"
					waitFor{
							try {
								newNodeTitleDiv.text() != "testNode"
							} catch (UnresolvablePropertyException e) {
								return true
							} catch (RequiredPageContentNotPresent e) {
								return true
							} 
						}
							
		}
	
}