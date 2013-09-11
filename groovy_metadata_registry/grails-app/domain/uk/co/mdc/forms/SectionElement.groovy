package uk.co.mdc.forms

import uk.co.mdc.model.DataElementConcept

class SectionElement extends FormDesignElement{

	DataElementConcept dataElementConcept
	
	static hasMany = [presentationElements: PresentationElement, questionElements: QuestionElement]

	static belongsTo = [formDesign: FormDesign]
	
    static constraints = {
		dataElementConcept nullable:true
		formDesign nullable:true
    }
}
