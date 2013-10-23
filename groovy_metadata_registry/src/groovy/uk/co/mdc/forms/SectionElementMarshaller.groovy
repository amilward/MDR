package uk.co.mdc.forms

import grails.converters.JSON

class SectionElementMarshaller {

	void register() {
		JSON.registerObjectMarshaller(SectionElement) { SectionElement sectionElement ->
				
			return [
			'id' : sectionElement.id,
			'type' : "Section Element",
			'label' : sectionElement.title,
			'containedElements' : sectionElement.questionElements
			]
		}
	}
	
}
