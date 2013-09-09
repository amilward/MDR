package uk.co.mdc.model

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
