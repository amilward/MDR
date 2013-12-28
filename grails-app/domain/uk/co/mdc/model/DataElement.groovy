package uk.co.mdc.model

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

class DataElement extends ModelElement {
	
	String name
	
	String description
	
	String definition
	
	DataElement parent
	
	DataElementConcept dataElementConcept
	
	Set relations
	
	static auditable = true
	
	static searchable = {
        content: spellCheck 'include'
		except = ["extension"]
    } 
	
	static hasMany = [subElements: DataElement, dataElementValueDomains: DataElementValueDomain, dataElementCollections: DataElementCollection]

	static belongsTo = [parent: DataElement, dataElementConcept: DataElementConcept]
	
    static constraints = {
		description nullable:true
		parent nullable: true
		dataElementConcept nullable: true
		definition nullable: true
		name blank: false
		extension nullable: true
		relations nullable: true
    }
	
	static mapping = {
		description type: "text"
		definition type: "text"
		subElements cascade: "save-update"
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
	/****functions for linking data elements to other data elements (relations) using DataElementDataElement class************/
	/******************************************************************************************************************/
	
	List relations() {
		
		def relationsR = []
		
		if(relations.collect{it.dataElement1Id}[0] == this.id){
			
			def relationshipIds = relations.collect{it.dataElement2Id}
			
			relationshipIds.each{ relationshipId->
				relationsR.add(DataElement.get(relationshipId))
			}
			
		}else{
			
			def relationshipIds = relations.collect{it.dataElement1Id}
			
			relationshipIds.each{ relationshipId->
				relationsR.add(DataElement.get(relationshipId))
			}
	
		}
		
		return relationsR
		
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
		
		
		if(this.relations.size()!=0){
			
			dataForDelete = this.relations()
			
			dataForDelete.each{ relationship->
				this.removeFromRelations(relationship)
			}
		}
		
		if(this.dataElementCollections.size()!=0){
		
			dataForDelete = this.dataElementCollections()
			
			dataForDelete.each{ collection->
				this.removeFromDataElementCollections(collection)
			}
		}
		
	}
	
}
