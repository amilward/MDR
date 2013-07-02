package uk.co.mdc.model

class ConceptualDomain {

	Integer refid;
	
	String description;
	
	static hasMany = [valueDomains: ValueDomain]
	
    static constraints = {
    }
}
