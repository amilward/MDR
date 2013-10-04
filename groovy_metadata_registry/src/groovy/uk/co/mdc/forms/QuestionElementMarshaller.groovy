package uk.co.mdc.forms
import java.util.Map;
import grails.converters.JSON


class QuestionElementMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(QuestionElement) { QuestionElement questionElement ->
				
			return [
			'id' : questionElement.id,
			'label' : questionElement.label,
			'prompt' : questionElement?.prompt,
			'style' : questionElement?.style,
			'additionalInstructions' : questionElement?.additionalInstructions,
			'defaultValue' : questionElement?.inputField?.defaultValue,
			'placeholder' : questionElement?.inputField?.placeholder,
			'unitOfMeasure' : questionElement?.inputField?.unitOfMeasure,
			'maxCharacters' : questionElement?.inputField?.maxCharacters,
			'format' : questionElement?.inputField?.format,
			'dataType' : questionElement?.inputField?.dataType?.name,
			'isEnumerated' : result = questionElement?.inputField?.dataType?.enumerated ? questionElement?.inputField?.dataType?.enumerated : false,
			'enumerations' : questionElement?.inputField?.dataType?.enumerations,
			]
		}
	}

}




