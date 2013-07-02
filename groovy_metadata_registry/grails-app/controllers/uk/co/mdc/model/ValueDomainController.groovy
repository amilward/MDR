package uk.co.mdc.model

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

    def create() {
        [valueDomainInstance: new ValueDomain(params)]
    }

    def save() {
        def valueDomainInstance = new ValueDomain(params)
        if (!valueDomainInstance.save(flush: true)) {
            render(view: "create", model: [valueDomainInstance: valueDomainInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), valueDomainInstance.id])
        redirect(action: "show", id: valueDomainInstance.id)
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

        [valueDomainInstance: valueDomainInstance]
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
                render(view: "edit", model: [valueDomainInstance: valueDomainInstance])
                return
            }
        }

        valueDomainInstance.properties = params

        if (!valueDomainInstance.save(flush: true)) {
            render(view: "edit", model: [valueDomainInstance: valueDomainInstance])
            return
        }

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
            valueDomainInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
            redirect(action: "show", id: id)
        }
    }
}
