package uk.co.mdc.catalogue

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class RelationshipTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def relationshipTypeService

    /* **************************************************************************************
     * ************************************* INDEX *********************************************************
     * default redirect to list page
     ********************************************************************************************* */

    def index() {
        redirect(action: "list", params: params)
    }

    /* **************************************************************************************
     * ************************************* LIST ***************************************************

     *....only use this to render the list template as the datatables method is used instead
     * to list all the data types
     *************************************************************************************** */

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def model =  RelationshipType.list(params)
        render model as JSON
    }

    /* **************************************************************************************
 * ********************************* DATA TABLES *************************************************

 * this function is called when listing the data types. It is called through ajax
 * using the the data tables plugin in the show.gsp view and the javascript that
 * calls the code is in main.js
 *********************************************************************************** */

    def dataTables(){

        // set the variables needed to pass back to the data tables plugin to render the data types
        def data
        def total
        def displayTotal
        def order
        def sortCol

        //if the user searches for a data type return the search results using the data type service

        if(params?.sSearch!='' && params?.sSearch!=null){

            def searchResults = relationshipTypeService.search(params.sSearch)

            total = searchResults.size()
            displayTotal = searchResults.size()

            if(total>0){
                data = searchResults
            }else{
                data=[]
            }

            //otherwise list the data types using the data type service and pass the relevant data
            //back to the data tables plugin request as json

        }else{

            order = params?.sSortDir_0
            sortCol = "name"

            data = relationshipTypeService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortCol, order: order)
            total = relationshipTypeService.count()
            displayTotal = relationshipTypeService.count()

        }

        def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]

        //NB. when the json is rendered it uses a custom json marshaller so that it includes the relevant
        //information (and doesn't return the WHOLE database due to all the links)
        //the corresponding json marshaller is stored in src/groovy/uk/co/mdc/catalogue/xxxxxxMarshaller.groovy

        render model as JSON
    }

    /* **************************************************************************************
	 * *********************************** SHOW *****************************************************

	 * show the conceptual domain in question using the find instance function and the relationshipType service
	 * ...presuming they have the appropriate permissions
	 *************************************************************************************** */


    def show(Long id) {
        def relationshipTypeInstance = findInstance()
        if (!relationshipTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), id])
            redirect(action: "list")
            return
        }

        [relationshipTypeInstance: relationshipTypeInstance]
    }


    /* **************************************************************************************
	 * ************************************* CREATE ***************************************************

	 * renders the data type template so the user can create a data type
	 * N.B additionally will only display objects that user has permission to read
	 *************************************************************************************** */


    def create() {
        [relationshipTypeInstance: new RelationshipType(params)]
    }


    /* **************************************************************************************
 * ************************************ SAVE ****************************************************

 * calls the data type service to create a data type . The service sets admin permissions
 * on that object for the user in question and creates the data type
 *************************************************************************************** */

    def save() {

        /* *****
		 * create the relationship type using the data type service
		 ******* */

        def relationshipTypeInstance = relationshipTypeService.create(params)


        /* ******
		 * check that the relationship type has been created without errors and render accordingly
		 ***** */

        if (!renderWithErrors('create', relationshipTypeInstance)) {
            redirectShow(message(code: 'default.created.message', args: [message(code: 'relationshipType.label', default: 'relationshipType'), relationshipTypeInstance.id]), relationshipTypeInstance.id)
        }


    }

/* **************************************************************************************
	 * ************************************** EDIT ******************************************

	 * this function redirects to the edit data type screen
	 ************************************************************************************** */

    def edit(Long id) {
        def relationshipTypeInstance = RelationshipType.get(id)
        if (!relationshipTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), id])
            redirect(action: "list")
            return
        }

        [relationshipTypeInstance: relationshipTypeInstance]
    }

    /* **************************************************************************************
 * ************************************ UPDATE ******************************************

 * this function updates the data type using the data type service
 *************************************************************************************** */

    def update(Long id, Long version) {
        def relationshipTypeInstance = findInstance()
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

        relationshipTypeService.update(relationshipTypeInstance, params)

        if (!renderWithErrors('update', relationshipTypeInstance)) {
            redirectShow message(code: 'default.updated.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), relationshipTypeInstance.id]), relationshipTypeInstance.id
        }

    }


    /* **************************************************************************************
	 * ********************************* DELETE *************************************************

	 * this function deletes the data type using the data type service
	 *********************************************************************************** */

    def delete(Long id) {
        def relationshipTypeInstance = findInstance()
        if (!relationshipTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), id])
            redirect(action: "list")
            return
        }

        try {
            relationshipTypeService.delete(relationshipTypeInstance)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'relationshipType.label', default: 'RelationshipType'), id])
            redirect(action: "show", id: id)
        }
    }


    /* **************************************************************************************
	 * ********************************* GRANT *************************************************
	
	 * this function grant permission to the given relationshipType
	 *********************************************************************************** */


    def grant = {

        def relationshipType = findInstance()

        if (!relationshipType) return

        if (!request.post) {
            return [relationshipTypeInstance: relationshipType]
        }

        relationshipTypeService.addPermission(relationshipType, params.recipient, params.int('permission'))

        redirectShow "Permission $params.permission granted on Report $relationshipType.id " + "to $params.recipient", relationshipType.id
    }

    /* **********************************************************************************
     * this function uses the relationshipType service to get the relationshipType so that
     * the appropriate security considerations are adhered to
     *********************************************************************************** */

    private RelationshipType findInstance() {
        def relationshipType = relationshipTypeService.get(params.long('id'))
        if (!relationshipType) {
            flash.message = "RelationshipType not found with id $params.id"
            redirect action: list
        }
        relationshipType
    }

    /* **********************************************************************************
     * this function redirects to the show relationshipType screen
     *********************************************************************************** */

    private void redirectShow(message, id) {
        flash.message = message
        //redirect with message

        redirect(action: "show", id: id)
    }

    /* **********************************************************************************
     * this function checks to see if the relationshipType passed to it contains errors i.e. when a
     * service returns the element. It either returns false (if no errors) or it redirects
     * to the view specified by the caller
     *********************************************************************************** */

    private boolean renderWithErrors(String view, RelationshipType relationshipType) {
        if (relationshipType.hasErrors()) {
            render view: view, model: [relationshipTypeInstance: relationshipType]
            return true

        }
        false
    }



}
