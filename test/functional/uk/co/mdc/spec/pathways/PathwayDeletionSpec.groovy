package uk.co.mdc.spec.pathways

import geb.spock.GebReportingSpec
import org.openqa.selenium.Dimension
import uk.co.mdc.pages.DashboardPage
import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.pathways.PathwayListPage
import uk.co.mdc.pages.pathways.PathwayShowPage

/**
 * Created by Ryan Brooks on 18/12/2013.
 */
class PathwayDeletionSpec extends GebReportingSpec {

    /**
     * Navigate to the pathway show page for every test.
     * @return
     */
    def setup(){
        driver.manage().window().setSize(new Dimension(1028, 768))
        to LoginPage
        loginRegularUser()
        at DashboardPage
        nav.goToPathwayListPage()
        at PathwayListPage
    }

    def "Check deletion can be cancelled"(){

        given: "I am on the show page for a pathway"
        dataTableTMLink.click()
        at PathwayShowPage

        when: "I click on the edit info button"
        editInfoButton.click()

        then: "The delete button is visible but the confirmation is not"
        waitFor{
            editModal.deleteButton.displayed
        }
        !editModal.deleteCancellationButton.displayed && !editModal.deleteConfirmationButton.displayed

        when: "I click on the delete button"
        editModal.deleteButton.click()

        then: "The confirmation is displayed"
        !editModal.deleteButton.displayed && editModal.deleteCancellationButton.displayed && editModal.deleteConfirmationButton.displayed

        when: "I click 'cancel'"
        editModal.deleteCancellationButton.click()

        then: "The confirmation is hidden and the delete button becomes visible again"
        editModal.deleteButton.displayed && !editModal.deleteCancellationButton.displayed && !editModal.deleteConfirmationButton.displayed
    }

    def "Check deletion can be confirmed"(){

        /**
         * Let's create a pathway to delete (so we don't hurt any other tests.
         */
        nav.expandPathwayMenuLink.click()
        waitFor{
            nav.createPathwayLink.displayed
        }
        nav.createPathwayLink.click()
        waitFor{
            nav.pathwayCreationModal.displayed
        }

        nav.newPathwayName = "A special pathway to be deleted"
        nav.newPathwayDescription = "This is a pathway"
        nav.newPathwayVersionNo = "1"
        nav.newPathwayIsDraft = false
        nav.newPathwaySubmit.click()
        waitFor{
            at PathwayShowPage
        }

        def pathwayShowURL = driver.currentUrl
        def matcher = pathwayShowURL =~ /pathwaysModel\/show\/(\d+)$/
        matcher.size() == 1
        def createdPathwayID = matcher[0][1]

        /**
         * Let's quickly check it's there
         */
        when: "I go to the page and click on the link"
        to PathwayListPage
        goToPathway(createdPathwayID)

        then: "I'm at the right page"
        at PathwayShowPage
        driver.currentUrl == pathwayShowURL

        /**
         * Then let's delete it
         */
        when: "I click on the edit modal"
        editInfoButton.click()

        then: "The edit modal pops up and the delete button is visible but the confirmation is not"
        waitFor{
            editModal.deleteButton.displayed
        }
        !editModal.deleteCancellationButton.displayed && !editModal.deleteConfirmationButton.displayed

        when: "I click on the delete button"
        editModal.deleteButton.click()

        then: "The confirmation is displayed"
        !editModal.deleteButton.displayed && editModal.deleteCancellationButton.displayed && editModal.deleteConfirmationButton.displayed

        when: "I click to confirm"
        editModal.deleteConfirmationButton.click()

        then: "We're taken back to the list page but the deleted item isn't present"
        waitFor{
            at PathwayListPage
        }
        !goToPathway(createdPathwayID)  
    }
}
