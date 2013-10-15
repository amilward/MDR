package uk.co.mdc.forms

import org.springframework.dao.DataIntegrityViolationException

import uk.co.mdc.model.*
import grails.converters.*

import org.codehaus.groovy.grails.web.json.*;

class FormDesignController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def formDesignService
	
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
	
	
	
	
	
    def create() {
		
		if(params?.createFromCollection=='true'){
		
			//get the collection fusing the id passed from collection show screen
			def collectionInstance = Collection.get(params.collectionId.toInteger())
			
			def dataElements = collectionInstance.dataElementCollections()
			
			//set up the array to hold the information about questions
			def questions = []
			
			//define temporary variables that need to be stored in the questions array
			//whilst looping through data elements (below)
			def valueDomains
			ValueDomain valueDomain
			def label
			def unitOfMeasure
			def dataType
			def format
			def enumerated
			def options
			def renderType
			def description
			
			
			dataElements.each{ dataElement->
				
				//N.B....need to change this so that the data elements in the collections can have more than one
				//value domain however, how we will look at adding this in the ui later
				valueDomains = dataElement.dataElementValueDomains()
				valueDomain = valueDomains[0]
				
				label = dataElement?.name
				description = dataElement?.description
				unitOfMeasure = valueDomain?.unitOfMeasure
				dataType = valueDomain?.dataType
				format = valueDomain?.format
				
				if(options!=null){
					options = null
				}
				
				//add select options if the data type is enumerated
				if(dataType?.enumerated){
					renderType = 'select'
					options = dataType.enumerations
				}else{
					renderType = 'text'
					options = null
				}
				
				//add the question data to the list
				/*questions.add([
					'dataElementId': dataElement.id,
					'valueDomainId': valueDomain.id,
					'label': label,
					'dataType': dataType,
					'format': format,
					'renderType': renderType,
					'options': options,
					 ])*/
				
				questions.push(new HashMap(
							label: label,
							dataElementId: dataElement.id, 
							valueDomainId: valueDomain.id,
							unitOfMeasure: unitOfMeasure,
							dataType: dataType,
							isEnumerated: dataType.enumerated,
							format: format,
							renderType: renderType,
							additionalInstructions: description,
							options: options	
							)
				)
			}
			
			[collectionId: collectionInstance.id, questions: questions as JSON]
			
		}else{
		
			[formDesignInstance: new FormDesign(params)]
		
		}
		
    }

    def save() {
		
		def collection = null
		collection = Collection.get(params?.formCollectionId.toInteger())
		
		def formDesignInstance = new FormDesign(
			collection: collection,
			refId: params?.refId,
			name: params?.name,
			description: params?.description
			)
		
		if (!formDesignInstance.save(flush: true)) {
			render(view: "create", model: [formDesignInstance: formDesignInstance])
			return
		}
		

		
		def formDesignElements = getFormDesignElements(formDesignInstance.id)
		
		def header = new SectionElement(
			label: params?.header.label,
			title: params?.header.title,
			style: params?.header.style,
			preText: params?.header.preText
			)
		
		formDesignInstance.addToFormDesignElements(header)
		formDesignInstance.header = header
		formDesignElements.each{ designElement ->
			formDesignInstance.addToFormDesignElements(designElement)
		}
		

        flash.message = message(code: 'default.created.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), formDesignInstance.id])
        redirect(action: "show", id: formDesignInstance.id)
    }
	
	def getFormDesignElements(formDesignId){
		
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
		
		def formDesignInstance = FormDesign.get(id)
		
		def questions = formDesignInstance.getQuestions()
		
		def model = [action:"index.html", method:"get", html: questions]
		
		render model as JSON
		
	}
	
	
	def saveForm(){
		
		 def form = request.JSON
		 def components = form.components
		 
		 def formDesignInstance = formDesignService.create(form)
		

		def model = [success: true, formDesignId: formDesignInstance.id]
		
		render model  as JSON
	}

	
	def updateForm(){
		
		 def form = request.JSON
		 def components = form.components

		 def formDesignId = form.formDesignId
		 
		 def formDesignInstance = findInstance(formDesignId.toInteger())
		 
		 if (!formDesignInstance) {
			 flash.message = message(code: 'default.not.found.message', args: [message(code: 'formDesignInstance.label', default: 'FormDesign'), id])
			 redirect(action: "list")
			 return
		 }
		 		 
		 formDesignInstance = formDesignService.update(formDesignInstance, form)

		def model = [success: true, formDesignId: formDesignInstance.id]
		
		render model  as JSON
	}
	
	
	
	def jsonFormsBuilder(Long id){
		
		def formDesignInstance = FormDesign.get(id)
		
		def questions = formDesignInstance.getQuestions()
		
		def model = [questions: questions]
		
		render model as JSON
		
	}
	
	
	def preview(Long id) {
		
		def formDesignInstance = FormDesign.get(id)
        if (!formDesignInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
            redirect(action: "list")
            return
        }
		

        [formDesignInstance: formDesignInstance]
		
	}
	
    def show(Long id) {
        def formDesignInstance = FormDesign.get(id)
        if (!formDesignInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
            redirect(action: "list")
            return
        }
		
        [formDesignInstance: formDesignInstance]
    }

    def edit(Long id) {
        def formDesignInstance = FormDesign.get(id)
        if (!formDesignInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
            redirect(action: "list")
            return
        }

        [formDesignInstance: formDesignInstance]
    }

    def update(Long id, Long version) {
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
    }

    def delete(Long id) {
        def formDesignInstance = FormDesign.get(id)
        if (!formDesignInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
            redirect(action: "list")
            return
        }

        try {
            formDesignInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def getDataType(){
		
		
	}
	
	
	
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
	 * this function uses the formDesign service to get the data element so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */
	
	private FormDesign findInstance() {
		def formDesign = formDesignService.get(params.long('id'))
		if (!formDesign) {
			flash.message = "FormDesign not found with id $params.id"
			redirect action: list
		}
		formDesign
	}
	
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
