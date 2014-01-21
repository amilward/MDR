package uk.co.mdc.model
import grails.converters.JSON

class DataElementMarshaller extends CustomMarshaller{
	
	
	void register() {
		JSON.registerObjectMarshaller(DataElement) { DataElement dataElement ->
				
			return [
			'id' : dataElement.id,
			'name' : dataElement.name,
			'description' : dataElement.description,
			'definition' : dataElement.definition,
            'versionNumber' : dataElement.versionNumber + '.' + dataElement.revisionNumber,
			'parent_id' : dataElement?.parent?.id,
			'parent_name' : dataElement?.parent?.name,
			'model_id' : dataElement?.relations("ModelElement")?.id,
			'model_name' : dataElement?.relations("ModelElement")?.name,
			'model_mandatory_id' : dataElement?.relations("MandatoryModelElement")?.id,
			'model_mandatory_name' : dataElement?.relations("MandatoryModelElement")?.name,
			'model_required_id' : dataElement?.relations("RequiredModelElement")?.id,
			'model_required_name' : dataElement?.relations("RequiredModelElement")?.name,
			'model_optional_id' : dataElement?.relations("OptionalModelElement")?.id,
			'model_optional_name' : dataElement?.relations("OptionalModelElement")?.name,
			'model_reference_id' : dataElement?.relations("MandatoryModelElement")?.id,
			'model_reference_name' : dataElement?.relations("MandatoryModelElement")?.name,
			'subElements': limitRender(dataElement?.subElements),
			'valueDomains': limitRender(dataElement?.relations("DataValue"))
			]
		}
	}
}




