package uk.co.mdc.pathways

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException
import org.codehaus.groovy.grails.plugins.searchable.*
import org.springframework.security.acls.model.Permission

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class PathwaysModelController {
	
	def pathwaysService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	/* **************************************************************************************
	 * ************************************* INDEX *********************************************************
	 
	 * default redirect to list dataElement page
	 ********************************************************************************************* */
	
	
    def index() {
        redirect(action: "list", params: params)
    }

    /* **************************************************************************************
	 * ************************************* LIST ***************************************************
	 
	 *....only use this to render the list template as the datatables method is used instead
	 * to list all the data elements
	 *************************************************************************************** */

	def list =  {
		[]
	}
	
	
	/* **************************************************************************************
	 * ********************************* DATA TABLES *************************************************
	
	 * this function is called when listing the data elements. It is called through ajax
	 * using the the data tables plugin in the show.gsp view and the javascript that
	 * calls the code is in main.js
	 *********************************************************************************** */
	
	
	
	def dataTables(){
		
		// set the variables needed to pass back to the data tables plugin to render the data elements
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		
		//if the user searches for a data element return the search results using the data Element service
		

		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = pathwaysService.search(params.sSearch)
			
			total = searchResults.size()
			displayTotal = searchResults.size()
			
			if(total>0){
				data = searchResults
			}else{
				data=[]
			}

			//otherwise list the data elements using the data elements service and pass the relevant data
			//back to the data tables plugin request as json
			
		}else{
		
			order = params?.sSortDir_0
			sortCol = getSortField(params?.iSortCol_0.toInteger())
			data = pathwaysService.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortCol, order: order)
			total = pathwaysService.count()
			displayTotal = pathwaysService.count()
			
		}
		
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]

		//NB. when the json is rendered it uses a custom json marshaller so that it includes the relevant
		//information (and doesn't return the whole database)
		//the corresponding json marshaller is stored in src/groovy/uk/co/mdc/model/xxxxxxMarshaller.groovy
				
		render model as JSON
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
	
	
	String getSortField(Integer column){
		
		def field
		
		switch(column){
			
			case 0:
				field = "refId"
			break
			
			case 1:
				field = "name"
			break
			
			case 2:
				field = "versionNo"
			break
			
			case 3:
				field = "isDraft"
			break
			
			case 4:
				field = "description"
			break
			
			default:
				field = "name"
			break
		}
		
		return field
		
	}
	
	
	
}
