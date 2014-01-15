package uk.co.mdc.forms

import grails.converters.JSON

class FormDesignMarshaller {

	void register() {
		JSON.registerObjectMarshaller(FormDesign) { FormDesign formDesign ->
				
			return [
			'id' : formDesign.id,
			'name' : formDesign.name,
			'description': formDesign.description,
			'header' : '',
			'footer' : '',
			'isDraft' : formDesign.isDraft,
			'containedElements' : formDesign.formDesignElements
			]
		}
	}

}
