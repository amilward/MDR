package uk.co.mdc.forms

import uk.co.mdc.model.DataElementConcept

class SectionElement extends FormDesignElement{

	//N.B. In the future potentially use a mapping class SectionElement_DataElementConcept
	DataElementConcept dataElementConcept
	
	String number
	String title
	//N.B. Ordered?
	String[] instructions
	
	
	static hasMany = [presentationElements: PresentationElement, questionElements: QuestionElement]

	static belongsTo = [formDesign: FormDesign]
	
    static constraints = {
		dataElementConcept nullable:true
		formDesign nullable:true
		title nullable:true
		instructions nullable:true
    }
}
