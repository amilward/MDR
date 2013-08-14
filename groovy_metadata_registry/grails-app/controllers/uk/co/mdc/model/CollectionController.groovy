package uk.co.mdc.model

import org.springframework.dao.DataIntegrityViolationException
import uk.co.mdc.CollectionBasket

class CollectionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [collectionInstanceList: Collection.list(params), collectionInstanceTotal: Collection.count()]
    }

    def create() {
        [dataElements: DataElement.list(), collectionInstance: new Collection(params)]
    }
	
	def saveBasketCollection(){

		def collectionInstance = new Collection(refId: params.refId, name: params?.name, description: params?.description)
		
		if (!collectionInstance.save(flush: true)) {
			render(view: "create", model: [dataElements: DataElement.list(), collectionInstance: collectionInstance])
			return
		}
		
		
		
		params.dataElementIds.each{ de ->
			
			def dataElement = DataElement.get(de)
			
			def dataElementSchemaInfo = params["dataElement_" + de]
			
			def schemaSpecification = SchemaSpecification.MANDATORY
			
			if(dataElementSchemaInfo=='required'){
			
				schemaSpecification = SchemaSpecification.REQUIRED
			
			}else if(dataElementSchemaInfo=='optional'){
			
				schemaSpecification = SchemaSpecification.OPTIONAL
			
			}else if(dataElementSchemaInfo=='reference'){
			
				schemaSpecification = SchemaSpecification.X
			
			}
			
			DataElementCollection.link(dataElement, collectionInstance, schemaSpecification)
			
			def collectionBasket = CollectionBasket.get(params.collection_basket_id)
			
			collectionBasket.dataElements.clear();
			collectionBasket.save(flush: true)
			
		}
		
		redirect(action: "show", id: collectionInstance.id)
	}

    def save() {
		
		//validate the parent child relationship
		
		Boolean valid = validateLinkedDataTypes()
		
		if(!valid){
			render(view: "create", model: [dataElements: DataElement.list(), collectionInstance: new Collection(params)])
			return
		}
		
        def collectionInstance = new Collection(params)
        if (!collectionInstance.save(flush: true)) {
            render(view: "create", model: [dataElements: DataElement.list(), collectionInstance: collectionInstance])
            return
        }
		
		linkDataElements(collectionInstance)

        flash.message = message(code: 'default.created.message', args: [message(code: 'collection.label', default: 'Collection'), collectionInstance.id])
        redirect(action: "show", id: collectionInstance.id)
    }

    def show(Long id) {
        def collectionInstance = Collection.get(id)
        if (!collectionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'collection.label', default: 'Collection'), id])
            redirect(action: "list")
            return
        }

        [collectionInstance: collectionInstance]
    }
	
	/*
	 * OLD FORM STUFF ------ JUST KEEPING IT FOR REFERENCE
	 * 
	 * def generateForm(Long id){
		def collectionInstance = Collection.get(id)
		if (!collectionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'collection.label', default: 'Collection'), id])
			redirect(action: "list")
			return
		}

		[collectionInstance: collectionInstance]
		
	}
	
	def saveForm(){
		
		def collectionInstance = Collection.get(params.collectionId)
		
		def dataMap = new HashMap() 
				
		params.each { name, value ->
			
			if(name!='collectionId' && name!='create' && name!='action' && name!='controller'){
				dataMap.put(name, value)
				}
			}
		
		def form = new Form(collection: collectionInstance, data: dataMap)
		
		if (!form.save(flush: true)) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'collection.label', default: 'Collection'), id])
			redirect(action: "generateForm")
		}

		redirect(controller: 'form', action: "list")
	}*/

    def edit(Long id) {
        def collectionInstance = Collection.get(id)
        if (!collectionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'collection.label', default: 'Collection'), id])
            redirect(action: "list")
            return
        }

        [dataElements: DataElement.list(), collectionInstance: collectionInstance]
    }

    def update(Long id, Long version) {
		
		Boolean valid = validateLinkedDataTypes()
		
		if(!valid){
			render(view: "edit", model: [dataElements: DataElement.list(), collectionInstance: new Collection(params)])
			return
		}
		
        def collectionInstance = Collection.get(id)
        if (!collectionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'collection.label', default: 'Collection'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (collectionInstance.version > version) {
                collectionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'collection.label', default: 'Collection')] as Object[],
                          "Another user has updated this Collection while you were editing")
                render(view: "edit", model: [collectionInstance: collectionInstance])
                return
            }
        }

        collectionInstance.properties = params

        if (!collectionInstance.save(flush: true)) {
            render(view: "edit", model: [collectionInstance: collectionInstance])
            return
        }
		
		linkDataElements(collectionInstance)

        flash.message = message(code: 'default.updated.message', args: [message(code: 'collection.label', default: 'Collection'), collectionInstance.id])
        redirect(action: "show", id: collectionInstance.id)
    }

    def delete(Long id) {
        def collectionInstance = Collection.get(id)
        if (!collectionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'collection.label', default: 'Collection'), id])
            redirect(action: "list")
            return
        }

        try {
			
			collectionInstance.prepareForDelete()
			
            collectionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'collection.label', default: 'Collection'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'collection.label', default: 'Collection'), id])
            redirect(action: "show", id: id)
        }
    }
	
	
	
	def removeDataElement() {
		Collection collection = Collection.get(params.collectionId)
		DataElement dataElement = DataElement.get(params.dataElementId)
		if(collection && dataElement){
			collection.removeFromDataElementCollections(dataElement)
		}
		redirect(action: 'edit', id: params.collectionId)
	}
	
	
	def validateLinkedDataTypes(){
		
		def mandatoryDataElements = params?.mandatoryDataElements
		
		def requiredDataElements = params.requiredDataElements
		
		def optionalDataElements = params.optionalDataElements
		
		def referenceDataElements = params.referenceDataElements
		
	
		if(mandatoryDataElements && requiredDataElements){
			
			if(mandatoryDataElements.contains(requiredDataElements)){
				
				flash.message = 'Added data elements must either be mandatory or required for any given collection, not both'
				return false
			}
		}
		
		if(mandatoryDataElements && optionalDataElements){
			
			if(mandatoryDataElements.contains(optionalDataElements)){
				
				flash.message = 'Added data elements must either be mandatory or optional for any given collection, not both.'
				return false
			}
		}
		
		if(mandatoryDataElements && referenceDataElements){
			
			if(mandatoryDataElements.contains(referenceDataElements)){
				
				flash.message = 'Added data elements must either be mandatory or reference for any given collection, not both'
				return false
			}
			
		}
		
		
		if(requiredDataElements && optionalDataElements){
			

			if(requiredDataElements.contains(optionalDataElements)){
				
				flash.message = 'Added data elements must either be required or optional for any given collection, not both'
				return false
			}
		}
		
		if(requiredDataElements && referenceDataElements){
			if(requiredDataElements.contains(referenceDataElements)){
				
				flash.message = 'Added data elements must either be required or reference for any given collection, not both'
				return false
			}
			
		}
		
		if(optionalDataElements && referenceDataElements){
			
			if(optionalDataElements.contains(referenceDataElements)){
				
				flash.message = 'Added data elements must either be reference or optional for any given collection, not both'
				return false
			}
			
		}
		
		return true
	
	}
	
	
	def linkDataElements(collectionInstance){
		
		def mandatoryDataElements = params?.mandatoryDataElements
		
		if(mandatoryDataElements){
			linkDataElementType(collectionInstance, mandatoryDataElements, SchemaSpecification.MANDATORY)
		}
		
		def requiredDataElements = params?.requiredDataElements
		
		if(requiredDataElements){
			linkDataElementType(collectionInstance, requiredDataElements, SchemaSpecification.REQUIRED)
		}
		
		def optionalDataElements = params?.optionalDataElements
		
		if(optionalDataElements){
			linkDataElementType(collectionInstance, optionalDataElements, SchemaSpecification.OPTIONAL)
		}
			
		def referenceDataElements = params?.referenceDataElements
		
		if(referenceDataElements){
			linkDataElementType(collectionInstance, referenceDataElements, SchemaSpecification.X)
		}
		
	}
	
	def linkDataElementType(collectionInstance, dataElements, schemaSpecification){
		
		if(dataElements!=null){
			
			
			if (dataElements instanceof String) {
				DataElement dataElement =  DataElement.get(dataElements)
				if(dataElement){
					DataElementCollection.link(dataElement, collectionInstance, schemaSpecification)
				}
			} else if (dataElements instanceof String[]) {
			
			
				  for (dataElementID in dataElements){
					  DataElement dataElement =  DataElement.get(dataElementID)
					  if(dataElement){
						  DataElementCollection.link(dataElement, collectionInstance, schemaSpecification)
					  }
				  }
			}
			
		}
	}
	

}
