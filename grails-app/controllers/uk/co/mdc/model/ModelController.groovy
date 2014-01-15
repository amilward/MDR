package uk.co.mdc.model

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

import uk.co.mdc.ModelBasket

class ModelController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def messageSource
	def modelService
	def dataElementService
	def catalogueElementService

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
	 * to list all the data models
	 *************************************************************************************** */

    def list() {
        []
    }
	
	/* **************************************************************************************
	 * ********************************* DATA TABLES *************************************************
	
	 * this function is called when listing the models. It is called through ajax
	 * using the the data tables plugin in the show.gsp view and the javascript that
	 * calls the code is in main.js
	 *********************************************************************************** */
	
	def dataTables(){
		
		// set the variables needed to pass back to the data tables plugin to render the models
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		def sortColName
		
		//if the user searches for a model return the search results using the model service
		
		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = modelService.search(params.sSearch)
			
			total = searchResults.size()
			displayTotal = searchResults.size()
			
			if(total>0){
				data = searchResults
			}else{
				data=[]
			}
			
			//otherwise list the models using the models service and pass the relevant data
			//back to the data tables plugin request as json
			
			
		}else{
		
			order = params?.sSortDir_0
			sortColName = getSortField(params?.iSortCol_0.toInteger())
			
			data = modelService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortColName, order: order)
			total = modelService.count()
			displayTotal = modelService.count()
			
		}
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
		
		//NB. when the json is rendered it uses a custom json marshaller so that it includes the relevant
		//information (and doesn't return the whole database)
		//the corresponding json marshaller is stored in src/groovy/uk/co/mdc/model/xxxxxxMarshaller.groovy
				
		render model as JSON
	}
	
	/* **************************************************************************************
	 * *********************************** SHOW *****************************************************
	
	 * show the model in question using the find instance function and the dataElement service
	 * ...presuming they have the appropriate permissions
	 *************************************************************************************** */
	
	def show(Long id) {
		
		//use the find instance method to get the model in question
		
		def modelInstance = findInstance()
		
		//if you can't find it or don't have permission go back to the list page otherwise show the model
		
		if (!modelInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'model.label', default: 'Model'), id])
			redirect(action: "list")
			return
		}

		[modelInstance: modelInstance, relationshipTypes: RelationshipType.list()]
	}
	

	/* **************************************************************************************
	 * ************************************* CREATE ***************************************************
	 
	 * renders the model template so the user can create a model
	 * N.B additionally will only display objects that user has permission to read
	 *************************************************************************************** */
	
    def create() {
        [dataElements: dataElementService.list(), modelInstance: new Model(params)]
    }
	
	/* **************************************************************************************
	 * ************************************ SAVE BASKET COLLECTION **************************
	 
	 * calls the model service to create a model and from the data elements included in the users
	 * model basket. The service sets admin permissions
	 * on that object for the user in question and creates the model
	 *************************************************************************************** */
	
	
	
	def saveBasketModel(){
		
		
		//create the model with the parameters passed from the model basket
		
		def modelInstance = modelService.createFromBasket(params)
		
		//if there are any errors in the model put them into a format
		//that we can display and redirect
			
		def errors = []
		
		if(modelInstance.errors){
				
			def locale = Locale.getDefault()
				
			for (fieldErrors in modelInstance.errors) {
				for (error in fieldErrors.allErrors) {
					   errors.add(messageSource.getMessage(error, locale))
				}
			}
			if(errors.size()>0){
				//redirect to the model basket controller any errors displayed
				redirect(controller: "ModelBasket", action:"show", id: params?.model_basket_id, params: ['errors': errors, name: params?.name, description: params?.description])
				return
			}
		}
		
		redirect(action: "show", id: modelInstance.id)
	}
	
	/* **************************************************************************************
	 * ************************************ SAVE ****************************************************
	 
	 * calls the model service to create a model and  sets admin permissions
	 * on that object for the user in question
	 *************************************************************************************** */

    def save() {
		
		/* ***
		 * validate the model looking at it's schema specifications and ensuring they are mutually exclusive
		 * i.e. a model cannot have a data element that is both mandatory and optional
		 * ****/
		
		Boolean valid = validateLinkedDataTypes()
		
		if(!valid){
			render(view: "create", model: [dataElements: dataElementService.list(), modelInstance: new Model(params)])
			return
		}
		
		/* *****
		 * create the data element using the data element service
		 ******* */
		
		
		def modelInstance = modelService.create(params)
		
		/* ******
		 * check that the data element has been created without errors and render accordingly
		 ***** */
		
		if (!renderWithErrors('create', modelInstance)) {
			redirectShow(message(code: 'default.created.message', args: [message(code: 'dataElement.label', default: 'DataElement'), modelInstance.id]), modelInstance.id)
		}
		
    }
	
	/* **************************************************************************************
	 * ************************************** EDIT ********************************************
	
	 * this function redirects to the edit model screen
	 *********************************************************************************** */

    def edit(Long id) {
        def modelInstance = findInstance()
        if (!modelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'model.label', default: 'Model'), id])
            redirect(action: "list")
            return
        }

        [dataElements: dataElementService.list(), mandatoryDataElements: modelInstance.relations('MandatoryModelElement'), requiredDataElements: modelInstance.relations('RequiredModelElement'), optionalDataElements: modelInstance.relations('OptionalModelElement'), referenceDataElements: modelInstance.relations('ReferenceModelElement'), modelInstance: modelInstance]
    }
	
	/* **************************************************************************************
	 * ************************************ UPDATE **********************************************
	
	 * this function updates the model using the model service
	 *********************************************************************************** */

    def update(Long id, Long version) {
		
		/* ***
		 * validate the model looking at it's schema specifications and ensuring they are mutually exclusive
		 * i.e. a model cannot have a data element that is both mandatory and optional
		 * ****/
		
		Boolean valid = validateLinkedDataTypes()
		
		if(!valid){
			redirect(action: "edit", id: id)
			return
		}
		
		//get the model (redirect if you can't)
		
        def modelInstance = findInstance()
        if (!modelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'model.label', default: 'Model'), id])
            redirect(action: "list")
            return
        }
		
		//check that we have the right version i.e. no one else has updated the model whilst we have been
		//looking at it

        if (version != null) {
            if (modelInstance.version > version) {
                modelInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'model.label', default: 'Model')] as Object[],
                          "Another user has updated this Model while you were editing")
                render(view: "edit", model: [modelInstance: modelInstance])
                return
            }
        }
		
		
		//update the model using the model service
		
		modelService.update(modelInstance, params)
		
		if (!renderWithErrors('edit', modelInstance)) {
			redirectShow message(code: 'default.updated.message', args: [message(code: 'dataElement.label', default: 'DataElement'), modelInstance.id]), modelInstance.id
		}
		

        
    }
	
	/* **************************************************************************************
	 * ********************************* DELETE *************************************************
	
	 * this function deletes the model using the model service
	 *********************************************************************************** */

    def delete(Long id) {
		
		//get the model and redirect if it doesn't exist
        def modelInstance = findInstance()
        if (!modelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'model.label', default: 'Model'), id])
            redirect(action: "list")
            return
        }
		
		//call the model service  to delete the model

        try {
			
			modelService.delete(modelInstance)
			
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'model.label', default: 'Model'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'model.label', default: 'Model'), id])
            redirect(action: "show", id: id)
        }
    }
	
	/* **************************************************************************************
	 * ********************************* DELETE *************************************************
	
	 * this function removes a data element from a model
	 *********************************************************************************** */
	
	def removeDataElement() {
		
		Model model = modelService.get(params.modelId)
		DataElement dataElement = dataElementService.get(params.dataElementId)
		if(model && dataElement){
			modelService.removeDataElement(model, dataElement)
		}
		redirect(action: 'edit', id: params.modelId)
	}
	
	
	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	
	 * this function grant permission to the given model
	 *********************************************************************************** */
	
	
	def grant = {
		
				def model = findInstance()
				
				if (!model) return
		
				if (!request.post) {
					return [modelInstance: model]
				}
		
				modelService.addPermission(model, params.recipient, params.int('permission'))
		
				redirectShow "Permission $params.permission granted on Report $model.id " + "to $params.recipient", model.id
			}
	
	/* **********************************************************************************
	 * this function uses the model service to get the model so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */
	
	private Model findInstance() {
		def model = modelService.get(params.long('id'))
		if (!model) {
			flash.message = "Model not found with id $params.id"
			redirect action: list
		}
		model
	}
	
	/* **********************************************************************************
	 * this function redirects to the show model screen
	 *********************************************************************************** */
	
	private void redirectShow(message, id) {
		flash.message = message
		//redirect with message
				
		redirect(action: "show", id: id)
	}
	
	/* **********************************************************************************
	 * this function checks to see if the model passed to it contains errors i.e. when a
	 * service returns the element. It either returns false (if no errors) or it redirects
	 * to the view specified by the caller
	 *********************************************************************************** */

	private boolean renderWithErrors(String view, Model model) {
		if (model.hasErrors()) {
			render view: view, model: [dataElements: dataElementService.list(), modelInstance: model]
			return true
			
		}
		false
	}
	
	
	/* **********************************************************************************
	 * this function validates the parameters submitted when creating or updating a model
	 * in particular this validates that the data element schemas are mutually exclusive
	 * i.e. a data element cannot be both mandatory and optional within a given model
	 *********************************************************************************** */
	
	
	def validateLinkedDataTypes(){
		
		def mandatoryDataElements = params?.mandatoryDataElements
		
		def requiredDataElements = params.requiredDataElements
		
		def optionalDataElements = params.optionalDataElements
		
		def referenceDataElements = params.referenceDataElements
		
		if(mandatoryDataElements && requiredDataElements){
			if(catalogueElementService.parameterContains(mandatoryDataElements, requiredDataElements)){
				
				flash.message = 'Added data elements must either be mandatory or required for any given model, not both'
				
				return false
			}
		}
		
	
		if(mandatoryDataElements && optionalDataElements){
			if(catalogueElementService.parameterContains(mandatoryDataElements, optionalDataElements)){
				flash.message = 'Added data elements must either be mandatory or optional for any given model, not both.'
				return false
			}
		}
		

		if(mandatoryDataElements && referenceDataElements){
			if(catalogueElementService.parameterContains(mandatoryDataElements, referenceDataElements)){
				flash.message = 'Added data elements must either be mandatory or reference for any given model, not both'
				return false
			}
		}
		
		if(requiredDataElements && referenceDataElements){
			if(catalogueElementService.parameterContains(requiredDataElements, referenceDataElements)){
				flash.message = 'Added data elements must either be mandatory or reference for any given model, not both'
				return false
			}
		}
		
		if(requiredDataElements && optionalDataElements){
			if(catalogueElementService.parameterContains(requiredDataElements, optionalDataElements)){
				flash.message = 'Added data elements must either be required or optional for any given model, not both'
				return false
			}
		}
		
		if(optionalDataElements && referenceDataElements){
			if(catalogueElementService.parameterContains(optionalDataElements, referenceDataElements)){
				flash.message = 'Added data elements must either be required or reference for any given model, not both'
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
