package uk.co.mdc.model

import org.springframework.dao.DataIntegrityViolationException

class DataElementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [dataElementInstanceList: DataElement.list(params), dataElementInstanceTotal: DataElement.count()]
    }

    def create() {
        [dataElementInstance: new DataElement(params)]
    }

    def save() {
        def dataElementInstance = new DataElement(params)
        if (!dataElementInstance.save(flush: true)) {
            render(view: "create", model: [dataElementInstance: dataElementInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'dataElement.label', default: 'DataElement'), dataElementInstance.id])
        redirect(action: "show", id: dataElementInstance.id)
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

        [dataElementInstance: dataElementInstance]
    }

    def update(Long id, Long version) {
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
                render(view: "edit", model: [dataElementInstance: dataElementInstance])
                return
            }
        }

        dataElementInstance.properties = params

        if (!dataElementInstance.save(flush: true)) {
            render(view: "edit", model: [dataElementInstance: dataElementInstance])
            return
        }

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
            dataElementInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "show", id: id)
        }
    }
}
