package uk.co.mdc.model

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException


import org.codehaus.groovy.grails.plugins.searchable.*
import org.springframework.security.acls.model.Permission

import grails.plugins.springsecurity.Secured

import org.springframework.dao.DataIntegrityViolationException

import java.util.ArrayList;
import java.util.List;

@Secured(['ROLE_USER'])
class DataElementConceptController {
	
	def dataElementConceptService
	def valueDomainService
	def dataElementService
	

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
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
	 * to list all the value domains
	 *************************************************************************************** */

    def list() {
        []
    }
	
	/* **************************************************************************************
	 * ********************************* DATA TABLES *************************************************
	
	 * this function is called when listing the data element concepts. It is called through ajax
	 * using the the data tables plugin in the show.gsp view and the javascript that
	 * calls the code is in main.js
	 *********************************************************************************** */
	
	
	def dataTables(){
		
		// set the variables needed to pass back to the data tables plugin to render the concepts
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		def sortColName
		
		//if the user searches for a concept return the search results using the dataelementconcept service
		

		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = dataElementConceptService.search(params.sSearch)
			
			total = searchResults.size()
			displayTotal = searchResults.size()
			
			if(total>0){
				data = searchResults
			}else{
				data=[]
			}
			
			//otherwise list the data elements using the data element concept service and pass the relevant data
			//back to the data tables plugin request as json
			
		}else{
		
			order = params?.sSortDir_0
			sortColName = getSortField(params?.iSortCol_0.toInteger())
			
			data = dataElementConceptService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortColName, order: order)
			total = DataElementConcept.count()
			displayTotal = DataElementConcept.count()
			
		}
		
		//NB. when the json is rendered it uses a custom json marshaller so that it includes the relevant
		//information (and doesn't return the whole database)
		//the corresponding json marshaller is stored in src/groovy/uk/co/mdc/model/xxxxxxMarshaller.groovy
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
				
		render model as JSON
	}
	
	/* **************************************************************************************
	 * *********************************** SHOW *****************************************************
	
	 * show the data element concept in question using the find instance function and the dataElementConcept service
	 * ...presuming they have the appropriate permissions
	 *************************************************************************************** */
	
	def show(Long id) {
		def dataElementConceptInstance = findInstance()
		if (!dataElementConceptInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), id])
			redirect(action: "list")
			return
		}

		[dataElementConceptInstance: dataElementConceptInstance]
	}

	
	/* **************************************************************************************
	 * ************************************* CREATE ***************************************************
	 
	 * renders the value domain template so the user can create a value domains
	 * N.B additionally will only display objects that user has permission to read i.e. if
	 * you are going to link the value domain and value domain you need read permission to see
	 * the value domain
	 *************************************************************************************** */
	
	def create() {
		[dataElementConcepts: dataElementConcept.list(), dataElements: dataElementService.list(), dataElementConceptInstance: new DataElementConcept(params)]
	}
	
	/* **************************************************************************************
	 * ************************************ SAVE ****************************************************
	 
	 * calls the value domain service to create a value domain and  sets admin permissions
	 * on that object for the user in question
	 *************************************************************************************** */
	
	def save() {
		
		/* ***
		 * validate the data element concept looking at it's parent-children-synonyms and ensuring they are
		 *  mutually exclusive i.e. a data element concept cannot have a subConcept that is the same as it's 
		 *  parent concept
		 * ****/
		
		Boolean valid = validateConcept()
		
		if(!valid){
			render(view: "edit", model: [dataElementConcepts: DataElementConcept.list(), dataElementConceptInstance: new DataElementConcept(params)])
			return
		}
		
		/* *****
		 * create the data element concept using the data element concept service
		 ******* */
		
		def dataElementConceptInstance = dataElementConceptService.create(params)
		
		/* ******
		 * check that the data element has been created without errors and render accordingly
		 ***** */
		
		if (!renderWithErrors('create', dataElementConceptInstance)) {
			redirectShow(message(code: 'default.created.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), dataElementConceptInstance.id]), dataElementConceptInstance.id)
		}
		
	}
	
	/* **************************************************************************************
	 * ************************************** EDIT ********************************************
	
	 * this function redirects to the edit data element concept screen
	 *********************************************************************************** */
	
	def edit(Long id) {
		def dataElementConceptInstance = findInstance()
		if (!dataElementConceptInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), id])
			redirect(action: "list")
			return
		}

		[dataElementConcepts: DataElementConcept.list(), dataElements: dataElementService.list(), dataElementConceptInstance: dataElementConceptInstance]
	}
	
	/* **************************************************************************************
	 * ************************************ UPDATE **********************************************
	
	 * this function updates the data element concept using the data element concept service
	 *********************************************************************************** */
	
	def update(Long id, Long version) {
		
		
		/* ***
		 * validate the data element concept looking at it's parent-children-synonyms and 
		 * ensuring they are mutually exclusive
		 * i.e. a dataElementConcept cannot have a subConcept that is the same as it's parent concept
		 * ******/
		
		Boolean valid = validateConcept()
		
		if(!valid){
			redirect(action: "edit", id: params.id)
			return
		}
		
		//get the data element concept
		
		def dataElementConceptInstance = findInstance()
		
		if (!dataElementConceptInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), id])
			redirect(action: "list")
			return
		}
		
		//check that we have the right version i.e. no one else has updated the data element whilst we have been
		//looking at it
		
		if (version != null) {
			if (dataElementConceptInstance.version > version) {
				dataElementConceptInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'dataElementConcept.label', default: 'DataElementConcept')] as Object[],
						  "Another user has updated this DataElementConcept while you were editing")
				render(view: "edit", model: [dataElementConcepts: dataElementConceptService.list(), dataElements: dataElementService.list(), dataElementConceptInstance: dataElementConceptInstance])
				return
			}
		}
		
		//update the data element concept
		
		dataElementConceptService.update(dataElementConceptInstance, params)
		
		if (!renderWithErrors('edit', dataElementConceptInstance)) {
			redirectShow message(code: 'default.updated.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), dataElementConceptInstance.id]), dataElementConceptInstance.id
		}

	}
	
	/* **************************************************************************************
	 * ********************************* DELETE *************************************************
	
	 * this function deletes the value domain using the value domain service
	 *********************************************************************************** */
	
	
	def delete(Long id) {
		
		//get the concept in question
		def dataElementConceptInstance = findInstance()
		
		//if it doesn't exist redirect the user
		if (!dataElementConceptInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), id])
			redirect(action: "list")
			return
		}
		
		//call data element concept service to delete the instance
		try {
			
			dataElementConceptService.delete(dataElementConceptInstance)
			
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), id])
			redirect(action: "show", id: id)
		}
	}
	
	
	
	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	
	 * this function grant permission to the given data element concept
	 *********************************************************************************** */
	
	
	def grant = {
		
				def dataElementConcept = findInstance()
				
				if (!dataElementConcept) return
		
				if (!request.post) {
					return [dataElementConceptInstance: dataElementConcept]
				}
		
				dataElementConceptService.addPermission(dataElementConcept, params.recipient, params.int('permission'))
		
				redirectShow "Permission $params.permission granted on Report $dataElementConcept.id " + "to $params.recipient", dataElementConcept.id
			}
	
	/* **********************************************************************************
	 * this function uses the dataElementConcept service to get the data element concept so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */
	
	private DataElementConcept findInstance() {
		def dataElementConcept = dataElementConceptService.get(params.long('id'))
		if (!dataElementConcept) {
			flash.message = "dataElementConcept not found with id $params.id"
			redirect action: list
		}
		dataElementConcept
	}
	
	/* **********************************************************************************
	 * this function redirects to the show data element concept screen
	 *********************************************************************************** */
	
	private void redirectShow(message, id) {
		flash.message = message
		//redirect with message
				
		redirect(action: "show", id: id)
	}
	
	/* **********************************************************************************
	 * this function checks to see if the data element concept passed to it contains errors i.e. when a
	 * service returns the element. It either returns false (if no errors) or it redirects
	 * to the view specified by the caller
	 *********************************************************************************** */

	private boolean renderWithErrors(String view, DataElementConcept dataElementConcept) {
		if (dataElementConcept.hasErrors()) {
			render view: view, model: [id:dataElementConcept.id]
			return true
			
		}
		false
	}
	
	
	/* **********************************************************************************
	 * this function validates the parameters submitted when creating or updating a data element concept
	 * in particular this validates the relationship between a data element concept and it's sub concepts
	 * and it's parent conceps. i.e. a data element concept cannot have the same sub concept and parent concept
	 *********************************************************************************** */
	
	
	Boolean validateConcept(){
			
		ArrayList children
		
		//check if subConcepts contain the given element
		
		if(params?.subConcepts!=null && params?.id!=null){
			
			children = getChildren()
			
			if(children.contains(params.id)){
				params.subConcepts = ''
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
		
		//check if subConcepts contain the parent element
		
		if(params?.subConcepts!=null && params?.parent?.id!=null){
			
			if(children==null){
				children = getChildren()
			}
			
			if(!ChildParentValid(params.parent.id.value.toString(), children)){
				params.subConcepts = ''
				flash.message = 'Error: Sub elements must not contain the parent element'
				return false
			}
		}
		return true
	}
	
	/*check if the parent concept is contained in any of data concept's sub concepts*/
	
	Boolean ChildParentValid(String parent, ArrayList children){
		
		if(children.contains(parent)){
			return false
		}
		
		return true
	}
	
	/* find all the subconcepts of a data element concept and return them*/
	
	List getChildren(){
		
		List children = new ArrayList()
		
		if(params.subConcepts.class.isArray()){
			children.addAll(params.subConcepts)
		}else{
			children.add(params.subConcepts.value.toString())
		}
		
		return children
		
	}
	
	/*this function is used by the dataTables to map the column to the data*/
	
	String getSortField(Integer column){
		
		def field
		
		switch(column){
			
			case 0:
				field = "name"
			break
			
			case 1:
				field = "parent"
			break
			
			default:
				field = "name"
			break
		}
		
		return field
		
	}
	
}
