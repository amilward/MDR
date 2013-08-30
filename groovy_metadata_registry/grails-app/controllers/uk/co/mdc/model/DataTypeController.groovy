package uk.co.mdc.model

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class DataTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        [dataTypeInstanceTotal: DataType.count()]
    }
	
	def dataTables(){

		def data
		def total
		def displayTotal
		def order
		def sortCol
		

		if(params?.sSearch!='' && params?.sSearch!=null){
			
			def searchResults = DataType.search(params.sSearch, [max:params.iDisplayLength])
			
			total = searchResults.total
			displayTotal = searchResults.total
			
			if(total>0){
				data = searchResults.results
			}else{
				data=[]
			}
			
			
			
		}else{
		
			order = params?.sSortDir_0
			sortCol = "name"
			
			data = DataType.list(max: params.iDisplayLength, offset: params.iDisplayStart, sort: sortCol, order: order)
			total = DataType.count()
			displayTotal = DataType.count()
			
		}
		
		

		def model = [sEcho: params.sEcho, iTotalRecords: total, iTotalDisplayRecords: displayTotal, aaData: data]
		
		render model as JSON
	}

    def create() {
        [dataTypeInstance: new DataType(params)]
    }

    def save() {
		
		params.enumerations = getEnumerations()

        def dataTypeInstance = new DataType(params)
		
        if (!dataTypeInstance.save(flush: true)) {
            render(view: "create", model: [dataTypeInstance: dataTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'dataType.label', default: 'DataType'), dataTypeInstance.id])
        redirect(action: "show", id: dataTypeInstance.id)
    }

    def show(Long id) {
        def dataTypeInstance = DataType.get(id)
        if (!dataTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataType.label', default: 'DataType'), id])
            redirect(action: "list")
            return
        }

        [dataTypeInstance: dataTypeInstance]
    }

    def edit(Long id) {
        def dataTypeInstance = DataType.get(id)
        if (!dataTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataType.label', default: 'DataType'), id])
            redirect(action: "list")
            return
        }

        [dataTypeInstance: dataTypeInstance]
    }

    def update(Long id, Long version) {
		
        def dataTypeInstance = DataType.get(id)
        if (!dataTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataType.label', default: 'DataType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (dataTypeInstance.version > version) {
                dataTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'dataType.label', default: 'DataType')] as Object[],
                          "Another user has updated this DataType while you were editing")
                render(view: "edit", model: [dataTypeInstance: dataTypeInstance])
                return
            }
        }
		
		
		
		params.enumerations = getEnumerations()
		
		if(params.enumerations==null){
			params.enumerations= new HashMap()
		}
		
        dataTypeInstance.properties = params

        if (!dataTypeInstance.save(flush: true)) {
            render(view: "edit", model: [dataTypeInstance: dataTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'dataType.label', default: 'DataType'), dataTypeInstance.id])
        redirect(action: "show", id: dataTypeInstance.id)
    }

    def delete(Long id) {
        def dataTypeInstance = DataType.get(id)
        if (!dataTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataType.label', default: 'DataType'), id])
            redirect(action: "list")
            return
        }

        try {
            dataTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'dataType.label', default: 'DataType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dataType.label', default: 'DataType'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def removeEnumeratedValue(){
		DataType dataType = DataType.get(params.dataTypeId)
		
		if(dataType){
			dataType.enumerations.remove(params.enumeratedValue)
		}
		
		redirect(action: 'edit', id: params.dataTypeId)

	}

	
	def getEnumerations(){

		if(params?.enumerated=='on'){
		
			if(params?.map_key){
				
				Map enumerations = new HashMap()
				def counter = 0
				
				//if there is more than one enumeration
				if(params.map_key.class.isArray()){
					
					//iterate through values and insert them into map
					params?.map_key?.each{ val->
						
						
						if(val!=''){
							
							def desc = ''
							desc = params?.map_value[counter]
							
							enumerations.put(val, desc)
							
						}
						
						counter++
					}
				
				}else{

					enumerations.put(params.map_key, params?.map_value)
					
				}
		
				return enumerations
			}
		}else{
			String empty = ''
			return empty
		}
		
		
	}	
	
}
