package uk.co.mdc.model

import java.util.List;
import java.util.Set;

class Collection {
	
	String refId
	 
	String name
	 
	String description
	
	Set dataElementCollections = []
	 
	static hasMany = [dataElementCollections: DataElementCollection]
	 
    static constraints = {
		refId unique: true
		name blank: false
    }
	
	static mapping = {
		description type: 'text'
	}
	
	/******************************************************************************************************************/
	/**************functions for linking data elements and value domains using dataElementValueDomains class*************************/
	/******************************************************************************************************************/
	
	
	List dataElementCollections() {
		return dataElementCollections.collect{it.dataElement}
	}
	
	List mandatoryDataElementCollections() {
		
		def mandatoryDataElementCollection = dataElementCollections.find{it.schemaSpecification==SchemaSpecification.MANDATORY}
		
		if(mandatoryDataElementCollection){
			return mandatoryDataElementCollection.collect{it.dataElement}
		}else{
			return null
		}
		
	}
	

	List requiredDataElementCollections() {
		
		def requiredDataElementCollection = DataElementCollection.findAll{collection==this && schemaSpecification == SchemaSpecification.REQUIRED}
		
		if(!requiredDataElementCollection.isEmpty()){
			return requiredDataElementCollection.collect{it.dataElement}
		}else{
			return null
		}
	}
	
	List optionalDataElementCollections() {
				
		def optionalDataElementCollection = DataElementCollection.findAll{collection==this && schemaSpecification == SchemaSpecification.OPTIONAL}
		
		if(!optionalDataElementCollection.isEmpty()){
			return optionalDataElementCollection.collect{it.dataElement}
		}else{
			return null
		}
	}
	
	List referenceDataElementCollections() {
				
		def referenceElementCollection = DataElementCollection.findAll{collection==this && schemaSpecification == SchemaSpecification.X}
		
		if(!referenceElementCollection.isEmpty()){
			return referenceElementCollection.collect{it.dataElement}
		}else{
			return null
		}
	}	
	
	//add a dataElement to list 
	
	List addToDataElementCollections(DataElement dataElement) {
		DataElementCollection.link(dataElement, this)
		return dataElementCollections()
	}

	//remove a dataElement from list
	
	List removeFromDataElementCollections(DataElement dataElement) {
		
		DataElementCollection.unlink(dataElement, this)
		return dataElementCollections()
	}
	
	/******************************************************************************************************************/
	/*********************remove all the associated valueDomains and collections before deleting data element*****************************/
	/******************************************************************************************************************/
	
	def prepareForDelete(){
				
		if(this.dataElementCollections.size()!=0){
			
			def dataForDelete = this.dataElementCollections()
			
			dataForDelete.each{ dataElement->
				this.removeFromDataElementCollections(dataElement)
			}
		}
	}
	
	
}
