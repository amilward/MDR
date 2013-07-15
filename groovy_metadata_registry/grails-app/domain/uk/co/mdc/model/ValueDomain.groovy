package uk.co.mdc.model

import java.util.List;

class ValueDomain {
	
	String name

	Integer refId

	String unitOfMeasure
	
	String regexDef
	
	String description
	
	String dataType
	
	Set dataElementValueDomains = []
	
	static hasMany = [dataElementValueDomains: DataElementValueDomain]
	
	static belongsTo = [conceptualDomain: ConceptualDomain]
	
    static constraints = {
		refId unique: true
		conceptualDomain nullable:true
    }
	
	
	List dataElementValueDomains() {
		return dataElementValueDomains.collect{it.dataElement}
	}

	//add a dataElement to list of dataElements who have signed up the game
	
	List addToDataElementValueDomains(DataElement dataElement) {
		DataElementValueDomain.link(dataElement, this)
		return dataElementValueDomains()
	}

	//remove a dataElement from list of dataElements who have signed up to the game
	
	List removeFromDataElementValueDomains(DataElement dataElement) {
		
		DataElementValueDomain.unlink(dataElement, this)
		return dataElementValueDomains()
	}
	
	def removeDataElement() {
		ValueDomain valueDomain = ValueDomain.get(params.valueDomainId)
		DataElement dataElement = DataElement.get(params.dataElementId)
		if(valueDomain && dataElement){
			dataElement.removeFromDataElementValueDomains(valueDomain)
		}
		redirect(action: 'edit', id: params.dataElementId)
	}
	
	def prepareForDelete(){
		if(this.dataElementValueDomains.size()!=0){
			this.dataElementValueDomains.each{ p->
				this.removeFromDataElementValueDomains(p.dataElement)
			}
		}
	}
	
}
