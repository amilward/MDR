package uk.co.mdc.model

import org.springframework.dao.DataIntegrityViolationException

class RelationshipTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [relationshipTypeInstanceList: RelationshipType.list(params), relationshipTypeInstanceTotal: RelationshipType.count()]
    }

    def create() {
        [relationshipTypeInstance: new RelationshipType(params)]
    }

    def save() {
        def relationshipTypeInstance = new RelationshipType(params)
        if (!relationshipTypeInstance.save(flush: true)) {
            render(view: "create", model: [relationshipTypeInstance: relationshipTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), relationshipTypeInstance.id])
        redirect(action: "show", id: relationshipTypeInstance.id)
    }

    def show(Long id) {
        def relationshipTypeInstance = RelationshipType.get(id)
        if (!relationshipTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), id])
            redirect(action: "list")
            return
        }

        [relationshipTypeInstance: relationshipTypeInstance]
    }

    def edit(Long id) {
        def relationshipTypeInstance = RelationshipType.get(id)
        if (!relationshipTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), id])
            redirect(action: "list")
            return
        }

        [relationshipTypeInstance: relationshipTypeInstance]
    }

    def update(Long id, Long version) {
        def relationshipTypeInstance = RelationshipType.get(id)
        if (!relationshipTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (relationshipTypeInstance.version > version) {
                relationshipTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'relationshipType.label', default: 'RelationshipType')] as Object[],
                          "Another user has updated this RelationshipType while you were editing")
                render(view: "edit", model: [relationshipTypeInstance: relationshipTypeInstance])
                return
            }
        }

        relationshipTypeInstance.properties = params

        if (!relationshipTypeInstance.save(flush: true)) {
            render(view: "edit", model: [relationshipTypeInstance: relationshipTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), relationshipTypeInstance.id])
        redirect(action: "show", id: relationshipTypeInstance.id)
    }

    def delete(Long id) {
        def relationshipTypeInstance = RelationshipType.get(id)
        if (!relationshipTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), id])
            redirect(action: "list")
            return
        }

        try {
            relationshipTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), id])
            redirect(action: "show", id: id)
        }
    }
}
