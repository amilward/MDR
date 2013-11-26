package uk.co.mdc.authentication;
import geb.spock.GebSpec

class UserLoginSpec extends GebSpec {
   def "Login screen standard elements"() {
	   when: 'I go to the login screen'
	   go "login"

	   then: ' The title of the page is "Login" and there are the standard components present'
	   title == "Login"
	   $("input", id: "username").@type == "text"
	   $("input", id: "password").@type == "password"
	   $("input", id: "remember_me").@type == "checkbox"
	   $("a", id: "forgottenPasswordLink").@text == "Forgot Password"
	   $("a", id: "registerLink").@text == "Register"
	   report "Login page"
   }
}