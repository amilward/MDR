package uk.co.mdc.model

import org.springframework.dao.DataIntegrityViolationException

class ValueDomainController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [valueDomainInstanceList: ValueDomain.list(params), valueDomainInstanceTotal: ValueDomain.count()]
    }

    def create() {
        [dataElements: DataElement.list(), dataTypes: DataType.list(), valueDomainInstance: new ValueDomain(params)]
    }

    def save() {
		
		
		if(params?.dataType){
			DataType dataType = DataType.get(params?.dataType)
			params.dataType = dataType
		}
        def valueDomainInstance = new ValueDomain(params)
        if (!valueDomainInstance.save(flush: true)) {
            render(view: "create", model: [valueDomainInstance: valueDomainInstance])
            return
        }
		
		linkDataElements(valueDomainInstance)
		

        flash.message = message(code: 'default.created.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), valueDomainInstance.id])
        redirect(action: "show", id: valueDomainInstance.id, model: [dataElements: DataElement.list()])
    }

    def show(Long id) {
        def valueDomainInstance = ValueDomain.get(id)
        if (!valueDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
            redirect(action: "list")
            return
        }

        [valueDomainInstance: valueDomainInstance]
    }

    def edit(Long id) {
        def valueDomainInstance = ValueDomain.get(id)
        if (!valueDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
            redirect(action: "list")
            return
        }

        [dataElements: DataElement.list(), dataTypes: DataType.list(), valueDomainInstance: valueDomainInstance]
    }

    def update(Long id, Long version) {
        def valueDomainInstance = ValueDomain.get(id)
        if (!valueDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (valueDomainInstance.version > version) {
                valueDomainInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'valueDomain.label', default: 'ValueDomain')] as Object[],
                          "Another user has updated this ValueDomain while you were editing")
                render(view: "edit", model: [dataElements: DataElement.list(), dataTypes: DataType.list(), valueDomainInstance: valueDomainInstance])
                return
            }
        }

		if(params?.dataType){
			DataType dataType = DataType.get(params?.dataType)
			params.dataType = dataType
		}
		
        valueDomainInstance.properties = params

        if (!valueDomainInstance.save(flush: true)) {
            render(view: "edit", model: [dataElements: DataElement.list(), dataTypes: DataType.list(), valueDomainInstance: valueDomainInstance])
            return
        }

	
		linkDataElements(valueDomainInstance)

        flash.message = message(code: 'default.updated.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), valueDomainInstance.id])
        redirect(action: "show", id: valueDomainInstance.id)
    }

    def delete(Long id) {
        def valueDomainInstance = ValueDomain.get(id)
		
        if (!valueDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
            redirect(action: "list")
            return
        }

        try {
			
			valueDomainInstance.prepareForDelete()
			
            valueDomainInstance.delete(flush: true)
			
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def removeDataElement() {
		ValueDomain valueDomain = ValueDomain.get(params.valueDomainId)
		DataElement dataElement = DataElement.get(params.dataElementId)
		if(valueDomain && dataElement){
			valueDomain.removeFromDataElementValueDomains(dataElement)
		}
		redirect(action: 'edit', id: params.valueDomainId)
	}
	
	def linkDataElements(valueDomainInstance){
		def dataElements = params.dataElements
		if(dataElements!=null){
			
			if (dataElements instanceof String) {
				DataElement dataElement =  DataElement.get(dataElements)
				if(dataElement){
					DataElementValueDomain.link(dataElement, valueDomainInstance)
				}
			} else if (dataElements instanceof String[]) {
				  for (dataElementID in dataElements){
					  DataElement dataElement =  DataElement.get(dataElementID)
					  if(dataElement){
						  DataElementValueDomain.link(dataElement, valueDomainInstance)
					  }
				  }
			}

		}
	}
	
}
