package uk.co.mdc.model

class DataElement {

	
	Integer refId;
	
	Integer parentId;
	
	Integer vdId;
	
	String description;
	
	String definition;
	
	static hasMany = [subElements: DataElement]
	
    static constraints = {
    }
}
