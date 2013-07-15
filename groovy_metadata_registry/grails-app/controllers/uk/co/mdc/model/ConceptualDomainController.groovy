package uk.co.mdc.model

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

        [valueDomains: ValueDomain.list(), conceptualDomainInstance: conceptualDomainInstance]
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
}
