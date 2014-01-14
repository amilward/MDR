package uk.co.mdc.model

import org.springframework.dao.DataIntegrityViolationException

class RelationshipController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [relationshipInstanceList: Relationship.list(params), relationshipInstanceTotal: Relationship.count()]
    }

    def create() {
        [relationshipInstance: new Relationship(params)]
    }

    def save() {
        def relationshipInstance = new Relationship(params)
        if (!relationshipInstance.save(flush: true)) {
            render(view: "create", model: [relationshipInstance: relationshipInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'relationship.label', default: 'Relationship'), relationshipInstance.id])
        redirect(action: "show", id: relationshipInstance.id)
    }

    def show(Long id) {
        def relationshipInstance = Relationship.get(id)
        if (!relationshipInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'relationship.label', default: 'Relationship'), id])
            redirect(action: "list")
            return
        }

        [relationshipInstance: relationshipInstance]
    }

    def edit(Long id) {
        def relationshipInstance = Relationship.get(id)
        if (!relationshipInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'relationship.label', default: 'Relationship'), id])
            redirect(action: "list")
            return
        }

        [relationshipInstance: relationshipInstance]
    }

    def update(Long id, Long version) {
        def relationshipInstance = Relationship.get(id)
        if (!relationshipInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'relationship.label', default: 'Relationship'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (relationshipInstance.version > version) {
                relationshipInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'relationship.label', default: 'Relationship')] as Object[],
                          "Another user has updated this Relationship while you were editing")
                render(view: "edit", model: [relationshipInstance: relationshipInstance])
                return
            }
        }

        relationshipInstance.properties = params

        if (!relationshipInstance.save(flush: true)) {
            render(view: "edit", model: [relationshipInstance: relationshipInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'relationship.label', default: 'Relationship'), relationshipInstance.id])
        redirect(action: "show", id: relationshipInstance.id)
    }

    def delete(Long id) {
        def relationshipInstance = Relationship.get(id)
        if (!relationshipInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'relationship.label', default: 'Relationship'), id])
            redirect(action: "list")
            return
        }

        try {
            relationshipInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'relationship.label', default: 'Relationship'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'relationship.label', default: 'Relationship'), id])
            redirect(action: "show", id: id)
        }
    }
}
