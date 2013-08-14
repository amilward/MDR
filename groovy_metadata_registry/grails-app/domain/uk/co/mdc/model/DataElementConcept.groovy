package uk.co.mdc.model

class DataElementConcept {
	
	String refId
	
	String name
	
	String description
	
	DataElementConcept parent
	
	static searchable = {
        content: spellCheck 'include'
    } 
	
	static hasMany = [subConcepts: DataElementConcept, dataElements: DataElement]
	
	static belongsTo = [parent: DataElementConcept]
	
    static constraints = {
		name blank: false
		parent nullable: true
		refId unique: true, nullable:true
		description nullable: true
		
		}
	
	static mapping = {
		description type: 'text'
	}
}
