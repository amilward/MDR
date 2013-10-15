package uk.co.mdc.pathways

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class PathwaysModelController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [pathwaysModelInstanceList: PathwaysModel.list(params), pathwaysModelInstanceTotal: PathwaysModel.count()]
    }

    def create() {
        [pathwaysModelInstance: new PathwaysModel(params)]
    }

    def save() {
        def pathwaysModelInstance = new PathwaysModel(params)
        if (!pathwaysModelInstance.save(flush: true)) {
            render(view: "create", model: [pathwaysModelInstance: pathwaysModelInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'pathwaysModel.label', default: 'PathwaysModel'), pathwaysModelInstance.id])
        redirect(action: "show", id: pathwaysModelInstance.id)
    }

    def show(Long id) {
        def pathwaysModelInstance = PathwaysModel.get(id)
        if (!pathwaysModelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pathwaysModel.label', default: 'PathwaysModel'), id])
            redirect(action: "list")
            return
        }

        [pathwaysModelInstance: pathwaysModelInstance]
    }

	def jsonPathways(Long id){
		
		def pathwaysModelInstance = PathwaysModel.get(id)
		if (!pathwaysModelInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pathwaysModel.label', default: 'PathwaysModel'), id])
			redirect(action: "list")
			return
		}

		def model = [pathwaysModelInstance: pathwaysModelInstance]
		
		render model as JSON
	}
	
    def edit(Long id) {
        def pathwaysModelInstance = PathwaysModel.get(id)
        if (!pathwaysModelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pathwaysModel.label', default: 'PathwaysModel'), id])
            redirect(action: "list")
            return
        }

        [pathwaysModelInstance: pathwaysModelInstance]
    }

    def update(Long id, Long version) {
        def pathwaysModelInstance = PathwaysModel.get(id)
        if (!pathwaysModelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pathwaysModel.label', default: 'PathwaysModel'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (pathwaysModelInstance.version > version) {
                pathwaysModelInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'pathwaysModel.label', default: 'PathwaysModel')] as Object[],
                          "Another user has updated this PathwaysModel while you were editing")
                render(view: "edit", model: [pathwaysModelInstance: pathwaysModelInstance])
                return
            }
        }

        pathwaysModelInstance.properties = params

        if (!pathwaysModelInstance.save(flush: true)) {
            render(view: "edit", model: [pathwaysModelInstance: pathwaysModelInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pathwaysModel.label', default: 'PathwaysModel'), pathwaysModelInstance.id])
        redirect(action: "show", id: pathwaysModelInstance.id)
    }

    def delete(Long id) {
        def pathwaysModelInstance = PathwaysModel.get(id)
        if (!pathwaysModelInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pathwaysModel.label', default: 'PathwaysModel'), id])
            redirect(action: "list")
            return
        }

        try {
            pathwaysModelInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pathwaysModel.label', default: 'PathwaysModel'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pathwaysModel.label', default: 'PathwaysModel'), id])
            redirect(action: "show", id: id)
        }
    }
}
