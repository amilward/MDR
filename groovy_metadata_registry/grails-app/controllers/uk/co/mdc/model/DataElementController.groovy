package uk.co.mdc.model

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import org.codehaus.groovy.grails.plugins.searchable.*
import org.springframework.security.acls.model.Permission
import grails.plugins.springsecurity.Secured

class DataElementController {

    static allowedMethods = [listJSON: "GET",save: "POST", update: "POST", delete: "POST"]
	
	def dataElementService
	
	/*
	 * default redirect to list dataElement page 
	 * */
	
	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		[dataElementInstanceList: DataElement.list(params), dataElementInstanceTotal: DataElement.count()]
	}
	
	def create() {
		[valueDomains: ValueDomain.list(), dataElements: DataElement.list(), externalSynonyms: ExternalSynonym.list(), dataElementInstance: new DataElement(params)]
	}

	def save() {
		
		//validate the parent child relationship
		
		Boolean valid = validateDataElement()
		
		if(!valid){
			render(view: "create", model: [valueDomains: ValueDomain.list(), dataElements: DataElement.list(), dataElementInstance: new DataElement(params)])
			return
		}
	   
		def dataElement = dataElementService.create(params)
		
		if (!renderWithErrors('create', dataElement)) {
			redirectShow "DataElement $dataElement.id created", dataElement.id
		}
		
	}
	
	private void redirectShow(message, id) {
		flash.message = message
		
		redirect action: show, id: id
	}

	private boolean renderWithErrors(String view, DataElement dataElement) {
		if (dataElement.hasErrors()) {
			render view: view, model: [dataElementInstance: dataElement]
			return true
		}
		false
	}
	
	
	
	
	/*OLD CODE FROM HERE ON*/

    
	
	def dataTables(){
		
		def data 
		def total
		def displayTotal
		def order
		def sortCol
		

		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = DataElement.search(params.sSearch, [max:params.iDisplayLength])
			
			total = searchResults.total
			displayTotal = searchResults.total
			
			if(total>0){				
				data = searchResults.results
			}else{
				data=[]
			}
			
			
			
		}else{
		
			order = params?.sSortDir_0
			sortCol = getSortField(params?.iSortCol_0.toInteger())
			
			data = DataElement.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortCol, order: order)
			total = DataElement.count()
			displayTotal = DataElement.count()
			
		}
		
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
				
		render model as JSON
	}

    

    def show(Long id) {
        def dataElementInstance = DataElement.get(id)
        if (!dataElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "list")
            return
        }

        [dataElementInstance: dataElementInstance]
    }

    def edit(Long id) {
        def dataElementInstance = DataElement.get(id)
        if (!dataElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "list")
            return
        }

        [valueDomains: ValueDomain.list(), selectedValueDomains: dataElementInstance.dataElementValueDomains() , dataElements: DataElement.list(), externalSynonyms: ExternalSynonym.list(), dataElementInstance: dataElementInstance]
    }

    def update(Long id, Long version) {
		
		//validate the params i.e. the parent isn't a subelement etc.
		
		Boolean valid = validateDataElement()
		
		if(!valid){
			redirect(action: "edit", id: params.id)
			return
		}
		
		//save the updates
		
        def dataElementInstance = DataElement.get(id)
		
        if (!dataElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (dataElementInstance.version > version) {
                dataElementInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'dataElement.label', default: 'DataElement')] as Object[],
                          "Another user has updated this DataElement while you were editing")
                render(view: "edit", model: [dataElementInstance: dataElementInstance,valueDomains: ValueDomain.list(), dataElements: DataElement.list()])
                return
            }
        }

		// remove subelements
		
		unLinkSubElements(dataElementInstance)
		
		//remove external synonyms
		
		unLinkExternalSynonyms(dataElementInstance)
		
		//add remove synonyms
		linkSynonyms(dataElementInstance)

        dataElementInstance.properties = params
		

        if (!dataElementInstance.save(flush: true)) {
            render(view: "edit", model: [dataElementInstance: dataElementInstance],valueDomains: ValueDomain.list(), dataElements: DataElement.list())
            return
        }
		
		// add/remove value domains
		linkValueDomains(dataElementInstance)	
		
		
        flash.message = message(code: 'default.updated.message', args: [message(code: 'dataElement.label', default: 'DataElement'), dataElementInstance.id])
        redirect(action: "show", id: dataElementInstance.id)
    }

    def delete(Long id) {
        def dataElementInstance = DataElement.get(id)
        if (!dataElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "list")
            return
        }

        try {
			dataElementInstance.prepareForDelete()
            dataElementInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "show", id: id)
        }
    }
	
	
	def removeValueDomain() {
		ValueDomain valueDomain = ValueDomain.get(params.valueDomainId)
		DataElement dataElement = DataElement.get(params.dataElementId)
		if(valueDomain && dataElement){
			dataElement.removeFromDataElementValueDomains(valueDomain)
		}
		redirect(action: 'edit', id: params.dataElementId)
	}
	
	
	def removeSubElement(){
		DataElement element = DataElement.get(params.elementId)
		DataElement subElement = DataElement.get(params.subElementId)
		
		if(element && subElement){
			element.removeFromSubElements(subElement)
		}
		
		redirect(action: 'edit', id: params.elementId)
	}
	
	def removeSynonym(){
		DataElement element = DataElement.get(params.elementId)
		ExternalSynonym externalSynonym = ExternalSynonym.get(params.synonymId)
		
		if(element && externalSynonym){
			element.removeFromExternalSynonyms(externalSynonym)
		}
		
		redirect(action: 'edit', id: params.elementId)
	}
	
	
	def unLinkSubElements(dataElementInstance){
		
		//if all data elements need to be removed or only a few elements need to be removed
		
			if(params?.subElements==null && dataElementInstance?.subElements.size()>0){
				
				def subElements = []
				subElements += dataElementInstance?.subElements
				
				subElements.each{ subElement->
					dataElementInstance.removeFromSubElements(subElement)
				}
				
	
			}else if(params?.subElements){
		
			if(params?.subElements.size() < dataElementInstance?.subElements.size()){
			
				def subElements = []
				
				subElements += dataElementInstance?.subElements
				
				subElements.each{ subElement->
					
	
					if(params?.subElements instanceof String){
						
							if(params?.subElements!=subElement){
						
								dataElementInstance.removeFromSubElements(subElement)
							
							}
						
						}else{
							
							if(!params?.subElements.contains(subElement)){
								
								dataElementInstance.removeFromSubElements(subElement)
								
							}
						
						}
					}
			}
			
		}
	}
	
	
	def unLinkExternalSynonyms(dataElementInstance){
		
			//if all data elements need to be removed or only a few elements need to be removed
			
			if(params?.externalSynonyms==null && dataElementInstance?.externalSynonyms.size()>0){
				
				def externalSynonyms = []
				externalSynonyms += dataElementInstance?.externalSynonyms
				
				externalSynonyms.each{ externalSynonym->
					dataElementInstance.removeFromExternalSynonyms(externalSynonym)
				}
				
	
			}else if(params.externalSynonyms){
		
				if(params?.externalSynonyms.size() < dataElementInstance?.externalSynonyms.size()){
			
				def externalSynonyms = []
				
				externalSynonyms += dataElementInstance?.externalSynonyms
				
				externalSynonyms.each{ externalSynonym->
					
	
					if(params?.externalSynonyms instanceof String){
						
							if(params?.externalSynonyms!=externalSynonym){
						
								dataElementInstance.removeFromExternalSynonyms(externalSynonym)
							
							}
						
						}else{
							
							if(!params?.externalSynonyms.contains(externalSynonym)){
								
								dataElementInstance.removeFromExternalSynonyms(externalSynonym)
								
							}
						
						}
					}
			}
			}	
	}	
	
	
	
	def linkSynonyms(dataElementInstance){
		
		def associatedSynonyms = dataElementInstance.synonyms()
		def synonyms = params.synonyms
		
		
		if(synonyms!=null){
			
			if (synonyms instanceof String) {
				
				DataElement synonym =  DataElement.get(synonyms)
				
				//remove all the value domains that aren't this one
				associatedSynonyms.each{ vd ->
					if(synonyms!=vd.id.toString()){
							dataElementInstance.removeFromSynonyms(vd)
					}
				}
				
				if(synonym){
					dataElementInstance.addToSynonyms(synonym)
				}
				
			}
			
			if (synonyms instanceof String[]) {
				
				//remove all the value domains that aren't this one
				associatedSynonyms.each{ vd ->
					if(!synonyms.contains(vd.id.toString())){
							dataElementInstance.removeFromSynonyms(vd)
					}
				}
				
				  for (synonymID in synonyms){
					  DataElement synonym =  DataElement.get(synonymID)
					  if(synonym){
							dataElementInstance.addToSynonyms(synonym)
						}
				  }
  
				  
			}

		}else{
		
		//remove all the value domains that aren't this one
		associatedSynonyms.each{ vd ->
					dataElementInstance.removeFromSynonyms(vd)
		}
		
		}
		
	}
	
	
	def linkValueDomains(dataElementInstance){
		
		def associatedValueDomains = dataElementInstance.dataElementValueDomains()
		def valueDomains = params.valueDomains
		
		if(valueDomains!=null){
			
			if (valueDomains instanceof String) {
				
				ValueDomain valueDomain =  ValueDomain.get(valueDomains)
				
				//remove all the value domains that aren't this one
				associatedValueDomains.each{ vd ->
					if(valueDomains!=vd.id.toString()){
							DataElementValueDomain.unlink(dataElementInstance, vd)
					}
				}
				
				if(valueDomain){
					
					DataElementValueDomain.link(dataElementInstance, valueDomain)
				}
				
			}
			
			if (valueDomains instanceof String[]) {
				
				//remove all the value domains that aren't this one
				associatedValueDomains.each{ vd ->
					if(!valueDomains.contains(vd.id.toString())){
							DataElementValueDomain.unlink(dataElementInstance, vd)
					}
				}
				
				  for (valueDomainID in valueDomains){
					  ValueDomain valueDomain =  ValueDomain.get(valueDomainID)
					  if(valueDomain){
							DataElementValueDomain.link(dataElementInstance, valueDomain)
						}
				  }
  
				  
			}

		}else{
		
		//remove all the value domains that aren't this one
		associatedValueDomains.each{ vd ->
					DataElementValueDomain.unlink(dataElementInstance, vd)
		}
		
		}
	}
		
	Boolean validateDataElement(){
		
		
		ArrayList children
		
		//check if subelements contain the given element
		
		if(params?.subElements!=null && params?.id!=null){
			
			children = getChildren()
			
			if(children.contains(params.id)){
				params.subElements = ''
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
		
		//check if subelements contain the parent element
		
		if(params?.subElements!=null && params?.parent?.id!=null){
			
			if(children==null){
				children = getChildren()
			}
			
			if(!ChildParentValid(params.parent.id.value.toString(), children)){
				params.subElements = ''
				flash.message = 'Error: Sub elements must not contain the parent element'
				return false
			}
		}
		
		//check if any synonyms are contained within the subelements or the parent element
		
		if(params.synonyms!=null){
			if(params?.subElements!=null){
				if(parameterContains(params.synonyms, params?.subElements)){
					params.subElements = ''
					flash.message = 'Error: Data Element Sub elements and Data Element Synonyms must be mutually exclusive'
					return false
				}
			}
			
			if(params?.parent!=null){
				if(parameterContains(params.synonyms, params?.parent)){
					params.parent = ''
					flash.message = 'Error: Data Element Parent Elements and Data Element Synonyms must be mutually exclusive'
					return false
				}
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
		
		if(params.subElements.class.isArray()){
			children.addAll(params.subElements)
		}else{
			children.add(params.subElements.value.toString())
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
			
			case 3:
				field = "dataElementConcept"
			break
			
			default:
				field = "dataElementConcept"
			break
		}
		
		return field
		
	}
	
	def parameterContains(params1, params2){
			
			if(params1 instanceof String[] && params2 instanceof String[]){
				
				params2.each{ params->
					
					if(params1.contains(params)){
						return true
					}
					
				}
				
			}else if(params1 instanceof String[] && params2 instanceof String){
			
				if(params1.contains(params2)){
					return true
				}
			
			}else if(params1 instanceof String && params2 instanceof String[]){
			
				if(params2.contains(params1)){
					return true
				}
			
			}else{
				if(params1==params2){
					return true
				}
			}
			
			return false		
	}
	
	
}
