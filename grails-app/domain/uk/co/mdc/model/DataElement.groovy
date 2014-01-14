package uk.co.mdc.model

import java.util.List;

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

class DataElement extends ModelElement {
	
	String name
	
	String description
	
	String definition
	
	DataElement parent
	
	static auditable = true
	
	static searchable = {
		except = ["extension", "storage", "relations"]
		spellCheck "include"

    } 
	
	static hasMany = [relations: Relationship, subElements: DataElement]

	static belongsTo = [parent: DataElement]
	
    static constraints = {
		description nullable:true
		parent nullable: true
		definition nullable: true
		name blank: false
		extension nullable: true
    }
	
	static mapping = {
		description type: "text"
		definition type: "text"
		subElements cascade: "save-update"
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
		
	}
	
}
