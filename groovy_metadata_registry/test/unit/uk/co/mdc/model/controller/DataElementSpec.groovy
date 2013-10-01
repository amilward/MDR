package uk.co.mdc.model.controller

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.DataElementController
import uk.co.mdc.model.DataElementService

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(DataElementController)
@Mock([DataElement, DataElementService])
class DataElementSpec extends Specification {


    def 'index action'() {

        when:
        controller.index()

        then:
        response.redirectUrl.endsWith "list"
    }
	
	
	def 'Ajax dataTables empty List'() {
		
		when:

		controller.dataTables()
		
		then:
		
		controller.response.contentAsString == '{"sEcho":null,"iTotalRecords":0,"iTotalDisplayRecords":0,"aaData":[]}'

		
	}
	
	
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
}
