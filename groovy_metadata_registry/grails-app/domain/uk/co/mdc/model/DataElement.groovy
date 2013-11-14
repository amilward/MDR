package uk.co.mdc.model

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.json.simple.JSONObject

class DataElement extends ExtensibleObject {
	
	String refId
	
	String externalIdentifier
	
	String name
	
	String description
	
	String definition
	
	DataElement parent
	
	DataElementConcept dataElementConcept
	
	Set synonyms
	
	static auditable = true
	
	static searchable = {
        content: spellCheck 'include'
		except = ["extension"]
    } 
	
	static hasMany = [synonyms: Synonym, subElements: DataElement, dataElementValueDomains: DataElementValueDomain, dataElementCollections: DataElementCollection, externalReferences: ExternalReference]
	
	static belongsTo = [parent: DataElement, dataElementConcept: DataElementConcept]
	
    static constraints = {
		refId unique: true
		parent nullable: true
		dataElementConcept nullable: true
		definition nullable: true
		externalIdentifier nullable:true
		name blank: false
		extension nullable: true
    }
	
	static mapping = {
		description type: 'text'
		definition type: 'text'
		extension sqlType: 'binary(5000)'
		subElements cascade: 'save-update'
	}
	
	/******************************************************************************************************************/
	/**************functions for linking data elements and value domains using dataElementValueDomains class*************************/
	/******************************************************************************************************************/
	
	List dataElementValueDomains() {
		return dataElementValueDomains.collect{it.valueDomain}
	}

	//add a valueDomain to list of valueDomains 
	
	List addToDataElementValueDomains(ValueDomain valueDomain) {
		DataElementValueDomain.link(this, valueDomain)
		return dataElementValueDomains()
	}

	//remove a valueDomain from list of valueDomains 
	
	List removeFromDataElementValueDomains(ValueDomain valueDomain) {
		
		DataElementValueDomain.unlink(this, valueDomain)
		return dataElementValueDomains()
	}
	
	
	/******************************************************************************************************************/
	/****functions for linking data elements to other data elements (synonyms) using DataElementDataElement class************/
	/******************************************************************************************************************/
	
	List synonyms() {
		
		def synonymsR = []
		
		if(synonyms.collect{it.dataElement1Id}[0] == this.id){
			
			def synonymIds = synonyms.collect{it.dataElement2Id}
			
			synonymIds.each{ synonymId->
				synonymsR.add(DataElement.get(synonymId))
			}
			
		}else{
			
			def synonymIds = synonyms.collect{it.dataElement1Id}
			
			synonymIds.each{ synonymId->
				synonymsR.add(DataElement.get(synonymId))
			}
	
		}
		
		return synonymsR
		
	}
	
	/******************************************************************************************************************/
	/****************functions for linking dataElements and Collections using dataElementCollections class***************************/
	/******************************************************************************************************************/

	
	List dataElementCollections() {
		return dataElementCollections.collect{it.collection}
	}

	//add a Collection to list of Collections 
	
	List addToDataElementCollections(Collection collection) {
		DataElementCollection.link(this, collection)
		return dataElementCollections()
	}

	//remove a Collection from list of Collections 
	
	List removeFromDataElementCollections(Collection collection) {
		
		DataElementCollection.unlink(this, collection)
		return dataElementCollections()
	}
	
	/******************************************************************************************************************/
	/*********************remove all the associated valueDomains and collections before deleting data element*****************************/
	/******************************************************************************************************************/
	
	
	def prepareForDelete(){
		
		def dataForDelete
		
		if(this.dataElementValueDomains.size()!=0){
			
			dataForDelete = this.dataElementValueDomains()
			
			dataForDelete.each{ valueDomain->
				this.removeFromDataElementValueDomains(valueDomain)
			}
		}
		
		
		if(this.synonyms.size()!=0){
			
			dataForDelete = this.synonyms()
			
			dataForDelete.each{ synonym->
				this.removeFromSynonyms(synonym)
			}
		}
		
		if(this.dataElementCollections.size()!=0){
		
			dataForDelete = this.dataElementCollections()
			
			dataForDelete.each{ collection->
				this.removeFromDataElementCollections(collection)
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
