package uk.co.mdc.spec.dashboard

import uk.co.mdc.pages.DashboardPage;
import uk.co.mdc.pages.authentication.LoginPage;
import geb.spock.GebReportingSpec
import uk.co.mdc.pages.forms.FormShowPage
import uk.co.mdc.pages.pathways.PathwayShowPage;

class DashboardSpec extends GebReportingSpec {

	//When I authenticate with a correct username/password combination
	//and I have navigated directly to the login screen
	//Then I am redirected to the system dashboard
	def "Viewing the dashboard from login"() {
		given: "I'm at the login page"
		to LoginPage

        when: "I login"
        loginRegularUser()

        then: "The dashboard is shown"
        at DashboardPage
        nav.navPresentAndVisible
	}

    def "Navigating to a pathway using the dashboard"(){
        given: "I'm at the dashboard"
        to LoginPage
        loginRegularUser()
        at DashboardPage

        when: "I go to the pathways list and click on a link"
        goToPathwaysScreen()
        getPathwaysLinks()[0].click()

        then:
        at PathwayShowPage
    }

    def "Navigating to a form using the dashboard"(){
        given: "I'm at the dashboard"
        to LoginPage
        loginRegularUser()
        at DashboardPage

        when: "I go to the pathways list and click on a link"
        goToFormsScreen()
        getFormsLinks()[0].click()

        then:
        at FormShowPage
    }
}
