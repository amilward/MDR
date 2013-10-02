package uk.co.mdc.forms

import uk.co.mdc.model.DataElement
import uk.co.mdc.model.ValueDomain

class QuestionElement extends FormDesignElement {

	//N.B. Potentially build in mapping class QuestionElement_DataElement rather than storing here
	DataElement dataElement
	ValueDomain valueDomain

	String questionNumber
	String prompt 
	String additionalInstructions
	InputField inputField
	
    static constraints = {
		
		dataElement nullable: true
		valueDomain nullable: true
		inputField nullable: true
		prompt nullable: true
		additionalInstructions nullable: true
		questionNumber nullable: true
    
	}
	
	//this function will create the chosen input field from the value domain and data type
	//and store it
	def createInputField(){
		
	}
}
