package uk.co.mdc.register

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode

/**
 * Overridden to provide firstName/lastName support
 * @author Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
 *
 */
class RegisterController extends grails.plugins.springsecurity.ui.RegisterController {
	
	/**
	 * Overridden index because we need to use the RegisterCommand object from this class.
	 */
	def index = {
		def copy = [:] + (flash.chainedParams ?: [:])
		copy.remove 'controller'
		copy.remove 'action'
		[command: new RegisterCommand(copy)]
	}
	
	/**
	 * Overridden register action to provide firstname/lastname support
	 */
	def register = { RegisterCommand command ->

		if (command.hasErrors()) {
			render view: 'index', model: [command: command]
			return
		}

		String salt = saltSource instanceof NullSaltSource ? null : command.username
		def user = lookupUserClass().newInstance(
			email: command.email, 
			username: command.username,
			firstName: command.firstName,
			lastName: command.lastName,
			accountLocked: true, 
			enabled: true
		)

		RegistrationCode registrationCode = springSecurityUiService.register(user, command.password, salt)
		if (registrationCode == null || registrationCode.hasErrors()) {
			// null means problem creating the user
			flash.error = message(code: 'spring.security.ui.register.miscError')
			flash.chainedParams = params
			redirect action: 'index'
			return
		}

		String url = generateLink('verifyRegistration', [t: registrationCode.token])

		def conf = SpringSecurityUtils.securityConfig
		def body = conf.ui.register.emailBody
		if (body.contains('$')) {
			body = evaluate(body, [user: user, url: url])
		}
		mailService.sendMail {
			to command.email
			from conf.ui.register.emailFrom
			subject conf.ui.register.emailSubject
			html body.toString()
		}

		render view: 'index', model: [emailSent: true]
	}
	
	static final betterPasswordValidator = { String password, command ->
		// Username cannot be password
		if (command.username && command.username.equals(password)) {
			return 'command.password.error.username'
		}
		
		if (!checkPasswordMinLength(password, command) ||
			!checkPasswordMaxLength(password, command)){
			return 'command.password.error.length'
		}
			
		if (!checkPasswordMinLength(password, command) ||
			!checkPasswordMaxLength(password, command) ||
			!checkPasswordRegex(password, command)) {
			return 'command.password.error.strength'
		}
	}
	
	static boolean checkPasswordMinLength(String password, command) {
		def conf = SpringSecurityUtils.securityConfig

		int minLength = conf.ui.password.minLength instanceof Number ? conf.ui.password.minLength : 8

		password && password.length() >= minLength
	}

	static boolean checkPasswordMaxLength(String password, command) {
		def conf = SpringSecurityUtils.securityConfig

		int maxLength = conf.ui.password.maxLength instanceof Number ? conf.ui.password.maxLength : 64

		password && password.length() <= maxLength
	}

	static boolean checkPasswordRegex(String password, command) {
		def conf = SpringSecurityUtils.securityConfig

		String passValidationRegex = conf.ui.password.validationRegex ?:
				'^.*(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$'

		password && password.matches(passValidationRegex)
	}

}

/**
 * Overridden to provide firstName/lastName support
 * @author Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
 *
 */
class RegisterCommand {
	
		String username
		String email
		String firstName
		String lastName
		String password
		String password2
	
		def grailsApplication
	
		static constraints = {
			username blank: false, nullable: false, validator: { value, command ->
				if (value) {
					def User = command.grailsApplication.getDomainClass(SpringSecurityUtils.securityConfig.userLookup.userDomainClassName).clazz
					if (User.findByUsername(value)) {
						return 'registerCommand.username.unique'
					}
					if(value.length() > 64 || value.length() < 6){
						return 'registerCommand.username.length'
					}
					if(value.contains(' ')){
						return 'registerCommand.username.spaces'
					}
				}
			}
			email blank: false, nullable: false, email: true
			password blank: false, nullable: false, validator: uk.co.mdc.register.RegisterController.betterPasswordValidator
			password2 validator: uk.co.mdc.register.RegisterController.password2Validator
		}
	}

class ResetPasswordCommand {
	String username
	String password
	String password2

	static constraints = {
		username nullable: false
		password blank: false, nullable: false, validator: uk.co.mdc.register.RegisterController.betterPasswordValidator
		password2 validator: uk.co.mdc.register.RegisterController.password2Validator
	}
}