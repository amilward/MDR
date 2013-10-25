package uk.co.mdc.forms

import org.springframework.dao.DataIntegrityViolationException
import uk.co.mdc.model.*
import grails.converters.*
import org.springframework.security.acls.model.Permission
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.web.json.*
@Secured(['ROLE_USER'])

class FormDesignController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def formDesignService
	def collectionService
	
	
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
	 * to list all the formDesigns
	 *************************************************************************************** */

    def list(Integer max) {
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
			
			def searchResults = formDesignService.search(params.sSearch)
			
			total = searchResults.size()
			displayTotal = searchResults.size()
			
			if(total>0){
				data = searchResults
			}else{
				data=[]
			}
			
			//otherwise list the data elements using the data elements service and pass the relevant data
			//back to the data tables plugin request as json
			
		}else{
		
			order = params?.sSortDir_0
			sortCol = params?.iSortCol_0
			sortCol = getSortField(sortCol)
			data = formDesignService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortCol, order: order)
			total = formDesignService.count()
			displayTotal = formDesignService.count()
			
		}
		
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]

		//NB. when the json is rendered it uses a custom json marshaller so that it includes the relevant
		//information (and doesn't return the whole database)
		//the corresponding json marshaller is stored in src/groovy/uk/co/mdc/model/xxxxxxMarshaller.groovy
				
		render model as JSON
	}
	
	
	/* **************************************************************************************
	 * *********************************** SHOW *****************************************************
	
	 * show the form design in question using the find instance function and the form design service
	 * ...presuming they have the appropriate permissions
	 *************************************************************************************** */
	
	def show(Long id) {
		
		//use the find instance method to get the form design in question
		
		def formDesignInstance = findInstance()
		
		//if you can't find it or don't have permission go back to the list page
		
		if (!formDesignInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
			redirect(action: "list")
			return
		}
		
		[formDesignInstance: formDesignInstance]
	}
	
	
	
	/* **************************************************************************************
	 * ************************************* CREATE ***************************************************
	 
	 * renders the form design  so the user can create a form design
	 * N.B additionally will only display objects that user has permission to read
	 *************************************************************************************** */
	
    def create() {
		
		//if the user has created the form design from a collection
		//create questions based on relevant data elements and value domains
		
		if(params?.createFromCollection=='true'){
		
			//get the collection using the id passed from collection show screen
			def collectionInstance = collectionService.get(params.collectionId.toInteger())
			
			def dataElements = collectionInstance.dataElementCollections()
			
			//set up the array to hold the information about questions
			def questions = []
			
			//define temporary variables that need to be stored in the questions array
			//whilst looping through data elements (below)
			ValueDomain valueDomain
			def valueDomains
			def label
			def unitOfMeasure
			def dataType
			def format
			def enumerated
			def options
			def renderType
			def description
			
			//loop through the data elements in the collection
			
			dataElements.each{ dataElement->
				
				//!!!!!!!!!!!!!!!!CHANGE THIS
				//N.B....need to change this so that the data elements in the collections can have more than one
				//value domain however, how we will look at adding this in the ui later
				// at present we can stick with the first value domain
				
				valueDomains = dataElement.dataElementValueDomains()
				valueDomain = valueDomains[0]
				label = dataElement?.name
				description = dataElement?.description
				unitOfMeasure = valueDomain?.unitOfMeasure
				dataType = valueDomain?.dataType
				format = valueDomain?.format
				
				//ensure that the options variable is null
				if(options!=null){
					options = null
				}
				
				
				//!!!!!!!!!!!!!!!!CHANGE THIS
				//!!!!!!!!!!!!!this needs to be improved there are more than two different data types
				//add select options if the data type is enumerated
				if(dataType?.enumerated){
					renderType = 'select'
					options = dataType.enumerations
				}else{
					renderType = 'text'
					options = null
				}

				println(label)
				println(dataElement?.id)
				println(valueDomain?.id)
				println(unitOfMeasure)
				println(dataType)
				println(dataType?.enumerated)
				println(format)
				println(renderType)
				println(description)
				println(options)
				
				
				//add the question information to the questions array
				questions.push(new HashMap(
							label: label,
							dataElementId: dataElement?.id, 
							valueDomainId: valueDomain?.id,
							unitOfMeasure: unitOfMeasure,
							dataType: dataType,
							isEnumerated: dataType?.enumerated,
							format: format,
							renderType: renderType,
							additionalInstructions: description,
							enumerations: options	
							))
			}
			
			//return the collection id and the questions needed to create a new form
			//based on the data elements in a collection
			
			[collectionId: collectionInstance.id, questions: questions as JSON]
			
		}else{
		
			//create a blank form
		
			def questions = []
		
			[collectionId: '', questions: questions as JSON]
		
		}
		
    }
	
	
	/* **************************************************************************************
	 * ************************************ SAVE ****************************************************
	 
	 * calls the data element service to create a dataElement and  sets admin permissions
	 * on that object for the user in question
	 *************************************************************************************** */
	

    def save() {
		
		def form = request.JSON
		
		def formDesignInstance = formDesignService.create(form)
	   
	   def model = [success: true, formDesignId: formDesignInstance.id]
	   
	   render model  as JSON
		
    }
	
	
	/* **************************************************************************************
	 * ************************************** PREVIEW ********************************************
	
	 * this function redirects to the edit form design screen
	 *********************************************************************************** */
	
	def preview(Long id) {
		//use the find instance method to get the form design in question
		
		def formDesignInstance = findInstance()
		
		//if you can't find it or don't have permission go back to the list page
		
		if (!formDesignInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
			redirect(action: "list")
			return
		}
		
		[formDesignInstance: formDesignInstance]
	}

	
	/* **************************************************************************************
	 * ************************************ UPDATE **********************************************
	
	 * this function updates the form using the form design service
	 *********************************************************************************** */
	

	def update(){
		
		 def form = request.JSON
		 
		 def components = form.components

		 def formDesignId = form.formDesignId
		 
		 def formDesignVersion = form.formVersionNo
		 
		 def formDesignInstance = findInstance(formDesignId.toInteger())
		 
		 if (!formDesignInstance) {
			 flash.message = message(code: 'default.not.found.message', args: [message(code: 'formDesignInstance.label', default: 'FormDesign'), id])
			 redirect(action: "list")
			 return
		 }
		 
		 
		 //check that we have the right version i.e. no one else has updated the form design whilst we have been
		 //looking at it
 
		 if (formDesignVersion != null) {
			 if (formDesignInstance.version > formDesignVersion) {
				 formDesignInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						   [message(code: 'formDesign.label', default: 'DataElement')] as Object[],
						   "Another user has updated this Form Design while you were editing")
				 def model = [success: true, formDesignId: formDesignInstance.id, message: 'version number conflict, please reload page and try again']
				 render model  as JSON
			}
		 }
		 		 
		formDesignInstance = formDesignService.update(formDesignInstance, form)
	
		def model = [success: true, formDesignId: formDesignInstance.id, formVersion: formDesignInstance.version, message: 'saved']
		
		render model  as JSON
	}
	

	
	/* **************************************************************************************
	 * ********************************* DELETE *************************************************
	
	 * this function deletes the data element using the data element service
	 *********************************************************************************** */

    def delete(Long id) {
        def formDesignInstance = findInstance(id)
		
        if (!formDesignInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
            redirect(action: "list")
            return
        }
		
		//call form design service to delete design

        try {
			formDesignService.delete(formDesignInstance)
           // formDesignInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
            redirect(action: "show", id: id)
        }
    }
	
	
	
	//forms get forms model
	
	def jsonFormsBuilder(Long id){
		
		def formDesignInstance = findInstance(id)
		
		//def questions = formDesignInstance.getQuestions()
		
		def model = [formDesign: formDesignInstance]
		
		//def model = [questions: questions]
		
		render model as JSON
		
	}
	
	//data tables column name for form design
	
	String getSortField(String column){
		
		def field
		
		switch(column){
			
			case '0':
				field = "refId"
			break
			
			case '1':
				field = "name"
			break
			
			case '2':
				field = "description"
			break
			
			default:
				field = "x"
			break
		}
		
		return field
		
	}
	
	
	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	
	 * this function grant permission to the given data element
	 *********************************************************************************** */
	
	
	def grant = {
		
				def formDesign = findInstance()
				
				if (!formDesign) return
		
				if (!request.post) {
					return [formDesignInstance: formDesign]
				}
		
				formDesignService.addPermission(formDesign, params.recipient, params.int('permission'))
		
				redirectShow "Permission $params.permission granted on Report $formDesign.id " + "to $params.recipient", formDesign.id
			}
	
	/* **********************************************************************************
	 * this function uses the formDesign service to get the form design so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */
	
	private FormDesign findInstance() {
		def formDesign = formDesignService.get(params?.long('id'))
		if (!formDesign) {
			flash.message = "FormDesign not found with id $params.id"
			redirect action: list
		}
		formDesign
	}
	
	//if no parameters sent
	private FormDesign findInstance(Long id) {
		def formDesign = formDesignService.get(id)
		if (!formDesign) {
			flash.message = "FormDesign not found with id $params.id"
			redirect action: list
		}
		formDesign
	}
	
	
	/* **********************************************************************************
	 * this function redirects to the show data element screen
	 *********************************************************************************** */
	
	private void redirectShow(message, id) {
		flash.message = message
		//redirect with message
				
		redirect(action: "show", id: id)
	}
	
	/* **********************************************************************************
	 * this function checks to see if the data element passed to it contains errors i.e. when a
	 * service returns the element. It either returns false (if no errors) or it redirects
	 * to the view specified by the caller
	 *********************************************************************************** */

	private boolean renderWithErrors(String view, FormDesign formDesign) {
		if (formDesign.hasErrors()) {
			render view: view, model: [formDesignInstance: formDesign]
			return true
			
		}
		false
	}
	
}




/*
 *
 * 
 * 
 * 	def preview(Long id) {
		
		def formDesignInstance = FormDesign.get(id)
        if (!formDesignInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
            redirect(action: "list")
            return
        }
		

        [formDesignInstance: formDesignInstance]
		
	}
	
 * 
 * def getFormDesignElements(formDesignId){
		
		def formDesignInstance = FormDesign.get(formDesignId)
		
		if(params?.questionLabels){
			
			def questions = []
			def options
			def counter = 0
			
			//if there is more than one enumeration
			if(params.questionLabels.class.isArray()){
				
				//iterate through values and insert them into map
				params?.questionLabels?.each{ questionLabel->
					
						def dataElement = DataElement.get(params?.questionDataElementIds[counter].toInteger())
						def valueDomain = ValueDomain.get(params?.questionValueDomainIds[counter].toInteger())
						def unitOfMeasure = params?.questionUnitOfMeasures[counter]
						def dataType = DataType.get(params?.questionDataTypes[counter].toInteger())
						def format = params?.questionFormats[counter]
						def renderType = params?.questionRenderTypes[counter]
						

						if(!params?.questionOptions[counter].isEmpty()){	
							options = new HashMap()			
							options.putAll(JSON.parse(params?.questionOptions[counter]))
						}else{
							options = null
						}
						def inputField = new InputField(
								unitOfMeasure: unitOfMeasure,
								dataType: dataType,
								format: format,
								renderType: renderType,
								options: options
								).save(flush:true, failOnError:true)
								
								
						questions.add(new QuestionElement(
							label: questionLabel,
							dataElement: dataElement, 
							valueDomain: valueDomain,
							inputField: inputField
							))
						
					counter++
				}
			
			}else{
			    def questionLabel = params?.questionLabels
				def dataElement = DataElement.get(params?.questionDataElementIds.toInteger())
				def valueDomain = ValueDomain.get(params?.questionValueDomainIds.toInteger())
				def unitOfMeasure = params?.unitOfMeasures
				def dataType = DataType.get(params?.questionDataTypes.toInteger())
				def format = params?.questionFormats
				def renderType = params?.questionRenderTypes
						
				if(!params?.questionOptions.isEmpty()){	
							options = new HashMap()			
							options.putAll(JSON.parse(params?.questionOptions))
				}else{
							options = null
						}
				
				questions.add(new QuestionElement(
							label: questionLabel,
							dataElement: dataElement, 
							valueDomain: valueDomain,
							inputField: new InputField(
								unitOfMeasure: unitOfMeasure,
								dataType: dataType,
								format: format,
								renderType: renderType,
								options: options
								).save(flush:true, failOnError:true)
							))
			}
			
			return questions
		}
	}
	
	def jsonPreviewFormDesign(Long id){
		
		def formDesignInstance = findInstance()
		
		def questions = formDesignInstance.getQuestions()
		
		def model = [action:"index.html", method:"get", html: questions]
		
		render model as JSON
		
	}
 * 
 * */

/*def update(Long id, Long version) {
 def formDesignInstance = FormDesign.get(id)
 if (!formDesignInstance) {
	 flash.message = message(code: 'default.not.found.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
	 redirect(action: "list")
	 return
 }

 if (version != null) {
	 if (formDesignInstance.version > version) {
		 formDesignInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
				   [message(code: 'formDesign.label', default: 'FormDesign')] as Object[],
				   "Another user has updated this FormDesign while you were editing")
		 render(view: "edit", model: [formDesignInstance: formDesignInstance])
		 return
	 }
 }

 formDesignInstance.properties = params

 if (!formDesignInstance.save(flush: true)) {
	 render(view: "edit", model: [formDesignInstance: formDesignInstance])
	 return
 }

 flash.message = message(code: 'default.updated.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), formDesignInstance.id])
 redirect(action: "show", id: formDesignInstance.id)
}*/
 