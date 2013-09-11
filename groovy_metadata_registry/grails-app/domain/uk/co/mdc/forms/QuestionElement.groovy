package uk.co.mdc.forms

import uk.co.mdc.model.DataElement
import uk.co.mdc.model.ValueDomain

class QuestionElement extends FormDesignElement {

	DataElement dataElement
	ValueDomain valueDomain

	InputField inputField
	
    static constraints = {
		
		dataElement nullable: true
		valueDomain nullable: true
		inputField nullable: true
    }
	
	//this function will create the chosen input field from the value domain and data type
	//and store it
	def createInputField(){
		
	}
}
