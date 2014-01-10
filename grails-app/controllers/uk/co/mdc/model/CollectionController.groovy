package uk.co.mdc.model

import grails.converters.JSON
import grails.orm.PagedResultList
import org.springframework.dao.DataIntegrityViolationException

import uk.co.mdc.CollectionBasket

class CollectionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def messageSource
	def collectionService
	def dataElementService
	def MDRService

	/* **************************************************************************************
	 * ************************************* INDEX *********************************************************
	 
	 * default redirect to list page
	 ********************************************************************************************* */
	
    def index() {
        redirect(action: "list", params: params)
    }
	
	/* **************************************************************************************
	 * ************************************* LIST ***************************************************
	 
	 *....only use this to render the list template as the datatables method is used instead
	 * to list all the data collections
	 *************************************************************************************** */

    def list() {
        []
    }
	
	/* **************************************************************************************
	 * ********************************* DATA TABLES *************************************************
	
	 * this function is called when listing the collections. It is called through ajax
	 * using the the data tables plugin in the show.gsp view and the javascript that
	 * calls the code is in main.js
	 *********************************************************************************** */
	
	def dataTables(){
		
		// set the variables needed to pass back to the data tables plugin to render the collections
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		def sortColName
		
		//if the user searches for a collection return the search results using the collection service
		
		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = collectionService.search(params.sSearch)
			
			total = searchResults.size()
			displayTotal = searchResults.size()
			
			if(total>0){
				data = searchResults
                println "Collections" + data
			}else{
				data=[]
			}
			
			//otherwise list the collections using the collections service and pass the relevant data
			//back to the data tables plugin request as json
			
			
		}else{
			order = params?.sSortDir_0
			sortColName = getSortField(params?.iSortCol_0.toInteger())
			
			data = collectionService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortColName, order: order)
			data = formfilter(data)
            total = data.size()
			displayTotal = total
		}
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
		//NB. when the json is rendered it uses a custom json marshaller so that it includes the relevant
		//information (and doesn't return the whole database)
		//the corresponding json marshaller is stored in src/groovy/uk/co/mdc/model/xxxxxxMarshaller.groovy
		render model as JSON

	}

    def formfilter(PagedResultList results){
        /* sorry about the java :-(  */
        Iterator iterator = results.iterator()
        ArrayList formlist = new ArrayList();

        for (int i = 0; i < results.size(); i++){
            def obj = results.get(i)
            if(obj instanceof uk.co.mdc.forms.FormDesign){
                formlist.add(obj)
            }
        }

        if(formlist.size() > 0){
            for (int i = 0; i < formlist.size(); i++){
                uk.co.mdc.forms.FormDesign form = formlist.get(i)
                results.remove(form)
            }
        }

        return results
    }
	
	/* **************************************************************************************
	 * *********************************** SHOW *****************************************************
	
	 * show the collection in question using the find instance function and the dataElement service
	 * ...presuming they have the appropriate permissions
	 *************************************************************************************** */
	
	def show(Long id) {
		
		//use the find instance method to get the collection in question
		
		def collectionInstance = findInstance()
		
		//if you can't find it or don't have permission go back to the list page otherwise show the collection
		
		if (!collectionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'collection.label', default: 'Collection'), id])
			redirect(action: "list")
			return
		}

		[collectionInstance: collectionInstance]
	}
	

	/* **************************************************************************************
	 * ************************************* CREATE ***************************************************
	 
	 * renders the collection template so the user can create a collection
	 * N.B additionally will only display objects that user has permission to read
	 *************************************************************************************** */
	
    def create() {
        [dataElements: dataElementService.list(), collectionInstance: new Collection(params)]
    }
	
	/* **************************************************************************************
	 * ************************************ SAVE BASKET COLLECTION **************************
	 
	 * calls the collection service to create a collection and from the data elements included in the users
	 * collection basket. The service sets admin permissions
	 * on that object for the user in question and creates the collection
	 *************************************************************************************** */
	
	
	
	def saveBasketCollection(){



		def collectionInstance = collectionService.createFromBasket(params)

		//if there are any errors in the collection put them into a format
		//that we can display and redirect
			
		def errors = []
		
		if(collectionInstance.errors){
				
			def locale = Locale.getDefault()
				
			for (fieldErrors in collectionInstance.errors) {
				for (error in fieldErrors.allErrors) {
					   errors.add(messageSource.getMessage(error, locale))
                       println messageSource.getMessage(error, locale)
				}
			}
			if(errors.size()>0){
				//redirect to the collection basket controller any errors displayed
				redirect(controller: "CollectionBasket", action:"show", id: params?.collection_basket_id, params: ['errors': errors, name: params?.name, description: params?.description])
				return
			}
		}
		
		redirect(action: "show", id: collectionInstance.id)
	}

    def saveDEBasketCollection(){

        //create the collection with the parameters passed from the collection basket
        //If we are calling this from pathways then we need to get the jsonObject out and add the parameters in piecemeal
        def jsonObject = JSON.parse(params.jsonobject)
        def it = params.entrySet ().iterator()
        while (it.hasNext()) {
            def entry = it.next()
            if (entry.key == "jsonobject"){
                jsonObject = JSON.parse(entry.value)
                it.remove()
            }
        }
        def jit = jsonObject.iterator()
        while (jit.hasNext()) {
            def collectionDetails = jit.next()
            //Now add the key parameters of the new de Collection in
            params.put(collectionDetails.key,collectionDetails.value)
        }
        def collectionInstance = collectionService.createFromBasket(params)
        //if there are any errors in the collection put them into a format
        //that we can display and redirect

        def errors = []

        if(collectionInstance.errors){

            def locale = Locale.getDefault()

            for (fieldErrors in collectionInstance.errors) {
                for (error in fieldErrors.allErrors) {
                    errors.add(messageSource.getMessage(error, locale))
                    println messageSource.getMessage(error, locale)
                }
            }
            if(errors.size()>0){
                //redirect to the collection basket controller any errors displayed
                redirect(controller: "CollectionBasket", action:"show", id: params?.collection_basket_id, params: ['errors': errors, name: params?.name, description: params?.description])
                return
            }
        }

        redirect(action: "show", id: collectionInstance.id)
    }
	
	/* **************************************************************************************
	 * ************************************ SAVE ****************************************************
	 
	 * calls the collection service to create a collection and  sets admin permissions
	 * on that object for the user in question
	 *************************************************************************************** */

    def save() {
		
		/* ***
		 * validate the collection looking at it's schema specifications and ensuring they are mutually exclusive
		 * i.e. a collection cannot have a data element that is both mandatory and optional
		 * ****/
		
		Boolean valid = validateLinkedDataTypes()
		
		if(!valid){
			render(view: "create", model: [dataElements: dataElementService.list(), collectionInstance: new Collection(params)])
			return
		}
		
		/* *****
		 * create the data element using the data element service
		 ******* */
		
		
		def collectionInstance = collectionService.create(params)
		
		/* ******
		 * check that the data element has been created without errors and render accordingly
		 ***** */
		
		if (!renderWithErrors('create', collectionInstance)) {
			redirectShow(message(code: 'default.created.message', args: [message(code: 'dataElement.label', default: 'DataElement'), collectionInstance.id]), collectionInstance.id)
		}
		
    }
	
	/* **************************************************************************************
	 * ************************************** EDIT ********************************************
	
	 * this function redirects to the edit collection screen
	 *********************************************************************************** */

    def edit(Long id) {
        def collectionInstance = findInstance()
        if (!collectionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'collection.label', default: 'Collection'), id])
            redirect(action: "list")
            return
        }

        [dataElements: dataElementService.list(), mandatoryDataElements: collectionInstance.mandatoryDataElementCollections(), requiredDataElements: collectionInstance.requiredDataElementCollections(), optionalDataElements: collectionInstance.optionalDataElementCollections(), referenceDataElements: collectionInstance.referenceDataElementCollections(), collectionInstance: collectionInstance]
    }
	
	/* **************************************************************************************
	 * ************************************ UPDATE **********************************************
	
	 * this function updates the collection using the collection service
	 *********************************************************************************** */

    def update(Long id, Long version) {
		
		/* ***
		 * validate the collection looking at it's schema specifications and ensuring they are mutually exclusive
		 * i.e. a collection cannot have a data element that is both mandatory and optional
		 * ****/
		
		Boolean valid = validateLinkedDataTypes()
		
		if(!valid){
			redirect(action: "edit", id: id)
			return
		}
		
		//get the collection (redirect if you can't)
		
        def collectionInstance = findInstance()
        if (!collectionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'collection.label', default: 'Collection'), id])
            redirect(action: "list")
            return
        }
		
		//check that we have the right version i.e. no one else has updated the collection whilst we have been
		//looking at it

        if (version != null) {
            if (collectionInstance.version > version) {
                collectionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'collection.label', default: 'Collection')] as Object[],
                          "Another user has updated this Collection while you were editing")
                render(view: "edit", model: [collectionInstance: collectionInstance])
                return
            }
        }
		
		
		//update the collection using the collection service
		
		collectionService.update(collectionInstance, params)
		
		if (!renderWithErrors('edit', collectionInstance)) {
			redirectShow message(code: 'default.updated.message', args: [message(code: 'dataElement.label', default: 'DataElement'), collectionInstance.id]), collectionInstance.id
		}
		

        
    }
	
	/* **************************************************************************************
	 * ********************************* DELETE *************************************************
	
	 * this function deletes the collection using the collection service
	 *********************************************************************************** */

    def delete(Long id) {
		
		//get the collection and redirect if it doesn't exist
        def collectionInstance = findInstance()
        if (!collectionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'collection.label', default: 'Collection'), id])
            redirect(action: "list")
            return
        }
		
		//call the collection service  to delete the collection

        try {
			
			collectionService.delete(collectionInstance)
			
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'collection.label', default: 'Collection'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'collection.label', default: 'Collection'), id])
            redirect(action: "show", id: id)
        }
    }
	
	/* **************************************************************************************
	 * ********************************* DELETE *************************************************
	
	 * this function removes a data element from a collection
	 *********************************************************************************** */
	
	def removeDataElement() {
		
		Collection collection = collectionService.get(params.collectionId)
		DataElement dataElement = dataElementService.get(params.dataElementId)
		if(collection && dataElement){
			collectionService.removeDataElement(collection, dataElement)
		}
		redirect(action: 'edit', id: params.collectionId)
	}
	
	
	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	
	 * this function grant permission to the given collection
	 *********************************************************************************** */
	
	
	def grant = {
		
				def collection = findInstance()
				
				if (!collection) return
		
				if (!request.post) {
					return [collectionInstance: collection]
				}
		
				collectionService.addPermission(collection, params.recipient, params.int('permission'))
		
				redirectShow "Permission $params.permission granted on Report $collection.id " + "to $params.recipient", collection.id
			}
	
	/* **********************************************************************************
	 * this function uses the collection service to get the collection so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */
	
	private Collection findInstance() {
		def collection = collectionService.get(params.long('id'))
		if (!collection) {
			flash.message = "Collection not found with id $params.id"
			redirect action: list
		}
		collection
	}
	
	/* **********************************************************************************
	 * this function redirects to the show collection screen
	 *********************************************************************************** */
	
	private void redirectShow(message, id) {
		flash.message = message
		//redirect with message
				
		redirect(action: "show", id: id)
	}
	
	/* **********************************************************************************
	 * this function checks to see if the collection passed to it contains errors i.e. when a
	 * service returns the element. It either returns false (if no errors) or it redirects
	 * to the view specified by the caller
	 *********************************************************************************** */

	private boolean renderWithErrors(String view, Collection collection) {
		if (collection.hasErrors()) {
			render view: view, model: [dataElements: dataElementService.list(), collectionInstance: collection]
			return true
			
		}
		false
	}
	
	
	/* **********************************************************************************
	 * this function validates the parameters submitted when creating or updating a collection
	 * in particular this validates that the data element schemas are mutually exclusive
	 * i.e. a data element cannot be both mandatory and optional within a given collection
	 *********************************************************************************** */
	
	
	def validateLinkedDataTypes(){
		
		def mandatoryDataElements = params?.mandatoryDataElements
		
		def requiredDataElements = params.requiredDataElements
		
		def optionalDataElements = params.optionalDataElements
		
		def referenceDataElements = params.referenceDataElements
		
		if(mandatoryDataElements && requiredDataElements){
			if(MDRService.parameterContains(mandatoryDataElements, requiredDataElements)){
				
				flash.message = 'Added data elements must either be mandatory or required for any given collection, not both'
				
				return false
			}
		}
		
	
		if(mandatoryDataElements && optionalDataElements){
			if(MDRService.parameterContains(mandatoryDataElements, optionalDataElements)){
				flash.message = 'Added data elements must either be mandatory or optional for any given collection, not both.'
				return false
			}
		}
		

		if(mandatoryDataElements && referenceDataElements){
			if(MDRService.parameterContains(mandatoryDataElements, referenceDataElements)){
				flash.message = 'Added data elements must either be mandatory or reference for any given collection, not both'
				return false
			}
		}
		
		if(requiredDataElements && referenceDataElements){
			if(MDRService.parameterContains(requiredDataElements, referenceDataElements)){
				flash.message = 'Added data elements must either be mandatory or reference for any given collection, not both'
				return false
			}
		}
		
		if(requiredDataElements && optionalDataElements){
			if(MDRService.parameterContains(requiredDataElements, optionalDataElements)){
				flash.message = 'Added data elements must either be required or optional for any given collection, not both'
				return false
			}
		}
		
		if(optionalDataElements && referenceDataElements){
			if(MDRService.parameterContains(optionalDataElements, referenceDataElements)){
				flash.message = 'Added data elements must either be required or reference for any given collection, not both'
				return false
			}
		}
		
		
		return true
	
	}
	
	
	/*this function is used by the dataTables function to map the column name to the data*/
	

	String getSortField(Integer column){
		
		def field
		
		switch(column){
			
			case 0:
				field = "name"
			break
			
			case 1:
				field = "description"
			break
			
			default:
				field = "name"
			break
		}
		
		return field
		
	}
	
	

}
