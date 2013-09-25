package uk.co.mdc.forms

import org.springframework.dao.DataIntegrityViolationException

class FieldController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [fieldInstanceList: Field.list(params), fieldInstanceTotal: Field.count()]
    }

    def create() {
        [fieldInstance: new Field(params)]
    }

    def save() {
        def fieldInstance = new Field(params)
        if (!fieldInstance.save(flush: true)) {
            render(view: "create", model: [fieldInstance: fieldInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'field.label', default: 'Field'), fieldInstance.id])
        redirect(action: "show", id: fieldInstance.id)
    }

    def show(Long id) {
        def fieldInstance = Field.get(id)
        if (!fieldInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'field.label', default: 'Field'), id])
            redirect(action: "list")
            return
        }

        [fieldInstance: fieldInstance]
    }

    def edit(Long id) {
        def fieldInstance = Field.get(id)
        if (!fieldInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'field.label', default: 'Field'), id])
            redirect(action: "list")
            return
        }

        [fieldInstance: fieldInstance]
    }

    def update(Long id, Long version) {
        def fieldInstance = Field.get(id)
        if (!fieldInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'field.label', default: 'Field'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (fieldInstance.version > version) {
                fieldInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'field.label', default: 'Field')] as Object[],
                          "Another user has updated this Field while you were editing")
                render(view: "edit", model: [fieldInstance: fieldInstance])
                return
            }
        }

        fieldInstance.properties = params

        if (!fieldInstance.save(flush: true)) {
            render(view: "edit", model: [fieldInstance: fieldInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'field.label', default: 'Field'), fieldInstance.id])
        redirect(action: "show", id: fieldInstance.id)
    }

    def delete(Long id) {
        def fieldInstance = Field.get(id)
        if (!fieldInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'field.label', default: 'Field'), id])
            redirect(action: "list")
            return
        }

        try {
            fieldInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'field.label', default: 'Field'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'field.label', default: 'Field'), id])
            redirect(action: "show", id: id)
        }
    }
}
