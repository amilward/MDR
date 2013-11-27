package uk.co.mdc.model

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

class DataTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	
	def dataTypeService
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
	 * to list all the data types
	 *************************************************************************************** */
	
    def list() {
        []
    }

	/* **************************************************************************************
	 * ********************************* DATA TABLES *************************************************
	
	 * this function is called when listing the data types. It is called through ajax
	 * using the the data tables plugin in the show.gsp view and the javascript that
	 * calls the code is in main.js
	 *********************************************************************************** */
		
	def dataTables(){
		
		// set the variables needed to pass back to the data tables plugin to render the data types
		def data
		def total
		def displayTotal
		def order
		def sortCol

		//if the user searches for a data type return the search results using the data type service
		
		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = dataTypeService.search(params.sSearch)
			
			total = searchResults.size()	
			displayTotal = searchResults.size()
			
			if(total>0){
				data = searchResults
			}else{
				data=[]
			}
			
			//otherwise list the data types using the data type service and pass the relevant data
			//back to the data tables plugin request as json
			
		}else{
		
			order = params?.sSortDir_0
			sortCol = "name"
			
			data = dataTypeService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortCol, order: order)
			total = dataTypeService.count()
			displayTotal = dataTypeService.count()
			
		}

		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
		
		//NB. when the json is rendered it uses a custom json marshaller so that it includes the relevant
		//information (and doesn't return the WHOLE database due to all the links)
		//the corresponding json marshaller is stored in src/groovy/uk/co/mdc/model/xxxxxxMarshaller.groovy
		
		render model as JSON
	}
	
	
	/* **************************************************************************************
	 * *********************************** SHOW *****************************************************
	
	 * show the conceptual domain in question using the find instance function and the dataType service
	 * ...presuming they have the appropriate permissions
	 *************************************************************************************** */
	
	def show(Long id) {
		
		//use the find instance method to get the dataType in question
		def dataTypeInstance = findInstance()
		
		//if you can't find it or don't have permission go back to the list page otherwise show the data type
		if (!dataTypeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataType.label', default: 'DataType'), id])
			redirect(action: "list")
			return
		}

		[dataTypeInstance: dataTypeInstance]
	}
	
	/* **************************************************************************************
	 * ************************************* CREATE ***************************************************
	 
	 * renders the data type template so the user can create a data type
	 * N.B additionally will only display objects that user has permission to read
	 *************************************************************************************** */

    def create() {
        [dataTypeInstance: new DataType(params)]
    }
	
	/* **************************************************************************************
	 * ************************************ SAVE ****************************************************
	 
	 * calls the data type service to create a data type . The service sets admin permissions
	 * on that object for the user in question and creates the data type
	 *************************************************************************************** */

    def save() {
		
		//gets the enumerations from the parameters and puts them into a format that 
		//can be consumed by the data type service
		
		params.enumerations = getEnumerations()
		
		/* *****
		 * create the data type using the data type service
		 ******* */

		def dataTypeInstance = dataTypeService.create(params)
		
		
        /* ******
		 * check that the data element has been created without errors and render accordingly
		 ***** */
		
		if (!renderWithErrors('create', dataTypeInstance)) {
			redirectShow(message(code: 'default.created.message', args: [message(code: 'dataType.label', default: 'dataType'), dataTypeInstance.id]), dataTypeInstance.id)
		}
    }
	
	
	/* **************************************************************************************
	 * ************************************** EDIT ******************************************
	
	 * this function redirects to the edit data type screen
	 ************************************************************************************** */

    def edit(Long id) {
		
		//get the data type (redirect if you can't)
        def dataTypeInstance = findInstance()
        if (!dataTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataType.label', default: 'DataType'), id])
            redirect(action: "list")
            return
        }

        [dataTypeInstance: dataTypeInstance]
    }
	
	/* **************************************************************************************
	 * ************************************ UPDATE ******************************************
	
	 * this function updates the data type using the data type service
	 *************************************************************************************** */
	
    def update(Long id, Long version) {
		
		//get the data type (redirect if you can't)
		
        def dataTypeInstance = findInstance()
        if (!dataTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataType.label', default: 'DataType'), id])
            redirect(action: "list")
            return
        }
		
		//check that we have the right version i.e. no one else has updated the data type
		//whilst we have been looking at it

        if (version != null) {
            if (dataTypeInstance.version > version) {
                dataTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'dataType.label', default: 'DataType')] as Object[],
                          "Another user has updated this DataType while you were editing")
                render(view: "edit", model: [dataTypeInstance: dataTypeInstance])
                return
            }
        }
		
		//gets the enumerations from the parameters and puts them into a format that
		//can be consumed by the data type service
		
		params.enumerations = getEnumerations()
		
		if(params.enumerations==null){
			params.enumerations= new HashMap()
		}
		
		//update the data type using the data type service
		
		dataTypeService.update(dataTypeInstance, params)
		
		if (!renderWithErrors('update', dataTypeInstance)) {
			redirectShow message(code: 'default.updated.message', args: [message(code: 'dataType.label', default: 'DataType'), dataTypeInstance.id]), dataTypeInstance.id
		}
    }
	
	/* **************************************************************************************
	 * ********************************* DELETE *************************************************
	
	 * this function deletes the data type using the data type service
	 *********************************************************************************** */
	
    def delete(Long id) {
		
		//get the data type and redirect if it doesn't exist
        def dataTypeInstance = findInstance()
        if (!dataTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataType.label', default: 'DataType'), id])
            redirect(action: "list")
            return
        }
		
		//call the data type service  to delete the data type
        try {
            dataTypeService.delete(dataTypeInstance)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'dataType.label', default: 'DataType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dataType.label', default: 'DataType'), id])
            redirect(action: "show", id: id)
        }
    }
	
	/* **************************************************************************************
	 * ********************************* REMOVE ENUMERATED VALUES *************************************************
	
	 * this function removes an enumerated value from a data type
	 *********************************************************************************** */
	
	def removeEnumeratedValue(){
		
		DataType dataTypeInstance = dataTypeService.get(params.dataTypeId.toInteger())
		
		dataTypeService.removeEnumeratedValue(dataTypeInstance, params.enumeratedValue)
		
		redirect(action: 'edit', id: params.dataTypeId.toInteger())

	}
	
	
	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	
	 * this function grant permission to the given dataType
	 *********************************************************************************** */
	
	
	def grant = {
		
				def dataType = findInstance()
				
				if (!dataType) return
		
				if (!request.post) {
					return [dataTypeInstance: dataType]
				}
		
				dataTypeService.addPermission(dataType, params.recipient, params.int('permission'))
		
				redirectShow "Permission $params.permission granted on Report $dataType.id " + "to $params.recipient", dataType.id
			}
	
	/* **********************************************************************************
	 * this function uses the dataType service to get the dataType so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */
	
	private DataType findInstance() {
		def dataType = dataTypeService.get(params.long('id'))
		if (!dataType) {
			flash.message = "DataType not found with id $params.id"
			redirect action: list
		}
		dataType
	}
	
	/* **********************************************************************************
	 * this function redirects to the show dataType screen
	 *********************************************************************************** */
	
	private void redirectShow(message, id) {
		flash.message = message
		//redirect with message
				
		redirect(action: "show", id: id)
	}
	
	/* **********************************************************************************
	 * this function checks to see if the dataType passed to it contains errors i.e. when a
	 * service returns the element. It either returns false (if no errors) or it redirects
	 * to the view specified by the caller
	 *********************************************************************************** */

	private boolean renderWithErrors(String view, DataType dataType) {
		if (dataType.hasErrors()) {
			render view: view, model: [dataTypeInstance: dataType]
			return true
			
		}
		false
	}

	
	/*
	 * this function gets the enumerated values from interface and puts them into a format that can
	 * be passed to the data types service
	 * 
	 * */
	
	def getEnumerations(){

		if(params?.enumerated=='on'){
		
			if(params?.map_key){
				
				Map enumerations = new HashMap()
				def counter = 0
				
				//if there is more than one enumeration
				if(params.map_key.class.isArray()){
					
					//iterate through values and insert them into map
					params?.map_key?.each{ val->
						
						
						if(val!=''){
							
							def desc = ''
							desc = params?.map_value[counter]
							
							enumerations.put(val, desc)
							
						}
						
						counter++
					}
				
				}else{

					enumerations.put(params.map_key, params?.map_value)
					
				}
		
				return enumerations
			}
		}else{
			String empty = ''
			return empty
		}
		
		
	}	
	
}
