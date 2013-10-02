package uk.co.mdc.forms

import org.springframework.dao.DataIntegrityViolationException

import uk.co.mdc.model.*
import grails.converters.*

import org.codehaus.groovy.grails.web.json.*;

class FormDesignController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [formDesignInstanceList: FormDesign.list(params), formDesignInstanceTotal: FormDesign.count()]
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
			
			dataElements.each{ dataElement->
				
				//N.B....need to change this so that the data elements in the collections can have more than one
				//value domain however, how we will look at adding this in the ui later
				valueDomains = dataElement.dataElementValueDomains()
				valueDomain = valueDomains[0]
				
				label = dataElement?.name
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
				questions.add([
					'dataElementId': dataElement.id,
					'valueDomainId': valueDomain.id,
					'label': label,
					'dataType': dataType,
					'format': format,
					'renderType': renderType,
					'options': options,
					 ])
			}
			
			[formDesignInstance: new FormDesign(params), collectionId: collectionInstance.id, questions: questions]
			
		}else{
		
			[formDesignInstance: new FormDesign(params)]
		
		}
		
    }

    def save() {
		
		def collection = null
		collection = Collection.get(params?.collection.id.toInteger())
		
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
			/*if(designElement instanceof uk.co.mdc.forms.QuestionElement){
				println(designElement.inputField)
				println(designElement.inputField.options)
			}*/
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
	
	
	def formsBuilder(Long id) {
		
		def formDesignInstance = FormDesign.get(id)
/*		if (!formDesignInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'formDesign.label', default: 'FormDesign'), id])
			redirect(action: "list")
			return
		}*/

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
}
