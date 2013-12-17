package uk.co.mdc.forms

/* ******************************************************
 *
 * THIS IS OLD CODE THAT WILL HAVE TO BE REMOVED
 *
 * */


import uk.co.mdc.model.Collection

class FormSpecification {
	
	Collection collection
	
	String name 
	
	static hasMany = [fields: Field]
	
	static belongsTo = [collection: Collection]
	
    static constraints = {
    }
}
