package uk.co.mdc.springsecurity.user

import uk.co.mdc.SecAuth
import uk.co.mdc.SecUser
import uk.co.mdc.SecUserSecAuth

class UserController extends grails.plugins.springsecurity.ui.UserController {
	def pendingRole = SecAuth.findByAuthority('ROLE_PENDING')
	def standardUserRole = SecAuth.findByAuthority('ROLE_USER')
	
	def activate() {
		def secUser = SecUser.findById(params.id)
		SecUserSecAuth.create(secUser, standardUserRole, true)
		SecUserSecAuth.remove(secUser, pendingRole, true)
		redirect(controller: "role", action: "listPendingUsers")
	}
}
