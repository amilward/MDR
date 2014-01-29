package uk.co.mdc.catalogue

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import org.codehaus.groovy.grails.plugins.searchable.*
import org.springframework.security.acls.model.Permission
import grails.plugins.springsecurity.Secured
import grails.rest.RestfulController
import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*

@Transactional(readOnly = true)

@Secured(['ROLE_USER']) 

class DataElementController extends RestfulController{

   // static namespace = 'v1'
   // static responseFormats = ['json', 'xml']

    static allowedMethods = [save: "POST", update: "PUT", delete: "POST"]

    //get services needed
    def dataElementService
    def catalogueElementService

    DataElementController() {
        super(DataElement)
    }


    /*
    @Override
    DataElement queryForResource(Serializable id) {
        DataElement.where { id == id }.find()
    }*/


	/* **************************************************************************************
	 * ************************************* INDEX *********************************************************
	 ********************************************************************************************* */

    @Override
    def index()  {}

    /* **************************************************************************************
     * ************************************* LIST ***************************************************
     *************************************************************************************** */

    @Override
    def list()  {
        //data = dataElementService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortCol, order: order)
        def total = dataElementService.count()
        def dataElements = dataElementService.list()
        render dataElements as JSON
    }


    /* **************************************************************************************
     * ************************************* SHOW ***************************************************
     *************************************************************************************** */

    @Override
    def show() {

        def dataElementInstance = findInstance()

        if(!dataElementInstance) {
            render status: NOT_FOUND
        }else {
            render dataElementInstance as JSON
        }

    }

    /* **************************************************************************************
* ************************************ SAVE **********************************************
*********************************************************************************** */

    @Transactional
    @Override
    def save() {

        /* ***
         * validate the data element looking at it's parent-children-synonyms and ensuring they are mutually exclusive
         * i.e. a dataElement cannot have a subElement that is the same as it's parent element
         * ****/

        def request = request.JSON

        /* *****
         * create the data element using the data element service
         ******* */


        def dataElementInstance = dataElementService.create(request.dataElement)

        /* ******
         * check that the data element has been created without errors and render accordingly
         ***** */

        if (!renderWithErrors(dataElementInstance)) {
            render dataElementInstance as JSON
        }

    }

    /* **************************************************************************************
 * ************************************ UPDATE **********************************************
 *********************************************************************************** */

    @Transactional
    @Override
    def update(Integer id) {

        /* ***
         * validate the data element for parent-children-synonym relationships and ensure they are mutually exclusive
         * i.e. a dataElement cannot have a subElement that is the same as it's parent element
         * ******/

        def request = request.JSON

        //get the data element

        def dataElementInstance = findInstance(id)

        if (!dataElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            render status: NOT_FOUND as JSON
            return
        }

        dataElementInstance = dataElementService.update(dataElementInstance, request)
       // dataElementInstance.refresh()

        if (!renderWithErrors(dataElementInstance)) {
            render dataElementInstance as JSON
        }

    }


    /* **********************************************************************************
     * this function uses the dataElement service to get the data element so that
     * the appropriate security considerations are adhered to
     *********************************************************************************** */

    private DataElement findInstance() {
        def dataElement = dataElementService.get(params.long('id'))
        if (!dataElement) {
            flash.message = "DataElement not found with id $params.id"
            redirect action: list
        }
        dataElement
    }

    private DataElement findInstance(Integer id) {
        def dataElement = dataElementService.get(id)
        if (!dataElement) {
            flash.message = "DataElement not found with id $params.id"
            redirect action: list
        }
        dataElement
    }


    /* **********************************************************************************
     * this function checks to see if the data element passed to it contains errors i.e. when a
     * service returns the element. It either returns false (if no errors) or it redirects
     * to the view specified by the caller
     *********************************************************************************** */

    private boolean renderWithErrors(DataElement dataElement) {
        if (dataElement.hasErrors()) {
            render dataElement.errors as JSON
            return true

        }
        false
    }

	
}
