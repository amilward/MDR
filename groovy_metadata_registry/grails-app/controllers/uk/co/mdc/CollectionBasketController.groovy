package uk.co.mdc

import grails.converters.JSON
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.dao.DataIntegrityViolationException
import uk.co.mdc.model.DataElement

class CollectionBasketController {
	
	SpringSecurityService springSecurityService

    static allowedMethods = [listJSON: "GET", addElement: "POST", removeElement: "POST"]
	
	/* This method is called from the dashboard template (main.js - collection basket scripts)
	 *  ajax to render the data elements that have been included
	 *  in the collection basket i.e. they have been added by dragging the data elements over the collection basket
	 * */
	
	def dataElementsAsJSON() {
		def current_user = SecUser.get(springSecurityService.currentUser.id)
		def collectionBasketInstance = current_user.collectionBasket
		
		def model = ["message":"fail"]
		
		if(collectionBasketInstance){
			model = [dataElements: collectionBasketInstance?.dataElements]
		}
		
		render model as JSON
	}
	
	/* This method is called from the dashboard template (main.js - collection basket scripts)
	 * when the user drags a data element over the collection basket to add the element*/
	
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
	
	/* This method is called from the dashboard template (main.js - collection basket scripts)
	 *  when the user drags a data element out of the collection basket to remove the element */
	
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

	
	/* This method renders the collection basket view where the user can view the data elements they have added as well as submit extra information such as whether
	 * the data element is mandatory/required in the collection. Finally the user can then save the collection which created a collection object and clears all the 
	 * data elements in the collection basket*/

    def show() {
        def current_user = SecUser.get(springSecurityService.currentUser.id)
		def collectionBasketInstance = current_user.collectionBasket
		println(collectionBasketInstance)
        if (!collectionBasketInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'collectionBasket.label', default: 'CollectionBasket'), id])
            redirect(controller: "index")
            return
        }

        [collectionBasketInstance: collectionBasketInstance, errors: params?.errors, refId: params?.refId, name: params?.name, description: params?.description]
    }

}
