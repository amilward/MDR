package uk.co.mdc.forms
import java.util.Map;
import grails.converters.JSON


class QuestionElementMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(QuestionElement) { QuestionElement questionElement ->
				
			return [
			'id' : questionElement.id,
			'name' : questionElement.label,
			'caption' : questionElement?.label,
			'placeholder' : questionElement?.label,
			'size' : questionElement?.inputField?.maxCharacters,
			'field_class' : questionElement?.style,
			]
		}
	}

}




