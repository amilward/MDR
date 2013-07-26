package uk.co.mdc.model

import org.springframework.dao.DataIntegrityViolationException

class DataElementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [dataElementInstanceList: DataElement.list(params), dataElementInstanceTotal: DataElement.count()]
    }

    def create() {
        [valueDomains: ValueDomain.list(), dataElements: DataElement.list(), dataElementInstance: new DataElement(params)]
    }

    def save() {
       
		
		//validate the parent child relationship
		
		Boolean valid = validateDataElement()
		
		if(!valid){
			render(view: "create", model: [valueDomains: ValueDomain.list(), dataElements: DataElement.list(), dataElementInstance: new DataElement(params)])
			return
		}

		//save the dataElement
		
		DataElement dataElementInstance = new DataElement(params)
		
        if (!dataElementInstance.save(flush: true)) {
            render(view: "create", model: [dataElementInstance: dataElementInstance, valueDomains: ValueDomain.list(), dataElements: DataElement.list()])
            return
        }
		
		//link selected value domains with  data element
		
		linkValueDomains(dataElementInstance)
		
		//redirect with message

        flash.message = message(code: 'default.created.message', args: [message(code: 'dataElement.label', default: 'DataElement'), dataElementInstance.id])
        redirect(action: "show", id: dataElementInstance.id, model: [valueDomains: ValueDomain.list()])
    }

    def show(Long id) {
        def dataElementInstance = DataElement.get(id)
        if (!dataElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "list")
            return
        }

        [dataElementInstance: dataElementInstance]
    }

    def edit(Long id) {
        def dataElementInstance = DataElement.get(id)
        if (!dataElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "list")
            return
        }

        [valueDomains: ValueDomain.list(), dataElements: DataElement.list(), dataElementInstance: dataElementInstance]
    }

    def update(Long id, Long version) {
		
		//validate the update a params
		
		Boolean valid = validateDataElement()
		
		if(!valid){
			redirect(action: "edit", id: params.id)
			return
		}
		
		//save the updates
		
        def dataElementInstance = DataElement.get(id)
        if (!dataElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (dataElementInstance.version > version) {
                dataElementInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'dataElement.label', default: 'DataElement')] as Object[],
                          "Another user has updated this DataElement while you were editing")
                render(view: "edit", model: [dataElementInstance: dataElementInstance,valueDomains: ValueDomain.list(), dataElements: DataElement.list()])
                return
            }
        }

        dataElementInstance.properties = params

        if (!dataElementInstance.save(flush: true)) {
            render(view: "edit", model: [dataElementInstance: dataElementInstance],valueDomains: ValueDomain.list(), dataElements: DataElement.list())
            return
        }
		
		
		linkValueDomains(dataElementInstance)
	
        flash.message = message(code: 'default.updated.message', args: [message(code: 'dataElement.label', default: 'DataElement'), dataElementInstance.id])
        redirect(action: "show", id: dataElementInstance.id)
    }

    def delete(Long id) {
        def dataElementInstance = DataElement.get(id)
        if (!dataElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "list")
            return
        }

        try {
			dataElementInstance.prepareForDelete()
            dataElementInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dataElement.label', default: 'DataElement'), id])
            redirect(action: "show", id: id)
        }
    }
	
	
	def removeValueDomain() {
		ValueDomain valueDomain = ValueDomain.get(params.valueDomainId)
		DataElement dataElement = DataElement.get(params.dataElementId)
		if(valueDomain && dataElement){
			dataElement.removeFromDataElementValueDomains(valueDomain)
		}
		redirect(action: 'edit', id: params.dataElementId)
	}
	
	
	def removeSubElement(){
		DataElement element = DataElement.get(params.elementId)
		DataElement subElement = DataElement.get(params.subElementId)
		
		if(element && subElement){
			element.removeFromSubElements(subElement)
		}
		
		redirect(action: 'edit', id: params.elementId)
	}
	
	
	
	def linkValueDomains(dataElementInstance){
		
		def valueDomains = params.valueDomains
		if(valueDomains!=null){
			
			if (valueDomains instanceof String) {
				ValueDomain valueDomain =  ValueDomain.get(valueDomains)
				if(valueDomain){
					DataElementValueDomain.link(dataElementInstance, valueDomain)
				}
			}
			
			if (valueDomains instanceof String[]) {
				  for (valueDomainID in valueDomains){
					  ValueDomain valueDomain =  ValueDomain.get(valueDomainID)
					  if(valueDomain){
							DataElementValueDomain.link(dataElementInstance, valueDomain)
						}
				  }
			}

		}
	}
		
	Boolean validateDataElement(){
		
		
		ArrayList children
		
		//check if subelements contain the given element
		
		if(params?.subElements!=null && params?.id!=null){
			
			children = getChildren()
			
			if(children.contains(params.id)){
				params.subElements = ''
				flash.message = 'Error: Sub elements must not contain the element itself'
				return false
			}

		}
		
		//check if parent elements contain the given element
		
		if(!params?.parent?.id.isEmpty() &&  params?.id!=null){
			if(params.parent.id == params.id){
				params.parent = ''
				flash.message = 'Error: Parent elements must not contain the element itself'
				return false
			}

		}
		
		//check if subelements contain the parent element
		
		if(params?.subElements!=null && params?.parent?.id!=null){
			
			if(children==null){
				children = getChildren()
			}
			
			if(!validateChildParentRelationship(params.parent.id.value.toString(), children)){
				params.subElements = ''
				flash.message = 'Error: Sub elements must not contain the parent element'
				return false
			}
		}
		return true
	}

	
	Boolean validateChildParentRelationship(String parent, ArrayList children){
		
		if(children.contains(parent)){
			return false
		}
		
		return true
	}
	
	
	List getChildren(){
		
		List children = new ArrayList()
		
		if(params.subElements.class.isArray()){
			children.addAll(params.subElements)
		}else{
			children.add(params.subElements.value.toString())
		}
		
		return children
		
	}
	
}
