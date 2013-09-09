package uk.co.mdc.model

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import org.codehaus.groovy.grails.plugins.searchable.*
import org.springframework.security.acls.model.Permission
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER']) 

class DataElementController {

    static allowedMethods = [listJSON: "GET",save: "POST", update: "POST", delete: "POST"]
	
	def dataElementService
	def valueDomainService
	
	/* **************************************************************************************
	 * ************************************* INDEX *********************************************************
	 
	 * default redirect to list dataElement page 
	 ********************************************************************************************* */
	
	def index =  {
		redirect(action: "list", params: params)
	}
	
	/* **************************************************************************************
	 * ************************************* LIST ***************************************************
	 
	 *....only use this to render the list template as the datatables method is used instead
	 * to list all the data elements
	 *************************************************************************************** */

	def list =  {
		[]
	}
	
	
	/* **************************************************************************************
	 * ********************************* DATA TABLES *************************************************
	
	 * this function is called when listing the data elements. It is called through ajax
	 * using the the data tables plugin in the show.gsp view and the javascript that 
	 * calls the code is in main.js
	 *********************************************************************************** */
	
	
	
	def dataTables(){
		
		// set the variables needed to pass back to the data tables plugin to render the data elements
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		
		//if the user searches for a data element return the search results using the data Element service
		

		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = dataElementService.search(params.sSearch, [max:params.iDisplayLength])
			
			total = searchResults.total
			displayTotal = searchResults.total
			
			if(total>0){
				data = searchResults.results
			}else{
				data=[]
			}
			
			//otherwise 
			
		}else{
		
			order = params?.sSortDir_0
			sortCol = getSortField(params?.iSortCol_0.toInteger())
			
			data = dataElementService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortCol, order: order)
			total = dataElementService.count()
			displayTotal = dataElementService.count()
			
		}
		
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
				
		render model as JSON
	}
	
	
	/* **************************************************************************************
	 * *********************************** SHOW *****************************************************
	
	 * show the data element in question using the find instance function and the dataElement service
	 * ...presuming they have the appropriate permissions
	 *************************************************************************************** */
	
	def show = {
		def dataElementInstance = findInstance()
		
		if (!dataElementInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
			redirect(action: "list")
			return
		}

		[dataElementInstance: dataElementInstance]
	}
	
	/* **************************************************************************************
	 * ************************************* CREATE ***************************************************
	 
	 * renders the data element template so the user can create a data elements
	 * N.B additionally will only display objects that user has permission to read
	 *************************************************************************************** */
	
	def create = {
		[valueDomains: valueDomainService.list(), dataElements: dataElementService.list(), externalSynonyms: ExternalSynonym.list(), dataElementInstance: new DataElement(params)]
	}
	
	//NOTE TO SELF - NEED TO UPDATE WHEN RELEVANT SERVICES ARE CREATED
	//IN ADDITION NEED TO THINK ABOUT HOW TO LINK OBJECTS AS BOTH SIDES OF LINK REQURE WRITE PERMISSION
	//POTENTIALLY COULD CREATE A 'PARTIAL WRITE' PERMISSION THAT ALLOWS USERS TO LINK BUT NOT EDIT
	
	/* **************************************************************************************
	 * ************************************ SAVE ****************************************************
	 
	 * calls the data element service to create a dataElement and  sets admin permissions
	 * on that object for the user in question
	 *************************************************************************************** */

	def save = {
		
		/* ***
		 * validate the data element looking at it's parent-children-synonyms and ensuring they are mutually exclusive
		 * i.e. a dataElement cannot have a subElement that is the same as it's parent element
		 * ****/
		
		Boolean valid = validateDataElement()
		
		if(!valid){
			render(view: "create", model: [valueDomains: ValueDomain.list(), dataElements: DataElement.list(), dataElementInstance: new DataElement(params)])
			return
		}
		
		/* *****
		 * create the data element using the data element service
		 ******* */ 
		
		
		def dataElementInstance = dataElementService.create(params)
		
		/* ******
		 * check that the data element has been created without errors and render accordingly
		 ***** */
		
		if (!renderWithErrors('create', dataElementInstance)) {
			redirectShow(message(code: 'default.created.message', args: [message(code: 'dataElement.label', default: 'DataElement'), dataElementInstance.id]), dataElementInstance.id)
		}
		
	}
	
	/* **************************************************************************************
	 * ************************************** EDIT ********************************************
	
	 * this function redirects to the edit data element screen
	 *********************************************************************************** */
	
	def edit(Long id) {
		def dataElementInstance = findInstance()
		if (!dataElementInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
			redirect(action: "list")
			return
		}

		[valueDomains: ValueDomain.list(), selectedValueDomains: dataElementInstance.dataElementValueDomains() , dataElements: dataElementService.list(), externalSynonyms: ExternalSynonym.list(), dataElementInstance: dataElementInstance]
	}
	
	/* **************************************************************************************
	 * ************************************ UPDATE **********************************************
	
	 * this function updates the data element using the data element service
	 *********************************************************************************** */
	
	def update(Long id, Long version) {
		
		/* ***
		 * validate the data element looking at it's parent-children-synonyms and ensuring they are mutually exclusive
		 * i.e. a dataElement cannot have a subElement that is the same as it's parent element
		 * ******/
		
		Boolean valid = validateDataElement()
		
		if(!valid){
			redirect(action: "edit", id: params.id)
			return
		}
		
		//get the data element
		
		def dataElementInstance = findInstance()
		
		if (!dataElementInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
			redirect(action: "list")
			return
		}
		
		//check that we have the right version i.e. no one else has updated the data element whilst we have been 
		//looking at it

		if (version != null) {
			if (dataElementInstance.version > version) {
				dataElementInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'dataElement.label', default: 'DataElement')] as Object[],
						  "Another user has updated this DataElement while you were editing")
				render(view: "edit", model: [dataElementInstance: dataElementInstance,valueDomains: ValueDomain.list(), dataElements: DataElement.list()])
				return
			}
		}

		dataElementService.update(dataElementInstance, params)
		
		if (!renderWithErrors('edit', dataElementInstance)) {
			redirectShow message(code: 'default.updated.message', args: [message(code: 'dataElement.label', default: 'DataElement'), dataElementInstance.id]), dataElementInstance.id
		}
		 
		
	}
	
	/* **************************************************************************************
	 * ********************************* DELETE *************************************************
	
	 * this function deletes the data element using the data element service
	 *********************************************************************************** */
	
	def delete(Long id) {
		
		//get the data element in question
		def dataElementInstance = findInstance()
		
		//if no element exists redirect
		if (!dataElementInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
			redirect(action: "list")
			return
		}

		//call data element service to delete element
		
		try {
			
			dataElementService.delete(dataElementInstance)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
			redirect(action: "show", id: id)
		}
		
	}
	
	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	
	 * this function grant permission to the given data element
	 *********************************************************************************** */
	
	
	def grant = {
		
				def dataElement = findInstance()
				
				if (!dataElement) return
		
				if (!request.post) {
					return [dataElementInstance: dataElement]
				}
		
				dataElementService.addPermission(dataElement, params.recipient, params.int('permission'))
		
				redirectShow "Permission $params.permission granted on Report $dataElement.id " + "to $params.recipient", dataElement.id
			}
	
	/* **********************************************************************************
	 * this function uses the dataElement service to get the data element so that 
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */
	
	private DataElement findInstance() {
		def dataElement = dataElementService.get(params.long('id'))
		if (!dataElement) {
			flash.message = "DataElement not found with id $params.id"
			redirect action: list
		}
		dataElement
	}
	
	/* **********************************************************************************
	 * this function redirects to the show data element screen 
	 *********************************************************************************** */
	
	private void redirectShow(message, id) {
		flash.message = message
		//redirect with message
				
		redirect(action: "show", id: id, model: [valueDomains: ValueDomain.list()])
	}
	
	/* **********************************************************************************
	 * this function checks to see if the data element passed to it contains errors i.e. when a 
	 * service returns the element. It either returns false (if no errors) or it redirects 
	 * to the view specified by the caller
	 *********************************************************************************** */

	private boolean renderWithErrors(String view, DataElement dataElement) {
		if (dataElement.hasErrors()) {
			render view: view, model: [dataElementInstance: dataElement, valueDomains: ValueDomain.list(), dataElements: DataElement.list()]
			return true
			
		}
		false
	}
	
	
	
	
	

	
	Boolean validateDataElement(){
		
		
		ArrayList children
		
		//check if subelements contain the given element
		
		if(params?.subElements!=null && params?.id!=null){
			
			children = getChildren()
			
			if(children.contains(params.id)){
				params.subElements = ''
				flash.message = 'Error: Sub elements must not contain the element itself'
				return false
			}

		}
		
		//check if parent elements contain the given element
		
		if(!params?.parent?.id.isEmpty() &&  params?.id!=null){
			if(params.parent.id == params.id){
				params.parent = ''
				flash.message = 'Error: Parent elements must not contain the element itself'
				return false
			}

		}
		
		//check if subelements contain the parent element
		
		if(params?.subElements!=null && params?.parent?.id!=null){
			
			if(children==null){
				children = getChildren()
			}
			
			if(!ChildParentValid(params.parent.id.value.toString(), children)){
				params.subElements = ''
				flash.message = 'Error: Sub elements must not contain the parent element'
				return false
			}
		}
		
		//check if any synonyms are contained within the subelements or the parent element
		
		if(params.synonyms!=null){
			if(params?.subElements!=null){
				if(parameterContains(params.synonyms, params?.subElements)){
					params.subElements = ''
					flash.message = 'Error: Data Element Sub elements and Data Element Synonyms must be mutually exclusive'
					return false
				}
			}
			
			if(params?.parent!=null){
				if(parameterContains(params.synonyms, params?.parent)){
					params.parent = ''
					flash.message = 'Error: Data Element Parent Elements and Data Element Synonyms must be mutually exclusive'
					return false
				}
			}

		}
		
		
		return true
	}

	
	Boolean ChildParentValid(String parent, ArrayList children){
		
		if(children.contains(parent)){
			return false
		}
		
		return true
	}
	
	
	List getChildren(){
		
		List children = new ArrayList()
		
		if(params.subElements.class.isArray()){
			children.addAll(params.subElements)
		}else{
			children.add(params.subElements.value.toString())
		}
		
		return children

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
				field = "parent"
			break
			
			case 3:
				field = "dataElementConcept"
			break
			
			default:
				field = "dataElementConcept"
			break
		}
		
		return field
		
	}
	
	def parameterContains(params1, params2){
			
			if(params1 instanceof String[] && params2 instanceof String[]){
				
				params2.each{ params->
					
					if(params1.contains(params)){
						return true
					}
					
				}
				
			}else if(params1 instanceof String[] && params2 instanceof String){
			
				if(params1.contains(params2)){
					return true
				}
			
			}else if(params1 instanceof String && params2 instanceof String[]){
			
				if(params2.contains(params1)){
					return true
				}
			
			}else{
				if(params1==params2){
					return true
				}
			}
			
			return false		
	}
	
	
}
