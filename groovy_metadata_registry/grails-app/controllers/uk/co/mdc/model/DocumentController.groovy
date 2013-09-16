package uk.co.mdc.model

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

class DocumentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def documentService
	def MDRService
	
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
	 * to list all the documents
	 *************************************************************************************** */
	

    def list() {
        []
    }
	
	
	/* **************************************************************************************
	 * ********************************* DATA TABLES *************************************************
	
	 * this function is called when listing the documents. It is called through ajax
	 * using the the data tables plugin in the show.gsp view and the javascript that
	 * calls the code is in main.js
	 *********************************************************************************** */

	def dataTables(){
		
		// set the variables needed to pass back to the data tables plugin to render the documents
		
		def data
		def total
		def displayTotal
		def order
		def sortCol
		def sortColName
		
		//if the user searches for a document return the search results using the document service
		
		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = documentService.search(params.sSearch)
			
			total = searchResults.size()
			displayTotal = searchResults.size()
			
			if(total>0){
				data = searchResults
			}else{
				data=[]
			}
			
			//otherwise list the documents using the documents service and pass the relevant data
			//back to the data tables plugin request as json
			
		}else{
		
			order = params?.sSortDir_0
			sortColName = getSortField(params?.iSortCol_0.toInteger())
			
			data = Document.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortColName, order: order)
			total = Document.count()
			displayTotal = Document.count()
			
		}
		
		
		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
		
		//NB. when the json is rendered it uses a custom json marshaller so that it includes the relevant
		//information (and doesn't return the WHOLE database due to all the links)
		//the corresponding json marshaller is stored in src/groovy/uk/co/mdc/model/xxxxxxMarshaller.groovy
			
		render model as JSON
	}
	
	
	/* **************************************************************************************
	 * *********************************** SHOW *****************************************************
	
	 * show the conceptual domain in question using the find instance function and the dataType service
	 * ...presuming they have the appropriate permissions
	 *************************************************************************************** */
	
	def show(Long id) {
		
		//use the find instance method to get the document in question
		def documentInstance = findInstance()
		
		//if you can't find it or don't have permission go back to the list page otherwise show the document
		if (!documentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'document.label', default: 'Document'), id])
			redirect(action: "list")
			return
		}

		[documentInstance: documentInstance]
	}
	
	/* **************************************************************************************
	 * ************************************* CREATE ***************************************************
	 
	 * renders the document template so the user can create a document
	 * N.B additionally will only display objects that user has permission to read
	 *************************************************************************************** */
	
	def create() {
		[documentInstance: new Document(params)]
	}
	
	/* **************************************************************************************
	 * ************************************ SAVE ****************************************************
	 
	 * calls the document service to create a document . The service sets admin permissions
	 * on that object for the user in question and creates the document
	 *************************************************************************************** */
	
	
    def save() {
		
		
		/* *****
		 * create the document using the data type service
		 ******* */

		def documentInstance = documentService.create(params)
		
		
		/* ******
		 * check that the document has been created without errors and render accordingly
		 ***** */
		
		if (!renderWithErrors('create', documentInstance)) {
			redirectShow(message(code: 'default.created.message', args: [message(code: 'document.label', default: 'document'), documentInstance.id]), documentInstance.id)
		}
		
    }

	/* **************************************************************************************
	 * ************************************** EDIT ******************************************
	
	 * this function redirects to the edit document screen
	 ************************************************************************************** */

    def edit(Long id) {
		
		//get the document (redirect if you can't)
        def documentInstance = findInstance()
        if (!documentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'document.label', default: 'Document'), id])
            redirect(action: "list")
            return
        }

        [documentInstance: documentInstance]
    }
	
	
	/* **************************************************************************************
	 * ************************************ UPDATE ******************************************
	
	 * this function updates the document using the document service
	 *************************************************************************************** */

    def update(Long id, Long version) {
		
		//get the document (redirect if you can't)

        def documentInstance = findInstance()
        if (!documentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'document.label', default: 'Document'), id])
            redirect(action: "list")
            return
        }
		
		//check that we have the right version i.e. no one else has updated the document
		//whilst we have been looking at it

        if (version != null) {
            if (documentInstance.version > version) {
                documentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'document.label', default: 'Document')] as Object[],
                          "Another user has updated this Document while you were editing")
                render(view: "edit", model: [documentInstance: documentInstance])
                return
            }
        }
		
		
		//update the document using the document service
		
		documentService.update(documentInstance, params)
		
		if (!renderWithErrors('edit', documentInstance)) {
			redirectShow message(code: 'default.updated.message', args: [message(code: 'document.label', default: 'Document'), documentInstance.id]), documentInstance.id
		}


    }
	
	/* **************************************************************************************
	 * ********************************* DELETE *************************************************
	
	 * this function deletes the document using the document service
	 *********************************************************************************** */

    def delete(Long id) {
		
		//get the document and redirect if it doesn't exist
        def documentInstance = findInstance()
        if (!documentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'document.label', default: 'Document'), id])
            redirect(action: "list")
            return
        }

		//call the document service  to delete the document
        try {
            documentService.delete(documentInstance)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'document.label', default: 'Document'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'document.label', default: 'Document'), id])
            redirect(action: "show", id: id)
        }
    }
	
	
	def download(Long id) {
		def document = findInstance()
		
		if(document){
			def docName  = document.name + "." + document.contentType
			
			//Whatever your content type is
			response.setContentType("application-xdownload")
			response.setHeader("Content-disposition", "attachment;filename=${docName}")
			response.outputStream << document.content
		}
		return
	}
	
	
	
	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	
	 * this function grant permission to the given document
	 *********************************************************************************** */
	
	
	def grant = {
		
				def document = findInstance()
				
				if (!document) return
		
				if (!request.post) {
					return [documentInstance: document]
				}
		
				documentService.addPermission(document, params.recipient, params.int('permission'))
		
				redirectShow "Permission $params.permission granted on Report $document.id " + "to $params.recipient", document.id
			}
	
	/* **********************************************************************************
	 * this function uses the document service to get the document so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */
	
	private Document findInstance() {
		def document = documentService.get(params.long('id'))
		if (!document) {
			flash.message = "Document not found with id $params.id"
			redirect action: list
		}
		document
	}
	
	/* **********************************************************************************
	 * this function redirects to the show document screen
	 *********************************************************************************** */
	
	private void redirectShow(message, id) {
		flash.message = message
		//redirect with message
				
		redirect(action: "show", id: id)
	}
	
	/* **********************************************************************************
	 * this function checks to see if the document passed to it contains errors i.e. when a
	 * service returns the element. It either returns false (if no errors) or it redirects
	 * to the view specified by the caller
	 *********************************************************************************** */

	private boolean renderWithErrors(String view, Document document) {
		if (document.hasErrors()) {
			render view: view, model: [documentInstance: document]
			return true
			
		}
		false
	}
	
	
	/*this function is used by the dataTables to map the columns to the data*/
	
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
				field = "description"
			break
			
			case 3:
				field = "fileName"
			break
			
			case 4:
				field = "contentType"
			break
			
			default:
				field = "refId"
			break
		}
		
		return field
		
	}
	
}
