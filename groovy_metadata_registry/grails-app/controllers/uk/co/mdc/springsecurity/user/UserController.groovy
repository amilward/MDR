package uk.co.mdc.springsecurity.user

import grails.plugins.springsecurity.Secured;
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode
import uk.co.mdc.SecAuth
import uk.co.mdc.SecUser
import uk.co.mdc.SecUserSecAuth

/**
 * Controller for user management functions.
 * @author Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
 */
class UserController extends grails.plugins.springsecurity.ui.UserController {
	def pendingRole = SecAuth.findByAuthority('ROLE_PENDING')
	def standardUserRole = SecAuth.findByAuthority('ROLE_USER')
	
	def siteUrl = "$request.scheme://$request.serverName:$request.serverPort$request.contextPath"
	
	/**
	 * Create a user from the passed params
	 */
	def save = {
		def user = lookupUserClass().newInstance(params)
		if (params.password) {
			String salt = saltSource instanceof NullSaltSource ? null : params.username
			user.password = springSecurityUiService.encodePassword(params.password, salt)
		}
		if (!user.save(flush: true)) {
			render view: 'create', model: [user: user, authorityList: sortedRoles()]
			return
		}

		addRoles(user)
		flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])}"
		
		def registrationCode = new RegistrationCode(username: user.username)
		registrationCode.save(flush: true)
		String url = generateLink('resetPassword', [t: registrationCode.token])
		
		sendMail {
			to "${user.firstName} ${user.lastName}<${user.email}>"
			// FIXME needs to be refactored into a messages class - i18n support
			subject "Model Catalogue user account details"
			html( g.render(template: "userCreatedEmail", model: [user: user, resetLink: url, mcUrl: siteUrl] ))
		  }
		
		redirect action: edit, id: user.id
	}
	
	/**
	 * Activate a user in the pending state.
	 * May only be carried out by admins
	 */
	@Secured(['ROLE_ADMIN'])
	def activate() {
		// TODO this should validate that the user is in the pending status
		def user = SecUser.findById(params.id)
		SecUserSecAuth.create(user, standardUserRole, true)
		SecUserSecAuth.remove(user, pendingRole, true)
		

		
		sendMail {
			to "${user.firstName} ${user.lastName}<${user.email}>"
			// FIXME needs to be refactored into a messages class - i18n support
			subject "Model Catalogue account activated!"
			html(g.render(template: "userActivatedEmail", model: [user: user, mcUrl: siteUrl]))
		  }
		
		redirect(controller: "role", action: "listPendingUsers")
	}
	
	
	protected String generateLink(String action, linkParams) {
		createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
				controller: 'register', action: action,
				params: linkParams)
	}
}
