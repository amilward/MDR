package uk.co.mdc.spec.pathways

import geb.spock.GebReportingSpec
import org.openqa.selenium.Keys
import uk.co.mdc.pages.DashboardPage
import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.pathways.PathwayListPage
import uk.co.mdc.pages.pathways.PathwayShowPage

/**
 * Created by rb on 22/01/2014.
 */
class NodePropertiesSpec extends GebReportingSpec{

    def newName = "My new name prefix "
    def newDesc = "A swanky description"

    def setup(){
        to LoginPage
        //loginRegularUser()
        loginAdminUser()
        at DashboardPage

        nav.goToPathwayListPage()
        at PathwayListPage

        getPathwayLinks()[0].click()
        at PathwayShowPage
    }
    def "Updating properties and clicking out is persisted instantly"(){

        when: "I edit the name of a node and leave the textbox and refresh the page"
        //FIXME Replace with shortcuts in PathwayShowPage (they're on my other machine so I'm hard-coding to tick off the bug)
        def node = pathwayCanvas.find(".node")[1]
        node.click()

        def oldName = $('#txt-properties-name').value()

        $('#txt-properties-name').click()
        $('#txt-properties-name') <<  newName
        pathwayName.click() // clicking anywhere should do it
        waitFor {
            node.text() == newName + oldName
        }
        go driver.currentUrl // refresh() doesn't actually do anything :(

        at PathwayShowPage
        node = pathwayCanvas.find(".node")[1]

        then: "The new name is still present"
        node.click()
        $('#txt-properties-name').value() != oldName
        $('#txt-properties-name').value() == newName + oldName

    }

    def "Updating properties when pressing return is persisted instantly"(){

        when: "I edit the name of a node and leave the textbox and refresh the page"
        //FIXME Replace with shortcuts in PathwayShowPage (they're on my other machine so I'm hard-coding to tick off the bug)
        def node = pathwayCanvas.find(".node")[1]
        node.click()

        def oldName = $('#txt-properties-name').value()

        $('#txt-properties-name').click()
        $('#txt-properties-name') <<  newName
        $('#txt-properties-name') <<  Keys.ENTER
        pathwayName.click() // clicking anywhere should do it
        waitFor {
            node.text() == newName + oldName
        }

        go driver.currentUrl // refresh() doesn't actually do anything :(

        at PathwayShowPage
        node = pathwayCanvas.find(".node")[1]

        then: "The new name is still present"
        node.click()
        $('#txt-properties-name').value() != oldName
        $('#txt-properties-name').value() == newName + oldName




        //when: "I edit the description of a node and leave the textbox and refresh the page"
        //def oldDesc = $('#txt-properties-desc').value()

        //then: "The new description is still present"

        //cleanup:
        //node = pathwayCanvas.find(".node")[0]
        //node.click()
        //$('#txt-properties-name').value(oldName)
        //$('#txt-properties-desc').value(oldDesc)

    }
}