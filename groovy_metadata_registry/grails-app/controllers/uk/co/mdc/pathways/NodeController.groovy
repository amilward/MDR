package uk.co.mdc.pathways

import grails.converters.JSON

import org.json.simple.JSONObject
import org.springframework.dao.DataIntegrityViolationException

class NodeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

   def nodeService
	
   /* **************************************************************************************
	* ************************************* GET NODE (JSON)***************************************
	* get node and return as Json
	********************************************************************************************* */
	
	def getNodeJSON(Long id){
		
		def nodeInstance = findInstance()
		def model 
		
		if (!nodeInstance) {
			model = [message: message(code: 'default.not.found.message', args: [message(code: 'Node.label', default: 'Node'), id])]
		
		 }else{
		 
		 	model = [nodeInstance: nodeInstance]
		 
		}

		render model as JSON
	}
	
	
	/* **************************************************************************************
	 * ************************************* CREATE NODE (JSON)***************************************
	 * create node and return as Json
	 ********************************************************************************************* */
	
	def createNodeFromJSON(){
	
		def data = request.JSON
		def model
		def newNode = data.nodeInstance
		
		def nodeInstance = nodeService.create(newNode)
		
		if(nodeInstance){
			model = [success: true, nodeId: nodeInstance.id, pathwaysModelVersion: nodeInstance.pathwaysModel.version, nodeVersion: nodeInstance.version, message: 'saved']
		}else{
		
			model = [success: false]
		
		}
		render model  as JSON
		
	}
	
	/* **************************************************************************************
	 * ************************************* UPDATE NODE (JSON)***************************************
	 * update node and return as Json
	 ********************************************************************************************* */
	
	def updateNodeFromJSON(){
		
		def data = request.JSON
		def model
		
		println(data)
		
		if(data?.nodeInstance?.id){
			
			def nodeInstance = findInstance(data.nodeInstance.id)

			if (!nodeInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'nodeInstance.label', default: 'Node'), nodeId])
				redirect(action: "list")
				return
			}
				
			
			def nodeVersion = data?.nodeInstance?.nodeVersionNo
				
			//check that we have the right version i.e. no one else has updated the form design whilst we have been
			 //looking at it
	 
			 if (nodeVersion != null) {
				 if (nodeInstance.version > nodeVersion) {
					 nodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
							   [message(code: 'formDesign.label', default: 'DataElement')] as Object[],
							   "Another user has updated this Node while you were editing")
					 model = [success: true, nodeId: nodeInstance.id, message: 'version number conflict, please reload page and try again']
					 render model  as JSON
				}
			 }
			 
			 nodeInstance = nodeService.update(nodeInstance, data.nodeInstance)
			 
						 if (nodeInstance.errors.hasErrors()) {
							 def responseMessage = [errors: true, details: nodeInstance.errors]
							 response.status = 400
							 render responseMessage as JSON
							 return
						 }
			 
					
			model = [success: true, nodeId: nodeInstance.id, nodeVersion: nodeInstance.version, message: 'saved']
		
		}else{
			
			 model = [errors: true, details: 'no id included']
			
		}
		
		render model  as JSON
	}
	
	
	/* **************************************************************************************
	 * ************************************* DELETE NODE (JSON)***************************************
	 * update node and return as Json
	 ********************************************************************************************* */
	
	def deleteNode(Long id){
		
		def nodeInstance = findInstance()
		
		def model
		def msg
		
		if (!nodeInstance) {
			msg = message(code: 'default.not.found.message', args: [message(code: 'node.label', default: 'Node'), id])
			model = [success: false, message: msg]
		}else{

			try {
				nodeService.delete(nodeInstance)
				msg = message(code: 'default.deleted.message', args: [message(code: 'node.label', default: 'Node'), id])
				model = [success: true, message: msg]
			}
			catch (DataIntegrityViolationException e) {
				msg = message(code: 'default.not.found.message', args: [message(code: 'node.label', default: 'Node'), id])
				model = [success: false, message: msg]
			}
		}
		render model as JSON
		
	}
	
	
	/* **************************************************************************************
	 * ********************************* GRANT *************************************************
	 * this function grant permission to the given node
	 *********************************************************************************** */


	def grant = {

		def node = findInstance()

		if (!node) return

			if (!request.post) {
				return [nodeInstance: node]
			}

		nodeService.addPermission(node, params.recipient, params.int('permission'))

		redirectShow "Permission $params.permission granted on Report $node.id " + "to $params.recipient", node.id
	}

	/* **********************************************************************************
	 * this function uses the node service to get the node so that
	 * the appropriate security considerations are adhered to
	 *********************************************************************************** */

	private Node findInstance() {
		def node = nodeService.get(params.long('id'))
		if (!node) {
			flash.message = "Node not found with id $params.id"
			redirect action: list
		}
		node
	}

	private Node findInstance(id) {
		def node = nodeService.get(id)
		if (!node) {
			flash.message = "Node not found with id $params.id"
			redirect action: list
		}
		node
	}




	
	
	
	
	
	/* FIXME these need to be removed - keeping them in for testing purposes*/

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
		
		println(params)
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

			
			def sources  = Link.findAllWhere(source: nodeInstance)
			
			println('removing link sources and targets')
			
			sources.each{ link ->
				
				link.delete(flush:true,failOnError:true)
			}
			
			//targets
			def targets = Link.findAllWhere(target: nodeInstance)
			
			targets.each{ link->
				
				link.delete(flush:true,failOnError:true)
				
			}
			
			
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
