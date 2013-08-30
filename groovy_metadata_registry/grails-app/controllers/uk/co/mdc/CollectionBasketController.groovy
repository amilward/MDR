package uk.co.mdc

import grails.converters.JSON
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.dao.DataIntegrityViolationException
import uk.co.mdc.model.DataElement

class CollectionBasketController {
	
	SpringSecurityService springSecurityService

    static allowedMethods = [listJSON: "GET",addElement: "POST", save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [collectionBasketInstanceList: CollectionBasket.list(params), collectionBasketInstanceTotal: CollectionBasket.count()]
    }
	
	def dataElementsAsJSON() {
		def current_user = SecUser.get(springSecurityService.currentUser.id)
		def collectionBasketInstance = current_user.collectionBasket
		
		def model = ["message":"fail"]
		
		if(collectionBasketInstance){
			model = [dataElements: collectionBasketInstance?.dataElements]
		}
		
		render model as JSON
	}

    def create() {
        [collectionBasketInstance: new CollectionBasket(params)]
    }
	
	def addElement(){
		def current_user = SecUser.get(springSecurityService.currentUser.id)
		def dataElement = DataElement.get(params.id)
		def collectionBasketInstance = current_user.collectionBasket
		def model = [message:"fail"]
		if(!collectionBasketInstance){
			render model as JSON
		}
		
		collectionBasketInstance.addToDataElements(dataElement)
		
		if(collectionBasketInstance.save(flush:true)){
			model = [message:"success"]
		}
		
		
		render model as JSON
	}
	
	
	def removeElement(){
		def current_user = SecUser.get(springSecurityService.currentUser.id)
		def dataElement = DataElement.get(params.id)
		def collectionBasketInstance = current_user.collectionBasket
		def model = [message:"fail"]
		if(!collectionBasketInstance){
			render model as JSON
		}
		
		collectionBasketInstance.removeFromDataElements(dataElement)
		
		if(collectionBasketInstance.save(flush:true)){
			model = [message:"success"]
		}
		
		
		render model as JSON
	}

    def save() {
        def collectionBasketInstance = new CollectionBasket(params)
        if (!collectionBasketInstance.save(flush: true)) {
            render(view: "create", model: [collectionBasketInstance: collectionBasketInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'collectionBasket.label', default: 'CollectionBasket'), collectionBasketInstance.id])
        redirect(action: "show", id: collectionBasketInstance.id)
    }

    def show(Long id) {
        def collectionBasketInstance = CollectionBasket.get(id)
        if (!collectionBasketInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'collectionBasket.label', default: 'CollectionBasket'), id])
            redirect(action: "list")
            return
        }

        [collectionBasketInstance: collectionBasketInstance, errors: params?.errors, refId: params?.refId, name: params?.name, description: params?.description]
    }

    def edit(Long id) {
        def collectionBasketInstance = CollectionBasket.get(id)
        if (!collectionBasketInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'collectionBasket.label', default: 'CollectionBasket'), id])
            redirect(action: "list")
            return
        }

        [collectionBasketInstance: collectionBasketInstance]
    }

    def update(Long id, Long version) {
        def collectionBasketInstance = CollectionBasket.get(id)
        if (!collectionBasketInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'collectionBasket.label', default: 'CollectionBasket'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (collectionBasketInstance.version > version) {
                collectionBasketInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'collectionBasket.label', default: 'CollectionBasket')] as Object[],
                          "Another user has updated this CollectionBasket while you were editing")
                render(view: "edit", model: [collectionBasketInstance: collectionBasketInstance])
                return
            }
        }

        collectionBasketInstance.properties = params

        if (!collectionBasketInstance.save(flush: true)) {
            render(view: "edit", model: [collectionBasketInstance: collectionBasketInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'collectionBasket.label', default: 'CollectionBasket'), collectionBasketInstance.id])
        redirect(action: "show", id: collectionBasketInstance.id)
    }

    def delete(Long id) {
        def collectionBasketInstance = CollectionBasket.get(id)
        if (!collectionBasketInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'collectionBasket.label', default: 'CollectionBasket'), id])
            redirect(action: "list")
            return
        }

        try {
            collectionBasketInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'collectionBasket.label', default: 'CollectionBasket'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'collectionBasket.label', default: 'CollectionBasket'), id])
            redirect(action: "show", id: id)
        }
    }
}
