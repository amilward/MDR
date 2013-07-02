package uk.co.mdc.model

import org.springframework.dao.DataIntegrityViolationException

class UmlModelController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [umlModelInstanceList: UmlModel.list(params), umlModelInstanceTotal: UmlModel.count()]
    }

    def create() {
        [umlModelInstance: new UmlModel(params)]
    }

    def save() {
        def umlModelInstance = new UmlModel(params)
        if (!umlModelInstance.save(flush: true)) {
            render(view: "create", model: [umlModelInstance: umlModelInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'umlModel.label', default: 'UmlModel'), umlModelInstance.id])
        redirect(action: "show", id: umlModelInstance.id)
    }

    def show(Long id) {
        def umlModelInstance = UmlModel.get(id)
        if (!umlModelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'umlModel.label', default: 'UmlModel'), id])
            redirect(action: "list")
            return
        }

        [umlModelInstance: umlModelInstance]
    }

    def edit(Long id) {
        def umlModelInstance = UmlModel.get(id)
        if (!umlModelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'umlModel.label', default: 'UmlModel'), id])
            redirect(action: "list")
            return
        }

        [umlModelInstance: umlModelInstance]
    }

    def update(Long id, Long version) {
        def umlModelInstance = UmlModel.get(id)
        if (!umlModelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'umlModel.label', default: 'UmlModel'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (umlModelInstance.version > version) {
                umlModelInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'umlModel.label', default: 'UmlModel')] as Object[],
                          "Another user has updated this UmlModel while you were editing")
                render(view: "edit", model: [umlModelInstance: umlModelInstance])
                return
            }
        }

        umlModelInstance.properties = params

        if (!umlModelInstance.save(flush: true)) {
            render(view: "edit", model: [umlModelInstance: umlModelInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'umlModel.label', default: 'UmlModel'), umlModelInstance.id])
        redirect(action: "show", id: umlModelInstance.id)
    }

    def delete(Long id) {
        def umlModelInstance = UmlModel.get(id)
        if (!umlModelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'umlModel.label', default: 'UmlModel'), id])
            redirect(action: "list")
            return
        }

        try {
            umlModelInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'umlModel.label', default: 'UmlModel'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'umlModel.label', default: 'UmlModel'), id])
            redirect(action: "show", id: id)
        }
    }
}
