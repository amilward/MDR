package uk.co.mdc.model

import org.springframework.dao.DataIntegrityViolationException

class ExternalSynonymController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [externalSynonymInstanceList: ExternalSynonym.list(params), externalSynonymInstanceTotal: ExternalSynonym.count()]
    }

    def create() {
        [externalSynonymInstance: new ExternalSynonym(params)]
    }

    def save() {
		
		params.attributes = getAttributes()
		
        def externalSynonymInstance = new ExternalSynonym(params)
        if (!externalSynonymInstance.save(flush: true)) {
            render(view: "create", model: [externalSynonymInstance: externalSynonymInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'externalSynonym.label', default: 'ExternalSynonym'), externalSynonymInstance.id])
        redirect(action: "show", id: externalSynonymInstance.id)
    }

    def show(Long id) {
        def externalSynonymInstance = ExternalSynonym.get(id)
        if (!externalSynonymInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalSynonym.label', default: 'ExternalSynonym'), id])
            redirect(action: "list")
            return
        }

        [externalSynonymInstance: externalSynonymInstance]
    }

    def edit(Long id) {
        def externalSynonymInstance = ExternalSynonym.get(id)
        if (!externalSynonymInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalSynonym.label', default: 'ExternalSynonym'), id])
            redirect(action: "list")
            return
        }

        [externalSynonymInstance: externalSynonymInstance]
    }

    def update(Long id, Long version) {
		
        def externalSynonymInstance = ExternalSynonym.get(id)
        if (!externalSynonymInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalSynonym.label', default: 'ExternalSynonym'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (externalSynonymInstance.version > version) {
                externalSynonymInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'externalSynonym.label', default: 'ExternalSynonym')] as Object[],
                          "Another user has updated this ExternalSynonym while you were editing")
                render(view: "edit", model: [externalSynonymInstance: externalSynonymInstance])
                return
            }
        }
		
		params.attributes = getAttributes()
		
		if(externalSynonymInstance.attributes){
			params.attributes.putAll(externalSynonymInstance.attributes)
		}

        externalSynonymInstance.properties = params

        if (!externalSynonymInstance.save(flush: true)) {
            render(view: "edit", model: [externalSynonymInstance: externalSynonymInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'externalSynonym.label', default: 'ExternalSynonym'), externalSynonymInstance.id])
        redirect(action: "show", id: externalSynonymInstance.id)
    }

    def delete(Long id) {
        def externalSynonymInstance = ExternalSynonym.get(id)
        if (!externalSynonymInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalSynonym.label', default: 'ExternalSynonym'), id])
            redirect(action: "list")
            return
        }

        try {
            externalSynonymInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'externalSynonym.label', default: 'ExternalSynonym'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'externalSynonym.label', default: 'ExternalSynonym'), id])
            redirect(action: "show", id: id)
        }
    }
	
	
	
	
	def removeAttribute(){
		ExternalSynonym externalSynonym = ExternalSynonym.get(params.externalSynonymId)
		
		if(externalSynonym){
			externalSynonym.attributes.remove(params.attribute)
		}
		
		redirect(action: 'edit', id: params.externalSynonymId)

	}

	
	def getAttributes(){
		
			if(params?.map_key){
				
				Map attributes = new HashMap()
				def counter = 0
				
				//if there is more than one enumeration
				if(params.map_key.class.isArray()){
					
					//iterate through values and insert them into map
					params?.map_key?.each{ val->
						
						
						if(val!=''){
							
							def desc = ''
							desc = params?.map_value[counter]
							
							attributes.put(val, desc)
							
						}
						
						counter++
					}
				
				}else{
					
					attributes.put(params.map_key, params?.map_value)
					
				}
		
				return attributes
			}else{
			
				String empty = ''
				return empty
			
			}

	}
	
	
}
