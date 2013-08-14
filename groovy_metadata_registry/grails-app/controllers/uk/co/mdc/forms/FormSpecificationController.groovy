package uk.co.mdc.forms

import org.springframework.dao.DataIntegrityViolationException
import uk.co.mdc.model.Collection
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.ValueDomain
import uk.co.mdc.model.DataType
import grails.converters.*
import org.codehaus.groovy.grails.web.json.*;

class FormSpecificationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [formSpecificationInstanceList: FormSpecification.list(params), formSpecificationInstanceTotal: FormSpecification.count()]
    }

    def create() {
		
		def collectionInstance = Collection.get(params.collectionId)
		def dataElements = collectionInstance.dataElementCollections()
		
		def Fields = []

		dataElements.each{ dataElement ->
			
			def options = new HashMap()
			def valueDomains
			def fieldType
			
			valueDomains =  dataElement.dataElementValueDomains()
			
			DataType dataType = valueDomains[0].dataType
			
			if(dataType.enumerated){
				fieldType = FieldType.select
				options.putAll(dataType.enumerations)
			}else{
				fieldType = FieldType.text
			}

			Fields.add([name: dataElement.name, type: fieldType, caption: dataElement.name, options: options])
		}

        [formSpecificationInstance: new FormSpecification(params), collectionId: collectionInstance.id, collectionName: collectionInstance.name, fields: Fields]
    }
	

    def save() {
		
		println(params)
		
		
		params.fields = getFields()
		
        def formSpecificationInstance = new FormSpecification(params)
        if (!formSpecificationInstance.save(flush: true)) {
            render(view: "create", model: [formSpecificationInstance: formSpecificationInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'formSpecification.label', default: 'FormSpecification'), formSpecificationInstance.id])
        redirect(action: "show", id: formSpecificationInstance.id)
    }

    def show(Long id) {
        def formSpecificationInstance = FormSpecification.get(id)
        if (!formSpecificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'formSpecification.label', default: 'FormSpecification'), id])
            redirect(action: "list")
            return
        }

        [formSpecificationInstance: formSpecificationInstance]
    }
	
	
	def jsonFormSpec(Long id){
		
		def formSpecificationInstance = FormSpecification.get(id)
		
		def model = [action:"index.html", method:"get", html: formSpecificationInstance?.fields]
		
		render model as JSON
		
	}

    def edit(Long id) {
        def formSpecificationInstance = FormSpecification.get(id)
        if (!formSpecificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'formSpecification.label', default: 'FormSpecification'), id])
            redirect(action: "list")
            return
        }

        [formSpecificationInstance: formSpecificationInstance]
    }

    def update(Long id, Long version) {
        def formSpecificationInstance = FormSpecification.get(id)
        if (!formSpecificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'formSpecification.label', default: 'FormSpecification'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (formSpecificationInstance.version > version) {
                formSpecificationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'formSpecification.label', default: 'FormSpecification')] as Object[],
                          "Another user has updated this FormSpecification while you were editing")
                render(view: "edit", model: [formSpecificationInstance: formSpecificationInstance])
                return
            }
        }

        formSpecificationInstance.properties = params

        if (!formSpecificationInstance.save(flush: true)) {
            render(view: "edit", model: [formSpecificationInstance: formSpecificationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'formSpecification.label', default: 'FormSpecification'), formSpecificationInstance.id])
        redirect(action: "show", id: formSpecificationInstance.id)
    }

    def delete(Long id) {
        def formSpecificationInstance = FormSpecification.get(id)
        if (!formSpecificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'formSpecification.label', default: 'FormSpecification'), id])
            redirect(action: "list")
            return
        }

        try {
            formSpecificationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'formSpecification.label', default: 'FormSpecification'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'formSpecification.label', default: 'FormSpecification'), id])
            redirect(action: "show", id: id)
        }
    }
	
	
	def getFields(){
					if(params?.fieldnames){
						
						def fields = []
						Map options = new HashMap()
						def counter = 0
						
						//if there is more than one enumeration
						if(params.fieldnames.class.isArray()){
							
							//iterate through values and insert them into map
							params?.fieldnames?.each{ fieldname->
								
									def type = params?.fieldtypes[counter]
									def caption = params?.fieldcaptions[counter]
									
									options.putAll(JSON.parse(params?.fieldoptions[counter]))
									
									fields.add(new Field(name: fieldname, caption: caption, type: type, options: options).save(flush:true, failOnError: true))
									
								counter++
							}
						
						}else{
							def fieldname = params?.fieldnames
							def type = params?.fieldtypes
							def caption = params?.fieldcaptions
							options.putAll(JSON.parse(params?.fieldoptions))
							fields.add(new Field(name: fieldname, caption: caption, type: type, options: options).save(flush:true, failOnError: true))
							
						}
				
						return fields
					}

			}
	
	
	
	
}
