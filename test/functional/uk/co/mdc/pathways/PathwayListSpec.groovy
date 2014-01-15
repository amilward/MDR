package uk.co.mdc.pathways

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

    def "Check that subpathways are not displayed in the big lists"(){

        given: "I'm on the dashboard"
        driver.manage().window().setSize(new Dimension(1028, 768))
        to LoginPage
        loginRegularUser()
        at DashboardPage


        when: "I go to the dashboard pathways list screen"
        $("#pathways").click()


        then: "The pathways list is displayed and only the top level pathways are listed."

        def table= $("#dashboard-pathways").find("table",0)
        table.displayed


        and: "the pathways list contains a known top-level pathway"
        table.find("a", text: "Transplanting and Monitoring Pathway").displayed

        and: "the pathways list does not contain a known subpathway"
        !table.find("a", text: "Guarding Patient on recovery and transfer to nursing ward").displayed


        when: "I go to the pathways list page"
        nav.goToPathwayListPage()
        at PathwayListPage

        then: "The pathways list is displayed"
        dataTableRows.displayed

        and: "the pathways list contains a known top-level pathway"
        dataTableRows.find("a", text: "Transplanting and Monitoring Pathway").displayed

        and: "the pathways list does not contain a known subpathway"
        !dataTableRows.find("a", text: "Guarding Patient on recovery and transfer to nursing ward").displayed


    }
}
