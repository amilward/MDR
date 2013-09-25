package uk.co.mdc.model

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

import org.codehaus.groovy.grails.plugins.searchable.*
import org.springframework.security.acls.model.Permission

import grails.plugins.springsecurity.Secured

import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_USER'])
class ValueDomainController {
	
	def valueDomainService
	def dataElementService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	/* **************************************************************************************
	 * ************************************* INDEX *********************************************************
	 
	 * default redirect to list value domains page
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
	
	 * this function is called when listing the value domains. It is called through ajax
	 * using the the data tables plugin in the show.gsp view and the javascript that
	 * calls the code is in main.js
	 *********************************************************************************** */
	
	
	def dataTables(){
		
		// set the variables needed to pass back to the data tables plugin to render the value domains
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		def sortColName
		
		//if the user searches for a value domain return the search results using the value domain service
		
		if(params?.sSearch!='' && params?.sSearch!=null){
			
			List searchResults = valueDomainService.search(params.sSearch)
			
			total = searchResults.size()
			displayTotal = searchResults.size()
			
			if(total>0){
				data = searchResults
			}else{
				data=[]
			}
			
			//otherwise list the value domain using the data elements service and pass the relevant data
			//back to the data tables plugin request as json
			
		}else{
		
			order = params?.sSortDir_0
			sortColName = getSortField(params?.iSortCol_0.toInteger())
			
			data = valueDomainService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortColName, order: order)
			total = valueDomainService.count()
			displayTotal = valueDomainService.count()
			
		}
		
		//NB. when the json is rendered it uses a custom json marshaller so that it includes the relevant
		//information (and doesn't return the whole database)
		//the corresponding json marshaller is stored in src/groovy/uk/co/mdc/model/xxxxxxMarshaller.groovy
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
				
		render model as JSON
	}
	
	/* **************************************************************************************
	 * *********************************** SHOW *****************************************************
	
	 * show the value domain in question using the find instance function and the dataElement service
	 * ...presuming they have the appropriate permissions
	 *************************************************************************************** */
	
	
	def show() {
		def valueDomainInstance = findInstance()
		if (!valueDomainInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
			redirect(action: "list")
			return
		}

		[valueDomainInstance: valueDomainInstance]
	}
	
	
	
	
	/* **************************************************************************************
	 * ************************************* CREATE ***************************************************
	 
	 * renders the value domain template so the user can create a value domains
	 * N.B additionally will only display objects that user has permission to read i.e. if
	 * you are going to link the value domain and value domain you need read permission to see
	 * the value domain
	 *************************************************************************************** */

    def create() {
        [dataElements: dataElementService.list(), dataTypes: DataType.list(), externalReferences: ExternalReference.list(), valueDomainInstance: new ValueDomain(params)]
    }

	/* **************************************************************************************
	 * ************************************ SAVE ****************************************************
	 
	 * calls the value domain service to create a value domain and  sets admin permissions
	 * on that object for the user in question
	 *************************************************************************************** */
	
    def save() {
		
		/* *****
		 * create the value domain using value domain service
		 ******* */
		
		
		def valueDomainInstance = valueDomainService.create(params)
		
		/* ******
		 * check that the value domain has been created without errors and render accordingly
		 ***** */
		
		if (!renderWithErrors('create', valueDomainInstance)) {
			redirectShow(message(code: 'default.created.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), valueDomainInstance.id]), valueDomainInstance.id)
		}
    }
	
	
	/* **************************************************************************************
	 * ************************************** EDIT ********************************************
	
	 * this function redirects to the edit value domain screen
	 *********************************************************************************** */
	
	def edit(Long id) {
		def valueDomainInstance = findInstance()
		if (!valueDomainInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
			redirect(action: "list")
			return
		}

		[dataElements: dataElementService.list(), selectedDataElements: valueDomainInstance.dataElementValueDomains(), dataTypes: DataType.list(), externalSynonyms: ExternalReference.list(), valueDomainInstance: valueDomainInstance]
	}
    
	/* **************************************************************************************
	 * ************************************ UPDATE **********************************************
	
	 * this function updates the value domain using the value domain service
	 *********************************************************************************** */
	
	def update(Long id, Long version) {
		
		//get the value domain
		def valueDomainInstance = findInstance()
		//redirect if value domain doesn't exist
		if (!valueDomainInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
			redirect(action: "list")
			return
		}
		
		//check that we have the right version i.e. no one else has updated the value domain whilst we have been
		//looking at it
		if (version != null) {
			if (valueDomainInstance.version > version) {
				valueDomainInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'valueDomain.label', default: 'ValueDomain')] as Object[],
						  "Another user has updated this ValueDomain while you were editing")
				render(view: "edit", model: [dataElements: DataElement.list(), dataTypes: DataType.list(), valueDomainInstance: valueDomainInstance])
				return
			}
		}
		
		//get  a data type object from the id in the parameter
		if(params?.dataType){
			DataType dataType = DataType.get(params?.dataType)
			params.dataType = dataType
		}
		
		
		//update the value domain
		valueDomainService.update(valueDomainInstance, params)
		
		if (!renderWithErrors('edit', valueDomainInstance)) {
			redirectShow message(code: 'default.updated.message', args: [message(code: 'dataElement.label', default: 'DataElement'), valueDomainInstance.id]), valueDomainInstance.id
		}
		 
	}
	
	/* **************************************************************************************
	 * ********************************* DELETE *************************************************
	
	 * this function deletes the value domain using the value domain service
	 *********************************************************************************** */
	
	def delete() {
		
		//get the value domain in question
		def valueDomainInstance = findInstance()
		
		//if it doesn't exist redirect the user
		if (!valueDomainInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
			redirect(action: "list")
			return
		}

		//call value domain service to delete the instance
		try {
			
			valueDomainService.delete(valueDomainInstance)
			
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
			redirect(action: "show", id: id)
		}
	}
	
	
	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	
	 * this function grant permission to the given value domain
	 *********************************************************************************** */
	
	
	def grant = {
		
				def valueDomain = findInstance()
				
				if (!valueDomain) return
		
				if (!request.post) {
					return [valueDomainInstance: valueDomain]
				}
		
				valueDomainService.addPermission(valueDomain, params.recipient, params.int('permission'))
		
				redirectShow "Permission $params.permission granted on Report $valueDomain.id " + "to $params.recipient", valueDomain.id
			}
	
	/* **********************************************************************************
	 * this function uses the valueDomain service to get the value domain so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */
	
	private ValueDomain findInstance() {
		def valueDomain = valueDomainService.get(params.long('id'))
		if (!valueDomain) {
			flash.message = "valueDomain not found with id $params.id"
			redirect action: list
		}
		valueDomain
	}
	
	/* **********************************************************************************
	 * this function redirects to the show value domain screen
	 *********************************************************************************** */
	
	private void redirectShow(message, id) {
		flash.message = message
		//redirect with message
				
		redirect(action: "show", id: id)
	}
	
	/* **********************************************************************************
	 * this function checks to see if the value domain passed to it contains errors i.e. when a
	 * service returns the element. It either returns false (if no errors) or it redirects
	 * to the view specified by the caller
	 *********************************************************************************** */

	private boolean renderWithErrors(String view, ValueDomain valueDomain) {
		if (valueDomain.hasErrors()) {
			render view: view, model: [valueDomainInstance: valueDomain, dataElements: dataElementService.list(), dataTypes: DataType.list(), externalSynonyms: ExternalReference.list()]
			return true
			
		}
		false
	}

	
	/*this function is used by the dataTables to map the columns to the data*/
	
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
				field = "dataType"
			break
			
			case 3:
				field = "format"
			break
			
			case 4:
				field = "conceptualDomain"
			break
			
			default:
				field = "conceptualDomain"
			break
		}
		
		return field
		
	}
	
}
