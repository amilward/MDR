package uk.co.mdc.model
import grails.converters.JSON

class ValueDomainMarshaller extends CustomMarshaller{

	void register() {
		JSON.registerObjectMarshaller(ValueDomain) { ValueDomain valueDomain ->
				
			return [
			'id' : valueDomain.id,
			'name' : valueDomain.name,
			'description' : valueDomain.description,
			'format' : valueDomain.format,
			'conceptualDomain_id' : valueDomain?.conceptualDomain?.id,
			'conceptualDomain_name' : valueDomain?.conceptualDomain?.name,
			'dataType_id' : valueDomain?.dataType?.id,
			'dataType_name' : valueDomain?.dataType?.name,
			'unitOfMeasure': valueDomain?.unitOfMeasure,
			'dataElements': limitRender(valueDomain.relations("ValueDomain")),
			'regexDef': valueDomain.regexDef
			]
		}
	}
}
