package uk.co.mdc.forms

import uk.co.mdc.model.Collection

class FormSpecification {
	
	Collection collection
	
	String name 
	
	static hasMany = [fields: Field]
	
	static belongsTo = [collection: Collection]
	
    static constraints = {
    }
}
