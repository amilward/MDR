package uk.co.mdc.model

class Rule {
	
	//maybe we need to add a type to these rules i.e. display rule, validation rule, of maybe 
	//this is part of the language
	
	String name
	String predicate
	String consequence

    static constraints = {
		name nullable:true
		predicate nullable:true
		consequence nullable:true
    }
}
