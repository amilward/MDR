package uk.co.mdc.model

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

class ValueDomainController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [valueDomainInstanceList: ValueDomain.list(params), valueDomainInstanceTotal: ValueDomain.count()]
    }
	
	def dataTables(){
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		def sortColName
		

		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = ValueDomain.search(params.sSearch, [max:params.iDisplayLength])
			
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
			
			data = ValueDomain.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortColName, order: order)
			total = ValueDomain.count()
			displayTotal = ValueDomain.count()
			
		}
		
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
				
		render model as JSON
	}

    def create() {
        [dataElements: DataElement.list(), dataTypes: DataType.list(), externalSynonyms: ExternalSynonym.list(), valueDomainInstance: new ValueDomain(params)]
    }

    def save() {
		
		
		if(params?.dataType){
			DataType dataType = DataType.get(params?.dataType)
			params.dataType = dataType
		}
        def valueDomainInstance = new ValueDomain(params)
        if (!valueDomainInstance.save(flush: true)) {
            render(view: "create", model: [valueDomainInstance: valueDomainInstance])
            return
        }
		
		linkDataElements(valueDomainInstance)
		

        flash.message = message(code: 'default.created.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), valueDomainInstance.id])
        redirect(action: "show", id: valueDomainInstance.id, model: [dataElements: DataElement.list()])
    }

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
	
	
	
	def linkDataElements(valueDomainInstance){
		def associatedDataElements = valueDomainInstance.dataElementValueDomains()
		def dataElements = params.dataElements
		
		if(dataElements!=null){
			
			if (dataElements instanceof String) {
				
				DataElement dataElement =  DataElement.get(dataElements)
				
				associatedDataElements.each{ vd ->
					if(dataElements!=vd.id.toString()){
							valueDomainInstance.removeFromDataElementValueDomains(vd)
					}
				}

				if(dataElement){
					
					DataElementValueDomain.link(dataElement, valueDomainInstance)
					
				}
				
			} else if (dataElements instanceof String[]) {
			
			
				//remove all the value domains that aren't this one
				associatedDataElements.each{ vd ->
					if(!dataElements.contains(vd.id.toString())){
							valueDomainInstance.removeFromDataElementValueDomains(vd)
					}
				}
			
				  for (dataElementID in dataElements){
					  DataElement dataElement =  DataElement.get(dataElementID)
					  if(dataElement){
						  DataElementValueDomain.link(dataElement, valueDomainInstance)
					  }
				  }
			}

		}else{
			
			//remove all the data elements that aren't this one
			associatedDataElements.each{ vd ->
						valueDomainInstance.removeFromDataElementValueDomains(vd)
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
