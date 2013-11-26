package uk.co.mdc.model

class DataElementConcept extends ExtensibleObject  {
	
	String name
	String description
	DataElementConcept parent
	
	static auditable = true
	
	static searchable = {
        content: spellCheck 'include'
    } 
	
	static hasMany = [subConcepts: DataElementConcept, dataElements: DataElement]
	
	static belongsTo = [parent: DataElementConcept]
	
    static constraints = {
		name blank: false
		parent nullable: true
		description nullable: true
		
		}
	
	static mapping = {
		description type: 'text'
		dataElements cascade: 'save-update'
		subConcepts cascade: 'save-update'
	}
	
	/******************************************************************************************************************/
	/*********************remove all the associated data elements before deleting**************************************/
	/******************************************************************************************************************/
	
	
	def prepareForDelete(){
		
		def dataForDelete = []
		
		if(this.dataElements.size()!=0){
			
			dataForDelete.addAll(this.dataElements)
			
			dataForDelete.each{ dataElement->
				this.removeFromDataElements(dataElement)
			}
		}
	}
	
}
