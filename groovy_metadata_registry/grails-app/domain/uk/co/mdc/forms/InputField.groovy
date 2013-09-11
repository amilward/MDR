package uk.co.mdc.forms

import java.util.Map;

import uk.co.mdc.model.DataType

class InputField {
	
	String defaultValue
	String placeholder
	Integer maxCharacters
	String unitOfMeasure
	DataType dataType
	String format
	Map options
	String instructions
	///maybe turn this into an enumeration
	String renderType
	
    static constraints = {
		 defaultValue nullable:true
		 placeholder nullable:true
		 maxCharacters nullable:true
		 unitOfMeasure nullable:true
		 dataType nullable:true
		 format nullable:true
		 options nullable:true
		 instructions nullable:true
		 renderType nullable:false
		}

	
	
}
