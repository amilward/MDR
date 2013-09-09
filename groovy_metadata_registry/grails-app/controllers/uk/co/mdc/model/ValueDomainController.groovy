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
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		def sortColName
		

		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = valueDomainService.search(params.sSearch, [max:params.iDisplayLength])
			
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
			
			data = valueDomainService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortColName, order: order)
			total = valueDomainService.count()
			displayTotal = valueDomainService.count()
			
		}
		
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
				
		render model as JSON
	}
	
	/* **************************************************************************************
	 * ************************************* CREATE ***************************************************
	 
	 * renders the value domain template so the user can create a data elements
	 * N.B additionally will only display objects that user has permission to read i.e. if
	 * you are going to link the value domain and data element you need read permission to see
	 * the data element
	 *************************************************************************************** */

    def create() {
        [dataElements: dataElementService.list(), dataTypes: DataType.list(), externalSynonyms: ExternalSynonym.list(), valueDomainInstance: new ValueDomain(params)]
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
	 * ********************************* GRANT *************************************************
	
	 * this function grant permission to the given data element
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
	 * this function uses the valueDomain service to get the data element so that
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

	private boolean renderWithErrors(String view, ValueDomain valueDomain) {
		if (valueDomain.hasErrors()) {
			render view: view, model: [valueDomainInstance: valueDomain, dataElements: dataElementService.list(), dataTypes: DataType.list(), externalSynonyms: ExternalSynonym.list()]
			return true
			
		}
		false
	}

	
	/* ******************************************************************
	 * ******************************************************************
	 * ******************************************************************
	 * OLD CODE HERE 
	 * ******************************************************************
	 * 
	 * ******************************************************************
	 * */
	
	
	def show(Long id) {
		def valueDomainInstance = ValueDomain.get(id)
		if (!valueDomainInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
			redirect(action: "list")
			return
		}

		[valueDomainInstance: valueDomainInstance]
	}

	def edit(Long id) {
		def valueDomainInstance = ValueDomain.get(id)
		if (!valueDomainInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
			redirect(action: "list")
			return
		}

		[dataElements: DataElement.list(), selectedDataElements: valueDomainInstance.dataElementValueDomains(), dataTypes: DataType.list(), externalSynonyms: ExternalSynonym.list(), valueDomainInstance: valueDomainInstance]
	}

	def update(Long id, Long version) {
		def valueDomainInstance = ValueDomain.get(id)
		if (!valueDomainInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (valueDomainInstance.version > version) {
				valueDomainInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'valueDomain.label', default: 'ValueDomain')] as Object[],
						  "Another user has updated this ValueDomain while you were editing")
				render(view: "edit", model: [dataElements: DataElement.list(), dataTypes: DataType.list(), valueDomainInstance: valueDomainInstance])
				return
			}
		}

		if(params?.dataType){
			DataType dataType = DataType.get(params?.dataType)
			params.dataType = dataType
		}
		
		//remove external synonyms
		
		unLinkExternalSynonyms(valueDomainInstance)
		
		valueDomainInstance.properties = params

		if (!valueDomainInstance.save(flush: true)) {
			render(view: "edit", model: [dataElements: DataElement.list(), dataTypes: DataType.list(), valueDomainInstance: valueDomainInstance])
			return
		}

	
		linkDataElements(valueDomainInstance)

		flash.message = message(code: 'default.updated.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), valueDomainInstance.id])
		redirect(action: "show", id: valueDomainInstance.id)
	}

	def delete(Long id) {
		def valueDomainInstance = ValueDomain.get(id)
		
		if (!valueDomainInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
			redirect(action: "list")
			return
		}

		try {
			
			valueDomainInstance.prepareForDelete()
			
			valueDomainInstance.delete(flush: true)
			
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
			redirect(action: "show", id: id)
		}
	}
	
	
	
	def removeDataElement() {
		ValueDomain valueDomain = ValueDomain.get(params.valueDomainId)
		DataElement dataElement = DataElement.get(params.dataElementId)
		if(valueDomain && dataElement){
			valueDomain.removeFromDataElementValueDomains(dataElement)
		}
		redirect(action: 'edit', id: params.valueDomainId)
	}
	
	
	def removeSynonym(){
		ValueDomain valueDomain = ValueDomain.get(params.valueDomainId)
		ExternalSynonym externalSynonym = ExternalSynonym.get(params.synonymId)
		
		if(valueDomain && externalSynonym){
			valueDomain.removeFromExternalSynonyms(externalSynonym)
		}
		
		redirect(action: 'edit', id: params.valueDomainId)
	}
	
	
	def unLinkExternalSynonyms(valueDomainInstance){
		
			//if all data elements need to be removed or only a few elements need to be removed
			
			if(params?.externalSynonyms==null && valueDomainInstance?.externalSynonyms.size()>0){
				
				def externalSynonyms = []
				externalSynonyms += valueDomainInstance?.externalSynonyms
				
				externalSynonyms.each{ externalSynonym->
					valueDomainInstance.removeFromExternalSynonyms(externalSynonym)
				}
				
	
			}else if(params.externalSynonyms){
		
				if(params?.externalSynonyms.size() < valueDomainInstance?.externalSynonyms.size()){
			
				def externalSynonyms = []
				
				externalSynonyms += valueDomainInstance?.externalSynonyms
				
				externalSynonyms.each{ externalSynonym->
					
	
					if(params?.externalSynonyms instanceof String){
						
							if(params?.externalSynonyms!=externalSynonym){
						
								valueDomainInstance.removeFromExternalSynonyms(externalSynonym)
							
							}
						
						}else{
							
							if(!params?.externalSynonyms.contains(externalSynonym)){
								
								valueDomainInstance.removeFromExternalSynonyms(externalSynonym)
								
							}
						
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
