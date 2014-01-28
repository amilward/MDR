package uk.co.mdc.catalogue

class CustomMarshaller {

	//this method ensures that we don't pull back the whole database i.e. all the data elements and their value domains and everything else they are associated with
	def limitRender(elements){
		
		def toRender = elements.collect{ element->
			["id": element.id, "name":element.name]
		}
		
		return toRender
		
	}

    def limitRenderRelations(elements){

        def toRender = elements.collect{ element->
            ["id": element.id, "name":element.name, "type": element.relationshipType.name, "relationshipDirection": element.relationshipDirection, class: element.getClass().getSimpleName() ]
        }

        return toRender

    }
	
}
