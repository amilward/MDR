package uk.co.mdc.spec.authentication;
import geb.spock.GebReportingSpec
import uk.co.mdc.pages.DashboardPage;
import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.pathways.*

class UserLoginSpec extends GebReportingSpec {

	def "Login screen standard elements"() {
		when: 'I go to the login screen'
		to LoginPage

		then: ' The title of the page is "Login" and there are the standard components present'
		at LoginPage
		username.@type == "text"
		password.@type == "password"
		rememberMe.@type == "checkbox"
		forgottenPasswordLink.@text == "Forgot Password"
		registerLink.@text == "Signup"
	}
	
	//When I authenticate with an incorrect username/password combination
	//Then an error message is displayed stating the combination is incorrect and I can try again
	def "User authentication attempts (bad)"() {
		when: 'I enter the incorrect credentials'
		to LoginPage
		username = "baduser"
		password = "badpassword"
		submitButton.click(LoginPage)

		then: 'Then an error message is displayed stating the combination is incorrect and I can try again'
		waitFor{ 
			at LoginPage
		}
		invalidUsernameOrPasswordError
		
		when: 'I enter the incorrect credentials again'
		username = "baduser43"
		password = "badpassword1"
		submitButton.click(LoginPage)

		then: 'Then an error message is displayed stating the combination is incorrect and I can try again'
		waitFor{ 
			at LoginPage
		}
		invalidUsernameOrPasswordError
	}
	
	//When I authenticate correctly after using an incorrect username/password combination
	//I am presented with the dashboard
	def "Successful second authentication"() {
		when: 'I enter the incorrect credentials'
		to LoginPage
		username = "baduser"
		password = "badpassword"
		submitButton.click(LoginPage)

		then: 'Then an error message is displayed stating the combination is incorrect and I can try again'
		at LoginPage
		invalidUsernameOrPasswordError
		
		when: 'I enter the correct credentials'
		username = "user1"
		password = "password1"
		submitButton.click(DashboardPage)

		then: 'Then an error message is displayed stating the combination is incorrect and I can try again'
		waitFor{
			at DashboardPage
		}
	}

	//When I authenticate with a correct username/password combination
	//and I have navigated directly to the login screen
	//Then I am redirected to the system dashboard
	def "Successful login when going to the login page directly"() {		
		when: 'I enter the correct credentials'
		to LoginPage
		username = "user1"
		password = "password1"
		submitButton.click(DashboardPage)

		then: 'Then I am redirected to the system dashboard'
		waitFor{
			at DashboardPage
		}
	}
	
	//When I authenticate with a correct username/password combination
	//and I have been redirected to the login screen whilst trying to access another resource
	//and I am authorised to access that resource
	//Then I am redirected to the requested resource
	def "Successful login when attempting to go somewhere else"() {
		when: 'I go to a restricted page and I am not logged in'
		go PathwayListPage.url
		
		then:'I am redirected to the login screen'
		waitFor{
			at LoginPage
		}
		
		when:'I authenticate successfully'
		username = "user1"
		password = "password1"
		submitButton.click(PathwayListPage)

		then: 'I am redirected to my original destination'
		waitFor{
			at PathwayListPage
		}
	}
	
	//When I authenticate with a correct username/password combination
	//and I have been redirected to the login screen whilst trying to access another resource
	//and I am not authorised to view that resource
	//Then I am shown an "access denied" screen
	
	//When I have forgotten my password
	//Then I click on a "forgotten password" link, enter my username or email address and a password reset email is sent to me.
	
	//Given a login screen and I am registered but haven't activated my account
	
	//When I authenticate with a correct username/password combination
	//and I have navigated directly to the login screen
	//Then I am presented an error page saying my account is locked
	
	//Given I am logged in
	//When I click the "log out" button
	//Then my session is terminated and I am returned to the log in page
	
	// Given I am logged in as a standard user
	// When I visit the pathways, form designer or data model sections
	// Then I am able to view them
//	def "Standard user has permissions to view the main areas"(){
//		given:"I am logged in as a standard user"
//		to LoginPage
//		username = "user1"
//		password = "password1"
//		submitButton.click(DashboardPage)
//		
//		when: "I click on the data model link in the navigation bar"
//		nav.modelLink.click()
//		
//		then: "I am taken to the data model page"
//		waitFor{
//			at ModelListPage
//		}
//		
//		when: "I click on the pathways link in the navigation bar"
//		nav.pathwayLink.click()
//		
//		then: "I am taken to the pathways page"
//		waitFor{
//			at PathwayListPage
//		}
//		
//		when: "I click on the form designer link in the navigation bar"
//		nav.formLink.click()
//		
//		then: "I am taken to the form designer page"
//		waitFor{
//			at FormListPage
//		}
//	}
}