package uk.co.mdc.forms

/* ******************************************************
 *
 * THIS IS OLD CODE THAT WILL HAVE TO BE REMOVED
 *
 * */


import uk.co.mdc.model.Model

class FormSpecification {
	
	Model collection
	
	String name 
	
	static hasMany = [fields: Field]
	
	static belongsTo = [collection: Model]
	
    static constraints = {
    }
}
