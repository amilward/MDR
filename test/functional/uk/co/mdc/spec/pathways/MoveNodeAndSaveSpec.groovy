/**
 * Author: Adam Milward (adam.milward@outlook.com)
 */
package uk.co.mdc.spec.pathways;
import geb.spock.GebReportingSpec

import  uk.co.mdc.pages.authentication.LoginPage
import  uk.co.mdc.pages.DashboardPage
import uk.co.mdc.pages.pathways.PathwayListPage
import uk.co.mdc.pages.pathways.PathwayShowPage

import org.openqa.selenium.Dimension

class MoveNodeAndSaveSpec extends GebReportingSpec {

    def setup(){
        driver.manage().window().setSize(new Dimension(1028, 768))
        to LoginPage
        loginRegularUser()
        //loginAdminUser()
        at DashboardPage

        nav.goToPathwayListPage()
        at PathwayListPage

        getPathwayLinks()[0].click()
        at PathwayShowPage
    }

    def "Move a node when viewing a pathway with new position saved"() {

	    given:"I am on the dashboard view in a 1024x768 browser window and login as admin"

	    when: "I drag and drop the first row of the form list"
	    def node2Y = node2.y
	    interact {
	    	dragAndDropBy(node2, 0, 100)
	    }

	    then: "The node position has moved by 100px down"
	    node2.y == node2Y + 100

	    when: "I go back to the pathway"
        nav.goToPathwayListPage()
        at PathwayListPage
	    dataTableTMLink.click()

        then: "it displays the name of the pathway"
        at PathwayShowPage
        waitFor{
            node2.y == node2Y + 100
        }

	}
}