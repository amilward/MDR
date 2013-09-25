package uk.co.mdc.forms

class PresentationElement extends FormDesignElement{
	
	TextElement textElement
	
	//need to add media element but will add in the next iteration

    static constraints = {
		textElement nullable:true
    }
}
