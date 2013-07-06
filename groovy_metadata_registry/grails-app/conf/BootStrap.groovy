import uk.co.mdc.*

class BootStrap {

	def springSecurityService
	
    def init = { servletContext ->
	
		def userAuth = SecAuth.findByAuthority('ROLE_USER') ?: new SecAuth(authority: 'ROLE_USER').save(failOnError: true)
		def adminAuth = SecAuth.findByAuthority('ROLE_ADMIN') ?: new SecAuth(authority: 'ROLE_ADMIN').save(failOnError: true)

		
		def adminUser = SecUser.findByUsername('admin') ?: new SecUser(
			username: 'admin',
			password: 'admin',
			enabled: true).save(failOnError: true)

	if (!adminUser.authorities.contains(adminAuth)) {
		SecUserSecAuth.create adminUser, adminAuth
	}

	
	//register rest api security filter
	
	SpringSecurityUtils.clientRegisterFilter('apiAuthFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 10)
		
		
		
    }
    def destroy = {
    }
}
