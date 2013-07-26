package uk.co.mdc.model

import java.util.List;

class ValueDomain {
	
	String name

	Integer refId

	String unitOfMeasure
	
	String regexDef
	
	String description
	
	DataType dataType
 	
	Set dataElementValueDomains = []
	
	static searchable = true
	
	static hasMany = [dataElementValueDomains: DataElementValueDomain]
	
	static belongsTo = [conceptualDomain: ConceptualDomain]
	
    static constraints = {
		refId unique: true
		conceptualDomain nullable:true
		dataType nullable:true
    }
	
	
	/******************************************************************************************************************/
	/**************functions for linking data elements and value domains using dataElementValueDomains class*************************/
	/******************************************************************************************************************/
	
	
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
	
	
	/******************************************************************************************************************/
	/*********************remove all the associated valueDomains and collections before deleting data element*****************************/
	/******************************************************************************************************************/
	
	def prepareForDelete(){
		if(this.dataElementValueDomains.size()!=0){
			
			def dataForDelete = this.dataElementValueDomains()
			
			dataForDelete.each{ dataElement->
				this.removeFromDataElementValueDomains(dataElement)
			}
		}
	}
	
}
