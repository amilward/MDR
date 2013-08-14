package uk.co.mdc.model
import java.util.Map;
import grails.converters.JSON
import uk.co.mdc.forms.*


class FieldMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(Field) { Field field ->
				
			return [
			'id' : field.id,
			'name' : field.name,
			'type' : field.type.value,
			'caption' : field.caption,
			'placeholder' : field.placeholder,
			'value' : field?.value,
			'size' : field?.size,
			'options' : field?.options,
			'field_class' : field?.field_class,
			'field_id' : field?.field_id
			]
		}
	}

}



