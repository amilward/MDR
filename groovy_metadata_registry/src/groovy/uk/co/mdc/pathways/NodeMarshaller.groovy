package uk.co.mdc.pathways

import grails.converters.JSON

//{"id" : "' + node.peCollection.id + '", "description" : "' + node.peCollection.description + '"}

class NodeMarshaller {
	void register() {
		JSON.registerObjectMarshaller(Node) { Node node ->
				
			return [
			'id': 'node' + node.id,
			'name' : node?.name,
			'description': node?.description,
			'x' : node.x,
			'y' : node.y,
			'dataElements' : '[' + node.GetElementsJSON() + ']'
			]
		}
	}
}
