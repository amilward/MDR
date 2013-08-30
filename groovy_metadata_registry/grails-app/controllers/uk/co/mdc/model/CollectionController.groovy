package uk.co.mdc.model

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

import uk.co.mdc.CollectionBasket

class CollectionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def messageSource

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
	
	
	def dataTables(){
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		def sortColName
		

		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = Collection.search(params.sSearch, [max:params.iDisplayLength])
			
			total = searchResults.total
			displayTotal = searchResults.total
			
			if(total>0){
				data = searchResults.results
			}else{
				data=[]
			}
			
			
			
		}else{
		
			order = params?.sSortDir_0
			sortColName = getSortField(params?.iSortCol_0.toInteger())
			
			data = Collection.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortColName, order: order)
			total = Collection.count()
			displayTotal = Collection.count()
			
		}
		
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
				
		render model as JSON
	}
	
	
	def saveBasketCollection(){

		def collectionInstance = new Collection(refId: params.refId, name: params?.name, description: params?.description)
		
		if (!collectionInstance.save(flush: true)) {
			
			def errors = []
			
			if(collectionInstance.errors){
				
				def locale = Locale.getDefault()
				
				for (fieldErrors in collectionInstance.errors) {
					for (error in fieldErrors.allErrors) {
					   errors.add(messageSource.getMessage(error, locale))
					}
				 }
			}
			redirect(controller: "CollectionBasket", action:"show", id: params?.collection_basket_id, params: ['errors': errors, refId: params?.refId, name: params?.name, description: params?.description])
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

        [dataElements: DataElement.list(), mandatoryDataElements: collectionInstance.mandatoryDataElementCollections(), requiredDataElements: collectionInstance.requiredDataElementCollections(), optionalDataElements: collectionInstance.optionalDataElementCollections(), referenceDataElements: collectionInstance.referenceDataElementCollections(), collectionInstance: collectionInstance]
    }

    def update(Long id, Long version) {
		
		Boolean valid = validateLinkedDataTypes()
		
		if(!valid){
			redirect(action: "edit", id: id)
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
			if(parameterContains(mandatoryDataElements, requiredDataElements)){
				
				flash.message = 'Added data elements must either be mandatory or required for any given collection, not both'
				
				return false
			}
		}
		
	
		if(mandatoryDataElements && optionalDataElements){
			if(parameterContains(mandatoryDataElements, optionalDataElements)){
				flash.message = 'Added data elements must either be mandatory or optional for any given collection, not both.'
				return false
			}
		}
		

		if(mandatoryDataElements && referenceDataElements){
			if(parameterContains(mandatoryDataElements, referenceDataElements)){
				flash.message = 'Added data elements must either be mandatory or reference for any given collection, not both'
				return false
			}
		}
		
		if(requiredDataElements && referenceDataElements){
			if(parameterContains(requiredDataElements, referenceDataElements)){
				flash.message = 'Added data elements must either be mandatory or reference for any given collection, not both'
				return false
			}
		}
		
		if(requiredDataElements && optionalDataElements){
			if(parameterContains(requiredDataElements, optionalDataElements)){
				flash.message = 'Added data elements must either be required or optional for any given collection, not both'
				return false
			}
		}
		
		if(optionalDataElements && referenceDataElements){
			if(parameterContains(optionalDataElements, referenceDataElements)){
				flash.message = 'Added data elements must either be required or reference for any given collection, not both'
				return false
			}
		}
		
		
		return true
	
	}
	
	def parameterContains(dataElements1, dataElements2){
			
			if(dataElements1 instanceof String[] && dataElements2 instanceof String[]){
				
				dataElements2.each{ dataElement->
					
					if(dataElements1.contains(dataElement)){
						return true
					}
					
				}
				
			}else if(dataElements1 instanceof String[] && dataElements2 instanceof String){
			
				if(dataElements1.contains(dataElements2)){
					return true
				}
			
			}else if(dataElements1 instanceof String && dataElements2 instanceof String[]){
			
				if(dataElements2.contains(dataElements1)){
					return true
				}
			
			}else{
				if(dataElements1==dataElements2){
					return true
				}
			}
			
			return false		
	}
	
	def linkDataElements(collectionInstance){
		
		def currentMandatoryElements = collectionInstance.mandatoryDataElementCollections()
		def mandatoryDataElements = params?.mandatoryDataElements
		
		extDataElementChecker(mandatoryDataElements, currentMandatoryElements, collectionInstance)
		
		def currentRequiredElements = collectionInstance.requiredDataElementCollections()
		def requiredDataElements = params?.requiredDataElements
		
		extDataElementChecker(requiredDataElements, currentRequiredElements, collectionInstance)
		
		def currentOptionalElements = collectionInstance.optionalDataElementCollections()
		def optionalDataElements = params?.optionalDataElements
		
		extDataElementChecker(optionalDataElements, currentOptionalElements, collectionInstance)
			
		def currentReferenceElements = collectionInstance.referenceDataElementCollections()
		def referenceDataElements = params?.referenceDataElements
		
		extDataElementChecker(referenceDataElements, currentReferenceElements, collectionInstance)
		
		
		if(mandatoryDataElements && mandatoryDataElements!=null){
			
				linkDataElementType(collectionInstance, mandatoryDataElements, SchemaSpecification.MANDATORY)
			
		}
		
		if(requiredDataElements && requiredDataElements!=null){
			
				linkDataElementType(collectionInstance, requiredDataElements, SchemaSpecification.REQUIRED)
			
		}
		
		if(optionalDataElements && optionalDataElements!=null){
			
				linkDataElementType(collectionInstance, optionalDataElements, SchemaSpecification.OPTIONAL)
			
		}
		
		if(referenceDataElements && referenceDataElements!=null){
			
				linkDataElementType(collectionInstance, referenceDataElements, SchemaSpecification.X)
			
		}
		
	}
	
	def extDataElementChecker(newDataElements, currentElements, collectionInstance){
		
		if(newDataElements!=null){
			
		if (newDataElements instanceof String) {
			
			//remove all the mandatory data elements that aren't this one
			currentElements.each{ de ->
				if(newDataElements!=de.id.toString()){
						collectionInstance.removeFromDataElementCollections(de)
				}
			}
			
		}
		
		if (newDataElements instanceof String[]) {
			
			//remove all the mandatory data elements that aren't this one
			currentElements.each{ de ->
				if(!newDataElements.contains(de.id.toString())){
						collectionInstance.removeFromDataElementCollections(de)
				}
			}
		
		}
			
		}else{
		
			//remove all the madatory data elements
			currentElements.each{ de ->
						collectionInstance.removeFromDataElementCollections(de)
			}
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
	
	
	String getSortField(Integer column){
		
		def field
		
		switch(column){
			
			case 0:
				field = "refId"
			break
			
			case 1:
				field = "name"
			break
			
			case 2:
				field = "description"
			break
			
			default:
				field = "refId"
			break
		}
		
		return field
		
	}
	
	

}
