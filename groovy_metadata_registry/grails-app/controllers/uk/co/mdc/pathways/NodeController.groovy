package uk.co.mdc.pathways

import grails.converters.JSON
import org.json.simple.JSONObject
import org.springframework.dao.DataIntegrityViolationException

class NodeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
	
	
	
	def getNodeJSON(Long id){
		
		def nodeInstance = Node.get(id)
		def model 
		
		if (!nodeInstance) {
		 model = [message: message(code: 'default.not.found.message', args: [message(code: 'Node.label', default: 'Node'), id])]
		
		 }else{
		 
		 model = [nodeInstance: nodeInstance]
		 
		}

		render model as JSON
	}
	
	def createNodeFromJSON(){
	
		def data = request.JSON
		
		def newNode = data.nodeInstance
		
		println('new node')
		println(newNode)
		
		def nodeInstance = new Node(
			refId: newNode?.refId,
			name: newNode?.name,
			x: newNode?.x,
			y: newNode?.y,
			description: newNode?.description
			)

		if (!nodeInstance.save(flush: true)) {
			println(nodeInstance.errors)
		}
		
		def pathway = PathwaysModel.get(1)
		
		pathway.addToPathwayElements(nodeInstance)
		
		
		def model = [success: true, nodeId: nodeInstance.id, nodeVersion: nodeInstance.version, message: 'saved']
		
		render model  as JSON
		
	}
	
	def updateNodeFromJSON(){
		
		def data = request.JSON
		
		def nodeId = data.nodeInstance.id
		
		def nodeVersion = data.nodeInstance.nodeVersionNo
		
		def nodeInstance = Node.get(nodeId)
		
		println(nodeInstance)
			
		if (!nodeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'nodeInstance.label', default: 'Node'), nodeId])
			redirect(action: "list")
			return
		}
			
			
		//check that we have the right version i.e. no one else has updated the form design whilst we have been
		 //looking at it
 
		 if (nodeVersion != null) {
			 if (nodeInstance.version > nodeVersion) {
				 nodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						   [message(code: 'formDesign.label', default: 'DataElement')] as Object[],
						   "Another user has updated this Node while you were editing")
				 def model = [success: true, nodeId: nodeInstance.id, message: 'version number conflict, please reload page and try again']
				 render model  as JSON
			}
		 }
		 
		println('nodeprops') 		 
		println(nodeInstance.properties)
		
		println('nodeprops')
		println(data.nodeInstance)
		 
		nodeInstance.properties = data.nodeInstance

        if (!nodeInstance.save(flush: true)) {
            println('failure')
        }

	
		def model = [success: true, nodeId: nodeInstance.id, nodeVersion: nodeInstance.version, message: 'saved']
		
		render model  as JSON
	}
	
	def deleteNode(Long id){
		
		println(id)
		
		def nodeInstance = Node.get(id)
		
		println(nodeInstance)
		
		def model
		def msg
		
		if (!nodeInstance) {
			msg = message(code: 'default.not.found.message', args: [message(code: 'node.label', default: 'Node'), id])
			model = [success: false, message: msg]
		}else{

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
