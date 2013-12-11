package uk.co.mdc.pages.registration;

import geb.Browser
import geb.Page
import geb.Module

class RegistrationErrorPage extends RegistrationPage{
  
  static url = "register/register"
  
  static at = {
    url == "register/register" &&
    title == "Register"
  }
  
  static content = {
    errors(required:false) { $("td.errors") }

    usernameError(required:false) {
      errors.has("input", id: "username").find("span", 0, class: "s2ui_error").text()
    }
    passwordError(required:false) {
		//$("span", 0, class: "s2ui_error").text()
      errors.has("input", id: contains("password") ).find("span", 0, class: "s2ui_error").text()
    }
      
  }
}