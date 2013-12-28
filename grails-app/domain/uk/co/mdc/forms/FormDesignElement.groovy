package uk.co.mdc.forms

import uk.co.mdc.model.ModelElement;

class FormDesignElement extends ModelElement  {
	
	String label
	String style
	
	//I'm assuming
	Integer designOrder
	
	//N.B. At present rules contains the hide/show logic for the given element
	
	static hasMany = [rules: Rule]
	static belongsTo = [formDesign: FormDesign]
	
    static constraints = {
		label nullable: true
		style nullable:true
		formDesign nullable:true
    }
	
}
