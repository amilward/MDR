package uk.co.mdc.authentication;
import geb.spock.GebReportingSpec

import uk.co.mdc.authentication.pages.*

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
		when: 'I enter the incorrect credentials'
		to LoginPage
		username = "baduser"
		password = "badpassword"
		submitButton.click()

		then: 'Then an error message is displayed stating the combination is incorrect and I can try again'
		at LoginPage
		invalidUsernameOrPasswordError
		
		when: 'I enter the correct credentials'
		to LoginPage
		username = "user1"
		password = "password1"
		submitButton.click(DashboardPage)

		then: 'Then an error message is displayed stating the combination is incorrect and I can try again'
		waitFor{
			at DashboardPage
		}
	}
	
	//When I authenticate with a correct username/password combination
	//and I have been redirected to the login screen whilst trying to access another resource
	//and I am authorised to access that resource
	//Then I am redirected to the requested resource
	
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
}