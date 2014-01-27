package uk.co.mdc.catalogue

import java.util.List;

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

class DataElement extends CatalogueElement {
	
	String name
	
	String description
	
	String definition
	
	DataElement parent
	
	static auditable = true
	
	static searchable = {
		except = ["extension", "relations"]
		spellCheck "include"

    } 
	
	static hasMany = [relations: Relationship]

	static belongsTo = [parent: DataElement]
	
    static constraints = {
		description nullable:true
		parent nullable: true
		definition nullable: true
		name blank: false
    }
	
	static mapping = {
		description type: "text"
		definition type: "text"
	}
	

	
	

	
}
