package uk.co.mdc.pages.registration;

import geb.Browser
import geb.Page
import geb.Module

class RegistrationPage extends Page{
  
  static url = "register/index"
  
  static at = {
    url == "register/index" &&
    title == "Register"
  }
  
  static content = {
    registerForm   { $("#registerForm") }
    username  { registerForm.find('#username') }
    email  { registerForm.find('#email') }
    firstName  { registerForm.find('#firstName') }
    lastName  { registerForm.find('#lastName') }
    password1  { registerForm.find('#password') }
    password2  { registerForm.find('#password2') }

    submitButton() {
      registerForm.find("input", type: "submit")
    }
      
  }
}