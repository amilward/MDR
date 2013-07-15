package uk.co.mdc.model

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

class DataElement {
	
	String name

	Integer refId
	
	String description
	
	String definition
	
	DataElement parent
	
	static hasMany = [subElements: DataElement, dataElementValueDomains: DataElementValueDomain]
	
	static belongsTo = [parent: DataElement]
	
    static constraints = {
		refId unique: true
		parent nullable: true
    }
	
	
	List dataElementValueDomains() {
		return dataElementValueDomains.collect{it.valueDomain}
	}

	//add a valueDomain to list of valueDomains who have signed up the game
	
	List addToDataElementValueDomains(ValueDomain valueDomain) {
		DataElementValueDomain.link(this, valueDomain)
		return dataElementValueDomains()
	}

	//remove a valueDomain from list of valueDomains who have signed up to the game
	
	List removeFromDataElementValueDomains(ValueDomain valueDomain) {
		
		DataElementValueDomain.unlink(this, valueDomain)
		return dataElementValueDomains()
	}
	
	//remove all the valueDomains who have signed up to the game before deleting the game
	
	def prepareForDelete(){
		if(this.dataElementValueDomains.size()!=0){
			this.dataElementValueDomains.each{ p->
				this.removeFromDataElementValueDomains(p.valueDomain)
			}
		}
	}
	
	static final String HELP = "A data element describes a logical unit of data that has precise meaning or precise semantics." + 
	"Alternatively one can understand a data element as something close to a column in a database table.  <br/>" +
	"i.e. Address - City <br/>" +
	"<br/>" +
	"It is important to include a clear data element <strong>definition</strong><br/>" +
	"A good definition is:<br/>" +
	"Precise - The definition should use words that have a precise meaning. Try to avoid words that have multiple meanings or multiple word senses.<br/>" +
	"Concise - The definition should use the shortest description possible that is still clear.<br/>" +
	"Non Circular - The definition should not use the term you are trying to define in the definition itself. This is known as a circular definition.<br/>" +
	"Distinct - The definition should differentiate a data element from other data elements. This process is called disambiguation.<br/>" +
	"Unencumbered - The definition should be free of embedded rationale, functional usage, domain information, or procedural information.<br/>" +
	"<br/><br/>" +
	"An example of a bad data element definition would be:" +
	"City - the name of the city that forms a part of an address" +
	"An example of a better definition would be:" +
	"A component of an address that specifies a location by identification of an urban area.<br/>" +
	"<br/>" +
	"A data element can have a number of different <strong>value domains</strong><br/>" +
	"A value domain expresses the set of allowed values for a data element."
	
	
}
