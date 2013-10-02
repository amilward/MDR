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
	
    static constraints = {
		 defaultValue nullable:true
		 placeholder nullable:true
		 maxCharacters nullable:true
		 unitOfMeasure nullable:true
		 dataType nullable:true
		 format nullable:true
		}

	
	
}
