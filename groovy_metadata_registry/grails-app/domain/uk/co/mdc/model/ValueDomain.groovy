package uk.co.mdc.model

import java.util.List;

class ValueDomain {
	
	String name

	Integer refId

	String unitOfMeasure
	
	String regexDef
	
	String description
	
	String dataType
	
	static hasMany = [dataElementValueDomains: DataElementValueDomain]
	
    static constraints = {
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
	
	//remove all the dataElements who have signed up to the game before deleting the game
	
	def beforeDelete() {
		
		if(dataElementValueDomains.size()!=0){
			dataElementValueDomains.each{ p->
				removeFromDataElementValueDomains(p.dataElement)
			}
		}
	  }
	
	def removeDataElement() {
		ValueDomain valueDomain = ValueDomain.get(params.valueDomainId)
		DataElement dataElement = DataElement.get(params.dataElementId)
		if(valueDomain && dataElement){
			dataElement.removeFromDataElementValueDomains(valueDomain)
		}
		redirect(action: 'edit', id: params.dataElementId)
	}
	
}
