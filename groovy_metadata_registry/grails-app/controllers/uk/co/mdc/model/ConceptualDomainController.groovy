package uk.co.mdc.model

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException
import org.apache.commons.lang.StringUtils

class ConceptualDomainController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def conceptualDomainService
	def MDRService
	def valueDomainService
	
	/* **************************************************************************************
	 * ************************************* INDEX *************************************************
	 * default redirect to list page
	 * ********************************************************************************************* */
	
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
	
	 * this function is called when listing the conceptual Domains. It is called through ajax
	 * using the the data tables plugin in the show.gsp view and the javascript that
	 * calls the code is in main.js
	 *********************************************************************************** */
	
	def dataTables(){
		
		// set the variables needed to pass back to the data tables plugin to render the conceptual domains
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		def sortColName
		
		//if the user searches for a conceptual domain return the search results using the conceptual domain service
		
		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = conceptualDomainService.search(params.sSearch)
			
			total = searchResults.size()
			displayTotal = searchResults.size()
			
			if(total>0){
				data = searchResults
			}else{
				data=[]
			}
			
			//otherwise list the conceptual domains using the conceptual domain service and pass the relevant data
			//back to the data tables plugin request as json
			
		}else{
		
			order = params?.sSortDir_0
			sortColName = getSortField(params?.iSortCol_0.toInteger())
			
			data = conceptualDomainService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortColName, order: order)
			total = conceptualDomainService.count()
			displayTotal = conceptualDomainService.count()
			
		}
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
		
		//NB. when the json is rendered it uses a custom json marshaller so that it includes the relevant
		//information (and doesn't return the WHOLE database due to all the links)
		//the corresponding json marshaller is stored in src/groovy/uk/co/mdc/model/xxxxxxMarshaller.groovy
				
		render model as JSON
	}
	
	/* **************************************************************************************
	 * *********************************** SHOW *****************************************************
	
	 * show the conceptual domain in question using the find instance function and the conceptual domain service
	 * ...presuming they have the appropriate permissions
	 *************************************************************************************** */
	
	def show(Long id) {
		
		//use the find instance method to get the conceptualdomain in question
		def conceptualDomainInstance = findInstance()
		
		//if you can't find it or don't have permission go back to the list page otherwise show the conceptualdomain
		if (!conceptualDomainInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), id])
			redirect(action: "list")
			return
		}

		[conceptualDomainInstance: conceptualDomainInstance]
	}
	
	/* **************************************************************************************
	 * ************************************* CREATE ***************************************************
	 
	 * renders the collection template so the user can create a conceptual domain
	 * N.B additionally will only display objects that user has permission to read
	 *************************************************************************************** */

    def create() {
        [valueDomains: valueDomainService.list(), conceptualDomainInstance: new ConceptualDomain(params)]
    }
	
	
	/* **************************************************************************************
	 * ************************************ SAVE ****************************************************
	 
	 * calls the collection service to create a conceptual domain . The service sets admin permissions
	 * on that object for the user in question and creates the conceptual domain
	 *************************************************************************************** */
	

    def save() {
		
		/* *****
		 * create the conceptual domain using the conceptual domain service
		 ******* */
		
		
		def conceptualDomainInstance = conceptualDomainService.create(params)
		
		/* ******
		 * check that the data element has been created without errors and render accordingly
		 ***** */
		
		if (!renderWithErrors('create', conceptualDomainInstance)) {
			redirectShow(message(code: 'default.created.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), conceptualDomainInstance.id]), conceptualDomainInstance.id)
		}

    }

	/* **************************************************************************************
	 * ************************************** EDIT ******************************************
	
	 * this function redirects to the edit conceptual domain screen
	 ************************************************************************************** */

    def edit(Long id) {
		
		//get the conceptual domain (redirect if you can't)
        def conceptualDomainInstance = findInstance()
        if (!conceptualDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), id])
            redirect(action: "list")
            return
        }

        [valueDomains: valueDomainService.list(), selectedValueDomains: conceptualDomainInstance.valueDomains , conceptualDomainInstance: conceptualDomainInstance]
    }

	
	/* **************************************************************************************
	 * ************************************ UPDATE ******************************************
	
	 * this function updates the conceptual domain using the conceptual domain service
	 *************************************************************************************** */
	
	
    def update(Long id, Long version) {
		
		//get the conceptual domain (redirect if you can't)

        def conceptualDomainInstance = findInstance()
        if (!conceptualDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), id])
            redirect(action: "list")
            return
        }
		
		//check that we have the right version i.e. no one else has updated the conceptual domain 
		//whilst we have been looking at it
		
        if (version != null) {
            if (conceptualDomainInstance.version > version) {
                conceptualDomainInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'conceptualDomain.label', default: 'ConceptualDomain')] as Object[],
                          "Another user has updated this ConceptualDomain while you were editing")
                render(view: "edit", model: [conceptualDomainInstance: conceptualDomainInstance])
                return
            }
        }	

		
		
		//update the conceptualDomain using the conceptualDomain service
		
		conceptualDomainService.update(conceptualDomainInstance, params)
		
		if (!renderWithErrors('edit', conceptualDomainInstance)) {
			redirectShow message(code: 'default.updated.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), conceptualDomainInstance.id]), conceptualDomainInstance.id
		}

    }

	
	/* **************************************************************************************
	 * ********************************* DELETE *************************************************
	
	 * this function deletes the conceptual domain using the conceptual domain service
	 *********************************************************************************** */
	
    def delete(Long id) {
		
		//get the conceptual domain and redirect if it doesn't exist
        def conceptualDomainInstance = findInstance()
        if (!conceptualDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), id])
            redirect(action: "list")
            return
        }
		
		//call the conceptual domain service  to delete the conceptual domain
        try {
			
			conceptualDomainService.delete(conceptualDomainInstance)
			
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), id])
            redirect(action: "show", id: id)
        }
    }
	
	/* **************************************************************************************
	 * ********************************* REMOVE VALUE DOMAINS *************************************************
	
	 * this function removes a value domain from a conceptualDomain
	 *********************************************************************************** */
	
	
	def removeEnumeratedValue(){
		ValueDomain valueDomain = valueDomainService.get(params.valueDomainId)
		ConceptualDomain conceptualDomain = conceptualDomainService.get(params.conceptualDomainId)
		if(valueDomain && conceptualDomain){
			conceptualDomainService.removeValueDomain(conceptualDomain,valueDomain)
		}
		
		redirect(action: 'edit', id: params.conceptualDomainId)

	}
	
	
	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	
	 * this function grant permission to the given conceptualDomain
	 *********************************************************************************** */
	
	
	def grant = {
		
				def conceptualDomain = findInstance()
				
				if (!conceptualDomain) return
		
				if (!request.post) {
					return [conceptualDomainInstance: conceptualDomain]
				}
		
				conceptualDomainService.addPermission(conceptualDomain, params.recipient, params.int('permission'))
		
				redirectShow "Permission $params.permission granted on Report $conceptualDomain.id " + "to $params.recipient", conceptualDomain.id
			}
	
	/* **********************************************************************************
	 * this function uses the conceptualDomain service to get the conceptualDomain so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */
	
	private ConceptualDomain findInstance() {
		def conceptualDomain = conceptualDomainService.get(params.long('id'))
		if (!conceptualDomain) {
			flash.message = "ConceptualDomain not found with id $params.id"
			redirect action: list
		}
		conceptualDomain
	}
	
	/* **********************************************************************************
	 * this function redirects to the show conceptualDomain screen
	 *********************************************************************************** */
	
	private void redirectShow(message, id) {
		flash.message = message
		//redirect with message
				
		redirect(action: "show", id: id)
	}
	
	/* **********************************************************************************
	 * this function checks to see if the conceptualDomain passed to it contains errors i.e. when a
	 * service returns the element. It either returns false (if no errors) or it redirects
	 * to the view specified by the caller
	 *********************************************************************************** */

	private boolean renderWithErrors(String view, ConceptualDomain conceptualDomain) {
		if (conceptualDomain.hasErrors()) {
			render view: view, model: [conceptualDomainInstance: conceptualDomain]
			return true
			
		}
		false
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
