/**
 * Author: Adam Milward (adam.milward@outlook.com)
 */
package uk.co.mdc.pathways;
import geb.spock.GebReportingSpec

import  uk.co.mdc.authentication.pages.LoginPage
import  uk.co.mdc.authentication.pages.DashboardPage
import uk.co.mdc.pages.pathways.PathwayListPage

import org.openqa.selenium.Dimension

class ListPathwaysSpec extends GebReportingSpec {

	def "List existing pathways using the nav menu"() {

		given:"I am on the dashboard view in a 1024x768 browser window"
		driver.manage().window().setSize(new Dimension(1028, 768))
		to LoginPage
		username = "user1"
		password = "password1"
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
		
		when:"I click pathways -> list pathway"
		nav.listPathwaysLink.click()
		
		
		then: "I go to the list pathways page"
		waitFor{
			at PathwayListPage
		}

		and: "it displays some rows in the data table"
			dataTableRows.size() > 0
		
		
		
	}
	
	def "Search existing pathways from the list page"() {
		
		given:"I am on the dashboard view in a 1024x768 browser window"
		driver.manage().window().setSize(new Dimension(1028, 768))
		to LoginPage
		username = "user1"
		password = "password1"
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
		
		when:"I click pathways -> list pathway"
		nav.listPathwaysLink.click()
		
		
		then: "I go to the list pathways page"
		waitFor{
			at PathwayListPage
		}
		
		and: "it displays some rows in the data table"
		dataTableRows.size() > 0
		
		then "I click in the search box and enter some text"
		
		
	}
	
}