package uk.co.mdc.model

class UmlModel {

	Integer refId;
	
	Integer conceptId;
	
	String description;
	
	static hasMany = [dataElements: DataElement, valueDomains: ValueDomain]
			
    static constraints = {
    }
}
