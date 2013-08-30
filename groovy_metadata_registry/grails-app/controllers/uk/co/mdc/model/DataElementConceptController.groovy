package uk.co.mdc.model

import grails.converters.JSON

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException

class DataElementConceptController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [dataElementConceptInstanceList: DataElementConcept.list(params), dataElementConceptInstanceTotal: DataElementConcept.count()]
    }
	
	def dataTables(){
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		def sortColName
		

		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = DataElementConcept.search(params.sSearch, [max:params.iDisplayLength])
			
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
			
			data = DataElementConcept.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortColName, order: order)
			total = DataElementConcept.count()
			displayTotal = DataElementConcept.count()
			
		}
		
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
				
		render model as JSON
	}
	
	

    def create() {
        [dataElementConcepts: DataElementConcept.list(), dataElements: DataElement.list(), dataElementConceptInstance: new DataElementConcept(params)]
    }

    def save() {
		
		Boolean valid = validateConcept()
		
		if(!valid){
			render(view: "edit", model: [dataElementConcepts: DataElementConcept.list(), dataElementConceptInstance: new DataElementConcept(params)])
			return
		}
		
		
        def dataElementConceptInstance = new DataElementConcept(params)
        if (!dataElementConceptInstance.save(flush: true)) {
            render(view: "create", model: [dataElementConceptInstance: dataElementConceptInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), dataElementConceptInstance.id])
        redirect(action: "show", id: dataElementConceptInstance.id)
    }

    def show(Long id) {
        def dataElementConceptInstance = DataElementConcept.get(id)
        if (!dataElementConceptInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), id])
            redirect(action: "list")
            return
        }

        [dataElementConceptInstance: dataElementConceptInstance]
    }

    def edit(Long id) {
        def dataElementConceptInstance = DataElementConcept.get(id)
        if (!dataElementConceptInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), id])
            redirect(action: "list")
            return
        }

        [dataElementConcepts: DataElementConcept.list(), dataElements: DataElement.list(), dataElementConceptInstance: dataElementConceptInstance]
    }

    def update(Long id, Long version) {
		
		
		//validate the update a params
		
		Boolean valid = validateConcept()
		
		if(!valid){
			redirect(action: "edit", id: params.id)
			return
		}
		
		
        def dataElementConceptInstance = DataElementConcept.get(id)
        if (!dataElementConceptInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (dataElementConceptInstance.version > version) {
                dataElementConceptInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'dataElementConcept.label', default: 'DataElementConcept')] as Object[],
                          "Another user has updated this DataElementConcept while you were editing")
                render(view: "edit", model: [dataElementConceptInstance: dataElementConceptInstance])
                return
            }
        }

		//remove data elements (if needed)
		
		unLinkDataElements(dataElementConceptInstance)
		
		//remove sub concepts (if needed)
		
		unLinkSubConcepts(dataElementConceptInstance)
		
        dataElementConceptInstance.properties = params

        if (!dataElementConceptInstance.save(flush: true)) {
            render(view: "edit", model: [dataElementConceptInstance: dataElementConceptInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), dataElementConceptInstance.id])
        redirect(action: "show", id: dataElementConceptInstance.id)
    }

    def delete(Long id) {
        def dataElementConceptInstance = DataElementConcept.get(id)
        if (!dataElementConceptInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), id])
            redirect(action: "list")
            return
        }
		
        try {
			dataElementConceptInstance.prepareForDelete()
            dataElementConceptInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dataElementConcept.label', default: 'DataElementConcept'), id])
            redirect(action: "show", id: id)
        }
    }
	
	
	
	def removeSubConcept(){
		DataElementConcept concept = DataElementConcept.get(params.conceptId)
		DataElementConcept subConcept = DataElementConcept.get(params.subConceptId)
		
		if(concept && subConcept){
			concept.removeFromSubConcepts(subConcept)
		}
		
		redirect(action: 'edit', id: params.conceptId)
	}
	
	def removeDataElement(){
		
		DataElement dataElement = DataElement.get(params.dataElementId)
		DataElementConcept dataElementConcept = DataElementConcept.get(params.conceptId)
		
		if(dataElement && dataElementConcept){
			dataElementConcept.removeFromDataElements(dataElement)
		}
		
		redirect(action: 'edit', id: params.conceptId)
	}
	
	
	
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
	
	
	
	Boolean ChildParentValid(String parent, ArrayList children){
		
		if(children.contains(parent)){
			return false
		}
		
		return true
	}
	
	
	List getChildren(){
		
		List children = new ArrayList()
		
		if(params.subConcepts.class.isArray()){
			children.addAll(params.subConcepts)
		}else{
			children.add(params.subConcepts.value.toString())
		}
		
		return children
		
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
				field = "parent"
			break
			
			default:
				field = "name"
			break
		}
		
		return field
		
	}
	
	
	
	def unLinkDataElements(dataElementConceptInstance){
		
			//if all data elements need to be removed or only a few elements need to be removed
			
			if(params?.dataElements==null && dataElementConceptInstance?.dataElements.size()>0){
				
				def dataElements = []
				dataElements += dataElementConceptInstance?.dataElements
				
				dataElements.each{ dataElement->
					dataElementConceptInstance.removeFromDataElements(dataElement)
				}
				
	
			}else if(params.dataElements){
		
				if(params?.dataElements.size() < dataElementConceptInstance?.dataElements.size()){
			
				def dataElements = []
				
				dataElements += dataElementConceptInstance?.dataElements
				
				dataElements.each{ dataElement->
					
	
					if(params?.dataElements instanceof String){
						
							if(params?.dataElements!=dataElement){
						
								dataElementConceptInstance.removeFromDataElements(dataElement)
							
							}
						
						}else{
							
							if(!params?.dataElements.contains(dataElement)){
								
								dataElementConceptInstance.removeFromDataElements(dataElement)
								
							}
						
						}
					}
			}
			}
	}
	
	
	def unLinkSubConcepts(subConceptConceptInstance){
		
			//if all data elements need to be removed or only a few elements need to be removed
			
			if(params?.subConcepts==null && subConceptConceptInstance?.subConcepts.size()>0){
				
				def subConcepts = []
				subConcepts += subConceptConceptInstance?.subConcepts
				
				subConcepts.each{ subConcept->
					subConceptConceptInstance.removeFromSubConcepts(subConcept)
				}
				
	
			}else if(params.subConcepts){
		
				if(params?.subConcepts.size() < subConceptConceptInstance?.subConcepts.size()){
			
				def subConcepts = []
				
				subConcepts += subConceptConceptInstance?.subConcepts
				
				subConcepts.each{ subConcept->
					
	
					if(params?.subConcepts instanceof String){
						
							if(params?.subConcepts!=subConcept){
						
								subConceptConceptInstance.removeFromSubConcepts(subConcept)
							
							}
						
						}else{
							
							if(!params?.subConcepts.contains(subConcept)){
								
								subConceptConceptInstance.removeFromSubConcepts(subConcept)
								
							}
						
						}
					}
			}
			}
	}
	
	
	
}
