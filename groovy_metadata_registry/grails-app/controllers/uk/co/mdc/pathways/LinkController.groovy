package uk.co.mdc.pathways

import org.springframework.dao.DataIntegrityViolationException

class LinkController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [linkInstanceList: Link.list(params), linkInstanceTotal: Link.count()]
    }

    def create() {
        [linkInstance: new Link(params)]
    }

    def save() {
        def linkInstance = new Link(params)
        if (!linkInstance.save(flush: true)) {
            render(view: "create", model: [linkInstance: linkInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'link.label', default: 'Link'), linkInstance.id])
        redirect(action: "show", id: linkInstance.id)
    }

    def show(Long id) {
        def linkInstance = Link.get(id)
        if (!linkInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'link.label', default: 'Link'), id])
            redirect(action: "list")
            return
        }

        [linkInstance: linkInstance]
    }

    def edit(Long id) {
        def linkInstance = Link.get(id)
        if (!linkInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'link.label', default: 'Link'), id])
            redirect(action: "list")
            return
        }

        [linkInstance: linkInstance]
    }

    def update(Long id, Long version) {
        def linkInstance = Link.get(id)
        if (!linkInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'link.label', default: 'Link'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (linkInstance.version > version) {
                linkInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'link.label', default: 'Link')] as Object[],
                          "Another user has updated this Link while you were editing")
                render(view: "edit", model: [linkInstance: linkInstance])
                return
            }
        }

        linkInstance.properties = params

        if (!linkInstance.save(flush: true)) {
            render(view: "edit", model: [linkInstance: linkInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'link.label', default: 'Link'), linkInstance.id])
        redirect(action: "show", id: linkInstance.id)
    }

    def delete(Long id) {
        def linkInstance = Link.get(id)
        if (!linkInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'link.label', default: 'Link'), id])
            redirect(action: "list")
            return
        }

        try {
            linkInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'link.label', default: 'Link'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'link.label', default: 'Link'), id])
            redirect(action: "show", id: id)
        }
    }
}
