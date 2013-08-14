package uk.co.mdc.forms

class Field {
	
	String name
	
	FieldType type
	
	String caption
	
	String placeholder
	
	String value
	
	Integer size
	
	Map options
	
	String field_class
	
	String field_id
	
    static constraints = {
		
		placeholder nullable:true
		value nullable:true
		size nullable:true
		options nullable:true
		field_class nullable:true
		field_id nullable:true
		
    }
}
