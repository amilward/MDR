package uk.co.mdc.forms

class FormRule {
	
	//In Steve's model these are called local definitions not rules
	String name
	String predicate
	String consequence

    static constraints = {
		name nullable:true
		predicate nullable:true
		consequence nullable:true
    }
}
