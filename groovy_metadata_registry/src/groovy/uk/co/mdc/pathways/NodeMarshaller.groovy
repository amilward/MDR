package uk.co.mdc.pathways

import grails.converters.JSON

class NodeMarshaller {
	void register() {
		JSON.registerObjectMarshaller(Node) { Node node ->
				
			return [
			'id': 'node' + node.id,
			'name' : node?.name,
			'description': node?.description,
			'x' : node.x,
			'y' : node.y,
			'dataElements' : '[{"id" : "Data_Element_10", "description" : "How loud can you shout?"}]'
			]
		}
	}
}
