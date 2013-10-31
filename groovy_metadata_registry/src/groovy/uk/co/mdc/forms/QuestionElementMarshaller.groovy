package uk.co.mdc.forms
import java.util.Map;
import org.codehaus.groovy.grails.web.json.JSONObject
import grails.converters.JSON


class QuestionElementMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(QuestionElement) { QuestionElement questionElement ->
				
			return [
			'id' : questionElement.id,
			'questionNumber' : questionElement?.designOrder,
			'label' : questionElement.label,
			'prompt' : questionElement?.prompt,
			'style' : questionElement?.style,
			'additionalInstructions' : questionElement?.additionalInstructions,
			'inputId' : questionElement?.inputField?.id,
			'dataElementId': questionElement?.dataElement?.id,
			'valueDomainId': questionElement?.valueDomain?.id,
			'defaultValue' : questionElement?.inputField?.defaultValue,
			'placeholder' : questionElement?.inputField?.placeholder,
			'unitOfMeasure' : questionElement?.inputField?.unitOfMeasure,
			'maxCharacters' : questionElement?.inputField?.maxCharacters,
			'format' : questionElement?.inputField?.format,
			'dataType' : questionElement?.inputField?.dataType?.name,
			'isEnumerated' : questionElement?.inputField?.dataType?.enumerated ? questionElement?.inputField?.dataType?.enumerated : false,
			'enumerations' : questionElement?.inputField?.dataType?.enumerations,
			'listItems': formatListItems(questionElement?.inputField?.dataType?.enumerated,questionElement?.inputField?.dataType?.enumerations),
			'cardinality': 1,
			'rule': "",
			'renderType': calculateRenderType(questionElement?.inputField?.dataType?.enumerated, questionElement?.inputField?.dataType?.name)
			]
		}
	}
	
	
	
	def calculateRenderType(isEnumerated, dataType){
		
		if(isEnumerated){
			return "List_Field"	
		}else{
		
			switch(dataType){
				
				case "String":
					return "Text_Field"
					break;
				
				case "Boolean":
					return "Boolean_Field"
					break;
					
				case "Date":
					return "Date_Field"
					break;
					
				case "Time":
					return "Time_Field"
					break;
				
				default:
					return "Text_Field"
					break;
				
			}
		
		}
		
	}
	
	def formatListItems(isEnumerated, enumerations){
	
		if(isEnumerated){
			
			def listItems = []
			
			enumerations.each{ key, value->
				
				def listItem = new JSONObject()
				
				listItem.put("code", key)
				listItem.put("definition", value)
				
				listItems.push(listItem)
			}
			
			return listItems
			
		}else{
		
			return []
		}
	
	}

}




