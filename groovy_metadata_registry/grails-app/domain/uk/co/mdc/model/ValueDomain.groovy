package uk.co.mdc.model

class ValueDomain {

	Integer refid;

	String unitOfMeasure;
	
	String regexDef;
	
	String description;
	
	String datatype;
	
	static hasMany = [dataElements: DataElement]
	
    static constraints = {
    }
}
