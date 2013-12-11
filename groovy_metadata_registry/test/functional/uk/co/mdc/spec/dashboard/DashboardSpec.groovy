package uk.co.mdc.spec.dashboard

import uk.co.mdc.pages.DashboardPage;
import uk.co.mdc.pages.authentication.LoginPage;
import geb.spock.GebReportingSpec;

class DashboardSpec extends GebReportingSpec {

	//When I authenticate with a correct username/password combination
	//and I have navigated directly to the login screen
	//Then I am redirected to the system dashboard
	def "Successful login when going to the login page directly"() {		
		when: 'I enter the correct credentials'
		to LoginPage
		username = "user1"
		password = "password1"
		submitButton.click(DashboardPage)

		then: 'I am redirected to the system dashboard and the navigation elements are visible'
		waitFor{
			at DashboardPage
		}	
		nav.navPresentAndVisible
	}
}
