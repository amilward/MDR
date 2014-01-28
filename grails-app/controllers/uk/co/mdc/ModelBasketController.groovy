package uk.co.mdc

import grails.converters.JSON
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.dao.DataIntegrityViolationException
import uk.co.mdc.catalogue.DataElement

class ModelBasketController {

    SpringSecurityService springSecurityService

    static allowedMethods = [listJSON: "GET", addElement: "POST", removeElement: "POST"]

    /* This method is called from the dashboard template (main.js - collection basket scripts)
     *  ajax to render the data elements that have been included
     *  in the collection basket i.e. they have been added by dragging the data elements over the collection basket
     * */

    def dataElementsAsJSON() {
        def current_user = SecUser.get(springSecurityService.currentUser.id)
        def modelBasketInstance = current_user.modelBasket

        def model = ["message": "fail"]

        if (modelBasketInstance) {
            model = [dataElements: modelBasketInstance?.dataElements]
        }

        render model as JSON
    }

    /* This method is called from the dashboard template (main.js - collection basket scripts)
     * when the user drags a data element over the collection basket to add the element*/

    def addElement() {
        def current_user = SecUser.get(springSecurityService.currentUser.id)
        def dataElement = DataElement.get(params.id)
        def modelBasketInstance = current_user.modelBasket
        def model = [message: "fail"]
        if (!modelBasketInstance) {
            render model as JSON
        }

        modelBasketInstance.addToDataElements(dataElement)

        if (modelBasketInstance.save(flush: true)) {
            model = [message: "success"]
        }


        render model as JSON
    }

    /* This method is called from the dashboard template (main.js - collection basket scripts)
     *  when the user drags a data element out of the collection basket to remove the element */

    def removeElement() {
        def current_user = SecUser.get(springSecurityService.currentUser.id)
        def dataElement = DataElement.get(params.id)
        def modelBasketInstance = current_user.modelBasket
        def model = [message: "fail"]
        if (!modelBasketInstance) {
            render model as JSON
        }

        modelBasketInstance.removeFromDataElements(dataElement)

        if (modelBasketInstance.save(flush: true)) {
            model = [message: "success"]
        }


        render model as JSON
    }

    /* This method renders the collection basket view where the user can view the data elements they have added as well as submit extra information such as whether
     * the data element is mandatory/required in the collection. Finally the user can then save the collection which created a collection object and clears all the
     * data elements in the collection basket*/

    def show() {
        def current_user = SecUser.get(springSecurityService.currentUser.id)
        def modelBasketInstance = current_user.modelBasket
        if (!modelBasketInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'modelBasket.label', default: 'modelBasket'), id])
            redirect(controller: "index")
            return
        }

        [modelBasketInstance: modelBasketInstance, errors: params?.errors, name: params?.name, description: params?.description]
    }

}
