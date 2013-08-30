package uk.co.mdc.model

class CustomMarshaller {

	//this method ensures that we don't pull back the whole database i.e. all the data elements and their value domains and everything else they are associated with
	def limitRender(elements){
		
		def toRender = elements.collect{ element->
			["id": element.id, "name":element.name, "refId": element.refId]
		}
		
		return toRender
		
	}
	
}
