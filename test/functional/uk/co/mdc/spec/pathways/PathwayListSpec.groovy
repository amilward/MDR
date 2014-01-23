package uk.co.mdc.spec.pathways

import geb.spock.GebReportingSpec
import org.openqa.selenium.Dimension
import uk.co.mdc.pages.DashboardPage
import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.pathways.PathwayListPage

/**
 * A specification for lists of pathways, including the ones
 * on the dashboard and pathways list page.
 *
 * Created by Ryan, Soheil and Susana on 15/01/2014.
 */
class PathwayListSpec extends GebReportingSpec{

    def setup(){
        driver.manage().window().setSize(new Dimension(1028, 768))
        to LoginPage
        loginRegularUser()
        at DashboardPage
    }

    def "Check that subpathways are not displayed in the dashboard list"(){

        when: "I go to the dashboard pathways list screen"
        $("#pathways").click()


        then: "The pathways list is displayed and only the top level pathways are listed."
        pathwaysTable.displayed


        and: "the pathways list contains a known top-level pathway"
        getPathwayLink("Transplanting and Monitoring Pathway").displayed

        and: "the pathways list does not contain a known subpathway"
        !getPathwayLink("Guarding Patient on recovery and transfer to nursing ward").displayed
    }

    def "Check that subpathways are not displayed on the pathways list page"(){

        when: "I go to the pathways list page"
        nav.goToPathwayListPage()
        at PathwayListPage

        then: "The pathways list is displayed"
        dataTableRows.displayed

        and: "the pathways list contains a known top-level pathway"
        getPathwayLink("Transplanting and Monitoring Pathway").displayed

        and: "the pathways list does not contain a known subpathway"
        !getPathwayLink("Guarding Patient on recovery and transfer to nursing ward").displayed


    }
}
