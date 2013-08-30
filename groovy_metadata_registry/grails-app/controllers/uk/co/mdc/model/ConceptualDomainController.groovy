package uk.co.mdc.model

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException
import org.apache.commons.lang.StringUtils

class ConceptualDomainController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [conceptualDomainInstanceList: ConceptualDomain.list(params), conceptualDomainInstanceTotal: ConceptualDomain.count()]
    }
	
	
	def dataTables(){
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		def sortColName
		

		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = ConceptualDomain.search(params.sSearch, [max:params.iDisplayLength])
			
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
			
			data = ConceptualDomain.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortColName, order: order)
			total = ConceptualDomain.count()
			displayTotal = ConceptualDomain.count()
			
		}
		
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
				
		render model as JSON
	}
	

    def create() {
        [valueDomains: ValueDomain.list(), conceptualDomainInstance: new ConceptualDomain(params)]
    }

    def save() {
        def conceptualDomainInstance = new ConceptualDomain(params)
        if (!conceptualDomainInstance.save(flush: true)) {
            render(view: "create", model: [conceptualDomainInstance: conceptualDomainInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), conceptualDomainInstance.id])
        redirect(action: "show", id: conceptualDomainInstance.id)
    }

    def show(Long id) {
        def conceptualDomainInstance = ConceptualDomain.get(id)
        if (!conceptualDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), id])
            redirect(action: "list")
            return
        }

        [conceptualDomainInstance: conceptualDomainInstance]
    }

    def edit(Long id) {
        def conceptualDomainInstance = ConceptualDomain.get(id)
        if (!conceptualDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), id])
            redirect(action: "list")
            return
        }

        [valueDomains: ValueDomain.list(), selectedValueDomains: conceptualDomainInstance.valueDomains , conceptualDomainInstance: conceptualDomainInstance]
    }

    def update(Long id, Long version) {
        def conceptualDomainInstance = ConceptualDomain.get(id)
        if (!conceptualDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (conceptualDomainInstance.version > version) {
                conceptualDomainInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'conceptualDomain.label', default: 'ConceptualDomain')] as Object[],
                          "Another user has updated this ConceptualDomain while you were editing")
                render(view: "edit", model: [conceptualDomainInstance: conceptualDomainInstance])
                return
            }
        }	

		// remove valueDomains (if needed)
		
		unLinkValueDomains(conceptualDomainInstance)
		
        conceptualDomainInstance.properties = params
		
        if (!conceptualDomainInstance.save(flush: true)) {
            render(view: "edit", model: [conceptualDomainInstance: conceptualDomainInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), conceptualDomainInstance.id])
        redirect(action: "show", id: conceptualDomainInstance.id)
    }

    def delete(Long id) {
        def conceptualDomainInstance = ConceptualDomain.get(id)
        if (!conceptualDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), id])
            redirect(action: "list")
            return
        }

        try {
			conceptualDomainInstance.prepareForDelete()
            conceptualDomainInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'conceptualDomain.label', default: 'ConceptualDomain'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def removeValueDomain(){
		ValueDomain valueDomain = ValueDomain.get(params.valueDomainId)
		ConceptualDomain conceptualDomain = ConceptualDomain.get(params.conceptualDomainId)
		if(valueDomain && conceptualDomain){
			conceptualDomain.removeFromValueDomains(valueDomain)
		}
		
		redirect(action: 'edit', id: params.conceptualDomainId)

	}
	
	def unLinkValueDomains(conceptualDomainInstance){
		
		//if all data elements need to be removed or only a few elements need to be removed
		
			if(params?.valueDomains==null && conceptualDomainInstance?.valueDomains.size()>0){
				
				def valueDomains = []
				valueDomains += conceptualDomainInstance?.valueDomains
				
				valueDomains.each{ valueDomain->
					conceptualDomainInstance.removeFromValueDomains(valueDomain)
				}
				
	
			}else if(params?.valueDomains){
		
			if(params?.valueDomains.size() < conceptualDomainInstance?.valueDomains.size()){
			
				def valueDomains = []
				
				valueDomains += conceptualDomainInstance?.valueDomains
				
				valueDomains.each{ valueDomain->
					
	
					if(params?.valueDomains instanceof String){
						
							if(params?.valueDomains!=valueDomain){
						
								conceptualDomainInstance.removeFromValueDomains(valueDomain)
							
							}
						
						}else{
							
							if(!params?.valueDomains.contains(valueDomain)){
								
								conceptualDomainInstance.removeFromValueDomains(valueDomain)
								
							}
						
						}
					}
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
				field = "description"
			break
			
			default:
				field = "name"
			break
		}
		
		return field
		
	}
	
}
