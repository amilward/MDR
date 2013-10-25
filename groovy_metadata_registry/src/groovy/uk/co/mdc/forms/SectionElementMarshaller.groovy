package uk.co.mdc.forms

import grails.converters.JSON

class SectionElementMarshaller {

	void register() {
		JSON.registerObjectMarshaller(SectionElement) { SectionElement sectionElement ->
				
			return [
			'id' : sectionElement.id,
			'renderType' : "Section Element",
			'label' : sectionElement.title,
			'sectionNumber': sectionElement?.designOrder,
			'containedElements' : sectionElement.questionElements
			]
		}
	}
	
}
