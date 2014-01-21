/**
 * Author: Adam Milward (adam.milward@outlook.com)
 */
package uk.co.mdc.spec.pathways;
import geb.spock.GebReportingSpec

import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.DashboardPage
import uk.co.mdc.pages.pathways.PathwayListPage
import uk.co.mdc.pages.pathways.PathwayShowPage

import org.openqa.selenium.Dimension

class SubPathwaySpec extends GebReportingSpec {

    def setup(){
        driver.manage().window().setSize(new Dimension(1024, 768))
        to LoginPage
        loginRegularUser()
        at DashboardPage

        nav.goToPathwayListPage()
        at PathwayListPage

        goToPathwayInList(0)
        at PathwayShowPage

    }

    def "Identifying parent nodes"(){

        at PathwayShowPage

        def nodeElement
        def newNode

        when: "I create a subpathway"
        def nodeId = getNodeIds()[0]
        createSubpathway( getNode(nodeId) )
        nodeElement = getNode(nodeId)

        then: "The node is highlighted"
        hasParentNodeStyle(nodeElement)

        when: "I create a new node"
        newNode = createNode("I'm here!")

        then: "That node is not marked as a parent"
        !hasParentNodeStyle(newNode)

        cleanup: "Remove our new nodes"
        deleteNode(newNode)

        nodeElement = getNode(nodeId)
        deleteNode(nodeElement)

    }


	def "View a Pathway and add a pathway to a Node on the pathway as admin"() {

        at PathwayShowPage

        when: "I click on a node"
        node2.click()

        then: "the view sub pathway button is visible in the properties panel"
        waitFor {
            viewSubPathwayButton.displayed
        }
        viewSubPathwayButton.@type=="button"

        when: "I click on the view sub pathway button"
        viewSubPathwayButton.click()

        then: "I am taken to the show pathway page for it"
        at PathwayShowPage

        and: "it displays the name of the pathway"
        waitFor{
            pathwayName.text() == "Anaesthesia and Operating Patient."
        }
    }
}
