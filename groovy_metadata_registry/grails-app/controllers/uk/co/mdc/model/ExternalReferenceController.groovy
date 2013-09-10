package uk.co.mdc.model

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class ExternalReferenceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [externalReferenceInstanceList: ExternalReference.list(params), externalReferenceInstanceTotal: ExternalReference.count()]
    }
	
	def dataTables(){
		
				def data
				def total
				def displayTotal
				def order
				def sortCol
				
		
				if(params?.sSearch!='' && params?.sSearch!=null){
					
					def searchResults = ExternalReference.search(params.sSearch, [max:params.iDisplayLength])
					
					total = searchResults.total
					displayTotal = searchResults.total
					
					if(total>0){
						data = searchResults.results
					}else{
						data=[]
					}
					
					
					
				}else{
				
					order = params?.sSortDir_0
					sortCol = "name"
					
					data = ExternalReference.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortCol, order: order)
					total = ExternalReference.count()
					displayTotal = ExternalReference.count()
					
				}
				
				
		
				def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
				
				render model as JSON
	}

    def create() {
        [externalReferenceInstance: new ExternalReference(params)]
    }

    def save() {
        def externalReferenceInstance = new ExternalReference(params)
        if (!externalReferenceInstance.save(flush: true)) {
            render(view: "create", model: [externalReferenceInstance: externalReferenceInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), externalReferenceInstance.id])
        redirect(action: "show", id: externalReferenceInstance.id)
    }

    def show(Long id) {
        def externalReferenceInstance = ExternalReference.get(id)
        if (!externalReferenceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), id])
            redirect(action: "list")
            return
        }

        [externalReferenceInstance: externalReferenceInstance]
    }

    def edit(Long id) {
        def externalReferenceInstance = ExternalReference.get(id)
        if (!externalReferenceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), id])
            redirect(action: "list")
            return
        }

        [externalReferenceInstance: externalReferenceInstance]
    }

    def update(Long id, Long version) {
        def externalReferenceInstance = ExternalReference.get(id)
        if (!externalReferenceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (externalReferenceInstance.version > version) {
                externalReferenceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'externalReference.label', default: 'ExternalReference')] as Object[],
                          "Another user has updated this ExternalReference while you were editing")
                render(view: "edit", model: [externalReferenceInstance: externalReferenceInstance])
                return
            }
        }

        externalReferenceInstance.properties = params

        if (!externalReferenceInstance.save(flush: true)) {
            render(view: "edit", model: [externalReferenceInstance: externalReferenceInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), externalReferenceInstance.id])
        redirect(action: "show", id: externalReferenceInstance.id)
    }

    def delete(Long id) {
        def externalReferenceInstance = ExternalReference.get(id)
        if (!externalReferenceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), id])
            redirect(action: "list")
            return
        }

        try {
            externalReferenceInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'externalReference.label', default: 'ExternalReference'), id])
            redirect(action: "show", id: id)
        }
    }
}
