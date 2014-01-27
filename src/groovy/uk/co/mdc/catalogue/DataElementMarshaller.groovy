package uk.co.mdc.catalogue
import grails.converters.JSON

class DataElementMarshaller extends CustomMarshaller{
	
	
	void register() {
		JSON.registerObjectMarshaller(DataElement) { DataElement dataElement ->
				
			return [
			'id' : dataElement.id,
            'version' : dataElement.version,
			'name' : dataElement.name,
			'description' : dataElement.description,
			'definition' : dataElement.definition,
            'versionNumber' : dataElement.versionNumber + '.' + dataElement.revisionNumber,
            'status' : dataElement.status.name(),
            'relations': limitRenderRelations(dataElement?.relations()),
            'class': 'DataElement'
			]
		}
	}
}




