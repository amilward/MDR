package uk.co.mdc.model.controller

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Specification
import uk.co.mdc.*
import grails.plugins.springsecurity.SpringSecurityService
import org.grails.plugins.springsecurity.service.acl.AclUtilService
import org.grails.plugins.springsecurity.service.acl.AclService
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission


import uk.co.mdc.model.DataElement
import uk.co.mdc.model.ValueDomain
import uk.co.mdc.model.ExternalReference
import uk.co.mdc.model.DataElementController
import uk.co.mdc.model.DataElementService
import uk.co.mdc.model.ValueDomainService

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(DataElementController)
@Mock([DataElement, DataElementService, ValueDomainService, ValueDomain, ExternalReference, AclUtilService,AclService, SecAuth, SecUser, SecUserSecAuth, SpringSecurityService])
class DataElementSpec extends Specification {


	//setup for all the methods
	
	
	
	//test that index goes to list page
	
    def 'index action'() {

        when:
        controller.index()

        then:
        response.redirectUrl.endsWith "list"
    }
	
	
	//test that the ajax request on the list page returns empty list
	
	def 'Ajax dataTables empty List'() {
		
		when:

		controller.dataTables()
		
		then:
		
		controller.response.contentAsString == '{"sEcho":null,"iTotalRecords":0,"iTotalDisplayRecords":0,"aaData":[]}'

		
	}
	
	
	//test that the ajax request on the list page returns a full list
	
	
	def 'Ajax dataTables 1 List'() {
		setup:
		dataElementInstance.save()

		when:

		controller.dataTables()
		
		then:
		
		controller.response.contentAsString == '{"sEcho":null,"iTotalRecords":1,"iTotalDisplayRecords":1,"aaData":[{"class":"uk.co.mdc.model.DataElement","id":1,"dataElementCollections":null,"dataElementConcept":null,"dataElementValueDomains":null,"definition":null,"description":"test description","externalIdentifier":null,"externalReferences":null,"name":"test","parent":null,"refId":"C1600","subElements":null,"synonyms":null}]}'

		
		where:
		dataElementInstance = new DataElement( 
			name:'test',
			refId:"C1600", 
			description:"test description"
		)
	}
	
	//test that the ajax request on the list page works with sort functionality
	
	
	def 'Ajax dataTables 2 Sort'() {
		
		setup:
		
		dataElementInstance1.save()
		dataElementInstance2.save()
		
		params.sSortDir_0 = "asc"
		params.iSortCol_0 = "1"
		
		when:

		controller.dataTables()
		
		then:
		
		controller.response.contentAsString == '{"sEcho":null,"iTotalRecords":2,"iTotalDisplayRecords":2,"aaData":[{"class":"uk.co.mdc.model.DataElement","id":2,"dataElementCollections":null,"dataElementConcept":null,"dataElementValueDomains":null,"definition":null,"description":"other1 description","externalIdentifier":null,"externalReferences":null,"name":"other1","parent":null,"refId":"other1","subElements":null,"synonyms":null},{"class":"uk.co.mdc.model.DataElement","id":1,"dataElementCollections":null,"dataElementConcept":null,"dataElementValueDomains":null,"definition":null,"description":"test description","externalIdentifier":null,"externalReferences":null,"name":"test","parent":null,"refId":"C1600","subElements":null,"synonyms":null}]}'

		
		where:
		dataElementInstance1 = new DataElement(
			name:'test',
			refId:"C1600",
			description:"test description"
		)
		
		dataElementInstance2 = new DataElement(
			name:'other1',
			refId:"other1",
			description:"other1 description"
		)
	}
	
	
	
	def "create action"() {
		setup:
		
		dataElementInstance1.save()
		dataElementInstance2.save()
		
		controller.params.refId = refId
		controller.params.name = name
		controller.params.description = description
		controller.params.definition = definition
		
		when:
		def model = controller.create()

		then:
		model.dataElementInstance != null
		model.dataElementInstance.refId == refId
		model.dataElementInstance.name == name
		model.dataElementInstance.description == description
		model.dataElementInstance.definition == definition

		where:
		refId = "testRef"
		name = "test create name"
		description = "create description"
		definition = "create definition"

		
		dataElementInstance1 = new DataElement(
			name:'test',
			refId:"C1600",
			description:"test description"
		)
		
		dataElementInstance2 = new DataElement(
			name:'other1',
			refId:"other1",
			description:"other1 description"
		)
	}
	
	def 'save action: valid dataElement'() {
		
		setup:
					
			//create user if none exists
			def roleAdmin = SecAuth.findByAuthority('ROLE_ADMIN') ?: new SecAuth(authority: 'ROLE_ADMIN').save(failOnError: true)
		
		
			def admin = new SecUser(username: 'admin', enabled: true, password: 'admin123').save()
			
			admin.metaClass.encodePassword = { -> }
			
			SecUserSecAuth.create admin, roleAdmin, true
			
			
			//login as admin so you can create the prepopulated data
			// have to be authenticated as an admin to create ACLs
			SCH.context.authentication = new UsernamePasswordAuthenticationToken( 'admin', 'admin123', AuthorityUtils.createAuthorityList('ROLE_ADMIN'))

		params.refId = refId
		params.name = name
		params.description = description
		params.definition = definition
		
		when:
		controller.save()

		then:
		response.redirectUrl.endsWith "show/1"
		controller.flash.message != null
		
		cleanup:
		// logout
		SCH.clearContext()
		
		where:
		refId = "testRef"
		name = "test create name"
		description = "create description"
		definition = "create definition"
		

		

	}
/*
	def 'save action: invalid dataElement'() {
		setup:
		controller.params.firstname = firstname
		controller.params.lastname = lastname

		when:
		controller.save()

		then:
		view.endsWith "create"
		model.authorInstance.firstname == firstname
		model.authorInstance.lastname == lastname

		where:
		firstname = "John"
		lastname = ""

	}*/
	
	
}
