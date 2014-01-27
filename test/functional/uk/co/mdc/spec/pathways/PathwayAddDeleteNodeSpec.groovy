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

    def setup() {
        driver.manage().window().setSize(new Dimension(1024, 768))
        to LoginPage
        loginAdminUser()
        at DashboardPage

        nav.goToPathwayListPage()
        at PathwayListPage

        goToPathwayInList(0)

        at PathwayShowPage
    }


    def "View a Pathway add a new node and then delete as admin"() {

        at PathwayShowPage

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

    def "Check that When I create a node with no description then the description is blank" () {

        at PathwayShowPage

        when: "I click on create Node"
        addNodeButton.click()

        then: "the create node modal pops up"
        waitFor{
            modalLabel.text() == "Create Node"
        }

        when: "I fill in the name and click create node"
        createNodeName = "testNode"
        createNodeButton.click()

        then: "the node appears in the interface with the testNode title"

        waitFor{
            newNodeTitleDiv.displayed
        }

        when: "I click on the node I have just created"
        newNodeTitleDiv.click()

        then: "the description of the node is null"
        propertiesDescription == ""

        then: "I delete the node"
        deleteSelectedElementButton.click()

    }

    def "Clear content of the Create node form after the user has cancelled the action"(){
        at PathwayShowPage
        
        when: "I click on create Node"
        addNodeButton.click()

        then: "the create node modal pops up"
        waitFor{
            modalLabel.text() == "Create Node"
        }

        when: "I fill in the name, click cancel node and click on create Node again"
        createNodeName = "testNode"
        createNodeDescription = "testDesc"
        cancelCreateNodeButton.click()
        addNodeButton.click()

        then: "Name and description textboxes are empty"
        createNodeName == ""
        createNodeDescription==""
    }
}