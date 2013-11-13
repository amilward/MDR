package uk.co.mdc.pathways

import org.springframework.dao.DataIntegrityViolationException

class NodeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [nodeInstanceList: Node.list(params), nodeInstanceTotal: Node.count()]
    }

    def create() {
        [nodeInstance: new Node(params)]
    }

    def save() {
        def nodeInstance = new Node(params)
        if (!nodeInstance.save(flush: true)) {
            render(view: "create", model: [nodeInstance: nodeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'node.label', default: 'Node'), nodeInstance.id])
        redirect(action: "show", id: nodeInstance.id)
    }

    def show(Long id) {
        def nodeInstance = Node.get(id)
        if (!nodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'node.label', default: 'Node'), id])
            redirect(action: "list")
            return
        }

        [nodeInstance: nodeInstance]
    }

    def edit(Long id) {
        def nodeInstance = Node.get(id)
        if (!nodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'node.label', default: 'Node'), id])
            redirect(action: "list")
            return
        }

        [nodeInstance: nodeInstance]
    }

    def update(Long id, Long version) {
        def nodeInstance = Node.get(id)
        if (!nodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'node.label', default: 'Node'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (nodeInstance.version > version) {
                nodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'node.label', default: 'Node')] as Object[],
                          "Another user has updated this Node while you were editing")
                render(view: "edit", model: [nodeInstance: nodeInstance])
                return
            }
        }

        nodeInstance.properties = params

        if (!nodeInstance.save(flush: true)) {
            render(view: "edit", model: [nodeInstance: nodeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'node.label', default: 'Node'), nodeInstance.id])
        redirect(action: "show", id: nodeInstance.id)
    }

    def delete(Long id) {
        def nodeInstance = Node.get(id)
        if (!nodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'node.label', default: 'Node'), id])
            redirect(action: "list")
            return
        }

        try {
            nodeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'node.label', default: 'Node'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'node.label', default: 'Node'), id])
            redirect(action: "show", id: id)
        }
    }
}
