package uk.co.mdc.forms

import uk.co.mdc.model.DataElement
import uk.co.mdc.model.ValueDomain

class QuestionElement extends FormDesignElement implements Comparable{

	//N.B. Potentially build in mapping class QuestionElement_DataElement rather than storing here
	DataElement dataElement
	ValueDomain valueDomain

	String questionNumber
	String prompt 
	String additionalInstructions
	InputField inputField
	
	static belongsTo = [section: FormDesignElement]
	
    static constraints = {
		
		dataElement nullable: true
		valueDomain nullable: true
		inputField nullable: true
		prompt nullable: true
		additionalInstructions nullable: true
		questionNumber nullable: true
		section nullable: true
    
	}
	
	static mapping = {
		additionalInstructions type: 'text'
		prompt type: 'text'
	}
	
	
	@Override
	public int compareTo(obj){
	  if(obj){
	    this.designOrder?.compareTo(obj.designOrder)
	  }
	}
}
