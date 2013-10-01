package uk.co.mdc.model.domain

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

import grails.plugin.spock.UnitSpec
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.DataElementValueDomain
import uk.co.mdc.model.ValueDomain
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(DataElement)
class DataElementSpec extends Specification{

   def "find DataElement by name"() {
    setup:
    mockDomain(DataElement)

    when:
    def dataElement1 = new DataElement( 
			name:name,
			refId:"C1600", 
			description:"This identifies the source of referral of each Consultant Out-Patient Episode."
		).save()

    then:
    DataElement.findByName(name) !=null

    where:
    name = 'SOURCE OF REFERRAL FOR OUT-PATIENTS'
  }
   
   
   def "refId unique constraints"() {
	   setup:
	   mockDomain(DataElement)
   
	   when:
	   def dataElement1 = new DataElement(
			   name:'someName',
			   refId:refId1,
			   description:"This identifies the source of referral of each Consultant Out-Patient Episode."
		   ).save()
		   
		   def dataElement2 = new DataElement(
			   name:'anotherNAme',
			   refId:refId2,
			   description:"This identifies the source of referral of each Consultant Out-Patient Episode."
		   )
	
		   dataElement2.validate()
   
	   then:

	   dataElement2.hasErrors() == !valid
   
	   where:
	   refId1 | refId2 | valid
	   'sameRefId' | 'sameRefId' | false
	   'RefId1' | 'RefId2' | true
	   
	 }
   
   def "list Value Domains"() {
	   setup:
	   
	   mockDomain(DataElement)
   
	   when:
	   
	   
	   def dataElement = new DataElement(name:"GENERAL MEDICAL PRACTICE CODE (PATIENT REGISTRATION)",
																	refId:"CR0120",
																	description:"The GENERAL MEDICAL PRACTICE CODE (PATIEN").save()
	   then:
	   
	   dataElement.dataElementValueDomains() == []
   
	   
	 }
   
   
}
