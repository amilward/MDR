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


	def getNodes(Long id){
		def pathwaysModelInstance = findInstance(id);
		def model
		if(pathwaysModelInstance){
			def nodes = pathwaysModelInstance.getNodes()
			
			model = [success: true, nodes: nodes]
		
		}else{
			
			 model = [errors: true, details: 'no model for this id included']
		}
		
		
		render model as JSON
	}
	
	def saveREST() {
		def unvalidated = request.JSON
		def pathway = [name: unvalidated.name, description: unvalidated.description, versionNo: unvalidated.version, isDraft: unvalidated.isDraft]
		
		//FIXME validate
		def pathwaysModelInstance = pathwaysService.create(pathway)
		
		//println pathwaysModelInstance.errors
		if (pathwaysModelInstance.errors.hasErrors()) {
			def responseMessage = [errors: true, details: pathwaysModelInstance.errors]
			response.status = 400
			render responseMessage as JSON
			return
		}

		render pathwaysModelInstance as JSON
		
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

	
	
    def show() {
		
		//if the entry point is from create pathways modal create a pathways modal first
		if(params.name){
			
			if(params.isDraft==null){
				params.isDraft = false
			}
			
			def pathwaysModelInstance = pathwaysService.create(params)
			
			if(pathwaysModelInstance.save(failOnError:true, flush:true)){
				redirect(action: "show", id: pathwaysModelInstance.id)
			}else{
				redirect(action: "list")
			}
			
			//else show the pathway
			
		}else{
		
	        [id: params.long('id')]
		}
    }
	
	
	def createPathwayFromJSON(){
		
			def data = request.JSON
			def model
			
			def pathwayInstance = pathwaysService.create(data)
			
			if(pathwayInstance){
				model = [success: true, pathwayId: pathwayInstance.id, pathwayVersion: pathwayInstance.version, message: 'saved']
			}else{
			
				model = [success: false]
			
			}
			render model  as JSON
			
		}
	
	
	def updatePathwayJSON(){
		def data = request.JSON
		def model
		
		if(data?.id){
			
			def pathwayInstance = findInstance(data?.id)

			if (!pathwayInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'pathwayInstance.label', default: 'Node'), data?.id])
				redirect(action: "list")
				return
			}
				
			def pathwayVersion = data?.version

			//check that we have the right version i.e. no one else has updated the form design whilst we have been
			 //looking at it
	 
			 if (pathwayVersion != null) {
				 if (pathwayInstance.version > pathwayVersion) {
					 pathwayInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
							   [message(code: 'formDesign.label', default: 'DataElement')] as Object[],
							   "Another user has updated this Node while you were editing")
					 model = [success: true, pathwayId: pathwayInstance.id, message: 'version number conflict, please reload page and try again']
					 render model  as JSON
				}
			 }
			 
			 pathwayInstance = pathwaysService.update(pathwayInstance, data)
			 
						 if (pathwayInstance.errors.hasErrors()) {
							 def responseMessage = [errors: true, details: pathwayInstance.errors]
							 response.status = 400
							 render responseMessage as JSON
							 return
						 }
			 
					
			model = [success: true, pathwayId: pathwayInstance.id, pathwayVersion: pathwayInstance.version, message: 'saved']
		
		}else{
			
			 model = [errors: true, details: 'no id included']
			
		}
		
		render model  as JSON
		
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
	
	
	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	 * this function grant permission to the given node
	 *********************************************************************************** */


	def grant = {

		def pathway = findInstance()

		if (!pathway) return

			if (!request.post) {
				return [pathwayInstance: pathway]
			}

		pathwaysService.addPermission(pathway, params.recipient, params.int('permission'))

		redirectShow "Permission $params.permission granted on Report $pathway.id " + "to $params.recipient", pathway.id
	}

	/* **********************************************************************************
	 * this function uses the pathway service to get the pathway so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */

	private PathwaysModel findInstance() {
		def pathway = pathwaysService.get(params.long('id'))
		if (!pathway) {
			flash.message = "Pathway not found with id $params.id"
			redirect action: list
		}
		pathway
	}

	private PathwaysModel findInstance(id) {
		def pathway = pathwaysService.get(id)
		if (!pathway) {
			flash.message = "Pathway not found with id $params.id"
			redirect action: list
		}
		pathway
	}
	
	
	String getSortField(Integer column){
		
		def field
		
		switch(column){
			
			case 0:
				field = "name"
			break
			
			case 1:
				field = "versionNo"
			break
			
			case 2:
				field = "isDraft"
			break
			
			case 3:
				field = "description"
			break
			
			default:
				field = "name"
			break
		}
		
		return field
		
	}
	
	
	/* def create() {
	 
	 
	 if(params.isDraft==null){
		 params.isDraft = false
	 }
	 
	 def pathwaysModelInstance = pathwaysService.create(params)
	 
	 if(pathwaysModelInstance.save(failOnError:true, flush:true)){
		 redirect(action: "show", id: pathwaysModelInstance.id)
	 }else{
		 redirect(action: "list")
	 }
	 
	 
 }*/
	
	/*
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
	 }*/
 
	
	
	
}
