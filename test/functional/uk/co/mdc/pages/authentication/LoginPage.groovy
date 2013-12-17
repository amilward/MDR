package uk.co.mdc.pages.authentication;

import geb.Browser
import geb.Page
import geb.Module

class LoginPage extends Page{
	
	static url = "login/auth"
	
	static at = {
		url == "login/auth" &&
		title == "Login"
	}
	
	static content = {
		loginForm 	{ $("#loginForm") }
		username 	{ loginForm.find('#username') }
		password 	{ loginForm.find('#password') }
		rememberMe 	{ loginForm.find('#remember_me') }

		submitButton() {
			loginForm.find("input", type: "submit")
		}
		
		forgottenPasswordLink 	{ $("a", id: "forgottenPasswordLink") }
		registerLink 			{ $("a", id: "registerLink") }
		
		errors(required:false) { $(".login_message p") }

		invalidUsernameOrPasswordError(required:false) {
			errors.filter(text:contains("Sorry, we were not able to find a user with that username and password."))
		}
	}
}