package uk.co.mdc.spec.registration;
import geb.spock.GebReportingSpec

import uk.co.mdc.pages.registration.RegistrationPage
import uk.co.mdc.pages.registration.RegistrationErrorPage

class RegistrationSpec extends GebReportingSpec {
	
	static String validUsername = "user55"
	static String invalidSpaceyUsername = "This is a username"
	static String invalidShortUsername = "me"
	static String invalidLongUsername = "thisisahugeusernamethisisahugeusernamethisisahugeusernamethisisahugeusernamethisisahugeusernamethisisahugeusernamethisisahugeusername"
	
	static String validPassword1 = "asd123!@£"
	static String validPassword2 = "asd!@£123"
	static String validPassword3 = "!@£asd123"
	static String validPassword4 = "!@£123asd"
	static String validPassword5 = "123asd!@£"
	static String validPassword6 = "123!@£asd"
	static String invalidShortPass = "a1!"
	static String invalidShortWeakPass = "a"
	static String invalidWeakPass = "thisisalongbutinsecurepassword1"
	static String invalidLongPass = "thisisalongbutinsecurepassword1thisisalongbutinsecurepassword1thisisalongbutinsecurepassword1thisisalongbutinsecurepassword1thisisalongbutinsecurepassword1"
	
	static String validEmail = "sample@example.com"
	
	static String validfirstName = "John"
	static String validlastName = "Smith"
	
	
	def "Registration username validation"(){
		
		given:"The registration page"
		to RegistrationPage
		
		when: "The username is too short"
		username = invalidShortUsername
		email = validEmail
		firstName = validfirstName
		lastName = validlastName
		password1 = validPassword1
		password2 = validPassword1
		submitButton.click(RegistrationErrorPage)
		
		then:
		waitFor{
			at RegistrationErrorPage
		}
		usernameError == "Username must be between 6 and 64 characters"
		
		when: "The username is too long"
		username = invalidLongUsername
		email = validEmail
		firstName = validfirstName
		lastName = validlastName
		password1 = validPassword1
		password2 = validPassword1
		submitButton.click(RegistrationErrorPage)
		
		then:
		waitFor{
			at RegistrationErrorPage
		}
		usernameError == "Username must be between 6 and 64 characters"
		
		when: "The username has spaces in it"
		username = invalidSpaceyUsername
		email = validEmail
		firstName = validfirstName
		lastName = validlastName
		password1 = validPassword1
		password2 = validPassword1
		submitButton.click(RegistrationErrorPage)
		
		then:
		waitFor{
			at RegistrationErrorPage
		}
		usernameError == "Username may not contain spaces"
	}
	
	def "Registration short password validation"(){
		
		given:"The registration page"
		to RegistrationPage
		
		when: "The password is too short"
		username = validUsername
		email = validEmail
		firstName = validfirstName
		lastName = validlastName
		password1 = invalidShortPass
		password2 = invalidShortPass
		submitButton.click(RegistrationErrorPage)
		
		then:
		waitFor{
			at RegistrationErrorPage
		}
		passwordError == "Password must be between 8 and 64 characters"
	}
	
	def "Registration long password validation"(){
		
		given:"The registration page"
		to RegistrationPage
		
		when: "The password is too short"
		username = validUsername
		email = validEmail
		firstName = validfirstName
		lastName = validlastName
		password1 = invalidLongPass
		password2 = invalidLongPass
		submitButton.click(RegistrationErrorPage)
		
		then:
		waitFor{
			at RegistrationErrorPage
		}
		passwordError == "Password must be between 8 and 64 characters"
	}
	
	def "Registration weak password validation"(){
		
		given:"The registration page"
		to RegistrationPage
		
		when: "The password is too short"
		username = validUsername
		email = validEmail
		firstName = validfirstName
		lastName = validlastName
		password1 = invalidWeakPass
		password2 = invalidWeakPass
		submitButton.click(RegistrationErrorPage)
		
		then:
		waitFor{
			at RegistrationErrorPage
		}
		passwordError == "Password must be between 8 and 64 characters and have at least one letter, number, and special character: !@#\$%^&"
	}
}