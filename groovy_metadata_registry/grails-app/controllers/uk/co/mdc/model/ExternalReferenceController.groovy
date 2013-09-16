package uk.co.mdc.model

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

class ExternalReferenceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def externalReferenceService
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
	 * to list all the external references
	 *************************************************************************************** */
	
    def list() {
        []
    }
	
	/* **************************************************************************************
	 * ********************************* DATA TABLES *************************************************
	
	 * this function is called when listing the external references. It is called through ajax
	 * using the the data tables plugin in the show.gsp view and the javascript that
	 * calls the code is in main.js
	 *********************************************************************************** */
	
	def dataTables(){
		
				// set the variables needed to pass back to the data tables plugin to render the external references
				def data
				def total
				def displayTotal
				def order
				def sortCol
				
				
				//if the user searches for a external reference return the search results using the external reference service
		
				if(params?.sSearch!='' && params?.sSearch!=null){
					
					def searchResults = externalReferenceService.search(params.sSearch)
					
					total = searchResults.size()
					displayTotal = searchResults.size()
					
					if(total>0){
						data = searchResults
					}else{
						data=[]
					}
					
					//otherwise list the external references using the external reference service and pass the relevant data
					//back to the data tables plugin request as json
					
				}else{
				
					order = params?.sSortDir_0
					sortCol = "name"
					
					data = externalReferenceService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortCol, order: order)
					total = externalReferenceService.count()
					displayTotal = externalReferenceService.count()
					
				}
				
				def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
				
				//NB. when the json is rendered it uses a custom json marshaller so that it includes the relevant
				//information (and doesn't return the WHOLE database due to all the links)
				//the corresponding json marshaller is stored in src/groovy/uk/co/mdc/model/xxxxxxMarshaller.groovy
				
				render model as JSON
	}
	
	/* **************************************************************************************
	 * *********************************** SHOW *****************************************************
	
	 * show the external reference in question using the find instance function and the external reference service
	 * ...presuming they have the appropriate permissions
	 *************************************************************************************** */
	
	def show(Long id) {
		
		//use the find instance method to get the external reference in question
		def externalReferenceInstance = findInstance()
		
		//if you can't find it or don't have permission go back to the list page otherwise show the external reference
		if (!externalReferenceInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), id])
			redirect(action: "list")
			return
		}

		[externalReferenceInstance: externalReferenceInstance]
	}

	/* **************************************************************************************
	 * ************************************* CREATE ***************************************************
	 
	 * renders the external reference template so the user can create an external reference
	 * N.B additionally will only display objects that user has permission to read
	 *************************************************************************************** */
	
    def create() {
        [externalReferenceInstance: new ExternalReference(params)]
    }
	
	/* **************************************************************************************
	 * ************************************ SAVE ****************************************************
	 
	 * calls the external reference service to create an external reference . The service sets admin permissions
	 * on that object for the user in question and creates the external reference
	 *************************************************************************************** */

    def save() {
		
		
		//gets the attributes from the parameters and puts them into a format that
		//can be consumed by the external reference service
		params.attributes = getAttributes()
		
		/* *****
		 * create the data type using the data type service
		 ******* */

		def externalReferenceInstance = externalReferenceService.create(params)
		
		
		/* ******
		 * check that the data element has been created without errors and render accordingly
		 ***** */
		
		if (!renderWithErrors('create', externalReferenceInstance)) {
			redirectShow(message(code: 'default.created.message', args: [message(code: 'externalReference.label', default: 'externalReference'), externalReferenceInstance.id]), externalReferenceInstance.id)
		}
    }

	/* **************************************************************************************
	 * ************************************** EDIT ******************************************
	
	 * this function redirects to the edit data type screen
	 ************************************************************************************** */
	

    def edit(Long id) {
		
		//get the external reference (redirect if you can't)
        def externalReferenceInstance = findInstance()
        if (!externalReferenceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), id])
            redirect(action: "list")
            return
        }

        [externalReferenceInstance: externalReferenceInstance]
    }
	
	
	/* **************************************************************************************
	 * ************************************ UPDATE ******************************************
	
	 * this function updates the external reference using the external reference service
	 *************************************************************************************** */

    def update(Long id, Long version) {
		
		//get the external reference (redirect if you can't)
        def externalReferenceInstance = findInstance()
        if (!externalReferenceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), id])
            redirect(action: "list")
            return
        }
		
		//check that we have the right version i.e. no one else has updated the exernal reference
		//whilst we have been looking at it

        if (version != null) {
            if (externalReferenceInstance.version > version) {
                externalReferenceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'externalReference.label', default: 'ExternalReference')] as Object[],
                          "Another user has updated this ExternalReference while you were editing")
                render(view: "edit", model: [externalReferenceInstance: externalReferenceInstance])
                return
            }
        }
		
		//gets the attributes from the parameters and puts them into a format that
		//can be consumed by the external reference service
		
		params.attributes = getAttributes()
		
		if(params.attributes==null){
			params.attributes= new HashMap()
		}

        externalReferenceInstance.properties = params

        //update the external reference using the external reference service
		
		externalReferenceService.update(externalReferenceInstance, params)
		
		if (!renderWithErrors('edit', externalReferenceInstance)) {
			redirectShow message(code: 'default.updated.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), externalReferenceInstance.id]), externalReferenceInstance.id
		}
    }
	
	/* **************************************************************************************
	 * ********************************* DELETE *************************************************
	
	 * this function deletes the external reference  using the external reference service
	 *********************************************************************************** */

    def delete(Long id) {
		
		//get the external reference and redirect if it doesn't exist
        def externalReferenceInstance = findInstance()
        if (!externalReferenceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), id])
            redirect(action: "list")
            return
        }

		//call the external reference service  to delete the external reference
        try {
			externalReferenceService.delete(externalReferenceInstance)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), id])
            redirect(action: "show", id: id)
        }
    }
	
	/* **************************************************************************************
	 * ********************************* REMOVE ATTRIBUTES  *************************************************
	
	 * this function removes an attributes from a external reference
	 *********************************************************************************** */
	
	def removeAttribute(){
		
		ExternalReference externalReferenceInstance = externalReferenceService.get(params.externalReferenceId.toInteger())
		
		if(externalReferenceInstance){
			externalReferenceService.removeAttribute(externalReferenceInstance, params.attribute)
		}
		
		redirect(action: 'edit', id: params.externalReferenceId.toInteger())

	}
	
	
	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	
	 * this function grant permission to the given externalReference
	 *********************************************************************************** */
	
	
	def grant = {
		
				def externalReference = findInstance()
				
				if (!externalReference) return
		
				if (!request.post) {
					return [externalReferenceInstance: externalReference]
				}
		
				externalReferenceService.addPermission(externalReference, params.recipient, params.int('permission'))
		
				redirectShow "Permission $params.permission granted on Report $externalReference.id " + "to $params.recipient", externalReference.id
			}
	
	/* **********************************************************************************
	 * this function uses the externalReference service to get the externalReference so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */
	
	private ExternalReference findInstance() {
		def externalReference = externalReferenceService.get(params.long('id'))
		if (!externalReference) {
			flash.message = "ExternalReference not found with id $params.id"
			redirect action: list
		}
		externalReference
	}
	
	/* **********************************************************************************
	 * this function redirects to the show externalReference screen
	 *********************************************************************************** */
	
	private void redirectShow(message, id) {
		flash.message = message
		//redirect with message
				
		redirect(action: "show", id: id)
	}
	
	/* **********************************************************************************
	 * this function checks to see if the externalReference passed to it contains errors i.e. when a
	 * service returns the element. It either returns false (if no errors) or it redirects
	 * to the view specified by the caller
	 *********************************************************************************** */

	private boolean renderWithErrors(String view, ExternalReference externalReference) {
		if (externalReference.hasErrors()) {
			render view: view, model: [externalReferenceInstance: externalReference]
			return true
			
		}
		false
	}
	

	def getAttributes(){
		
			if(params?.map_key){
				
				Map attributes = new HashMap()
				def counter = 0
				
				//if there is more than one enumeration
				if(params.map_key.class.isArray()){
					
					//iterate through values and insert them into map
					params?.map_key?.each{ val->
						
						
						if(val!=''){
							
							def desc = ''
							desc = params?.map_value[counter]
							
							attributes.put(val, desc)
							
						}
						
						counter++
					}
				
				}else{
					
					attributes.put(params.map_key, params?.map_value)
					
				}
		
				return attributes
			}else{
			
				String empty = ''
				return empty
			
			}

	}

}
