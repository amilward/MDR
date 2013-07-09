import uk.co.mdc.*
import uk.co.mdc.model.ValueDomain
import uk.co.mdc.model.DataElement
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils


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
			
		//populate with test data
		
		if (!DataElement.count()) {
			new DataElement(name:"average_speed", refId:1, description:"average speed of vehicle", definition:"total distance travelled divided by total journey time").save(failOnError: true)
			new DataElement(name:"average_monthly_rainfall", refId:2, description:"average rainfall over the course of a given month", definition:"total annual rainfall divided by 12").save(failOnError: true)
		}

		if (!ValueDomain.count()) {
			new ValueDomain(name:"speed", refId:1, description:"speed", dataType:"float", unitOfMeasure:"mph", regexDef:"sss").save(failOnError: true)
			new ValueDomain(name:"volume", refId:2, description:"volume", dataType:"float", unitOfMeasure:"cm3", regexDef:"sss").save(failOnError: true)
		}
		
		
    }
    def destroy = {
    }
}
