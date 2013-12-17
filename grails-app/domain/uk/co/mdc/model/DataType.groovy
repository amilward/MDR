package uk.co.mdc.model

class DataType extends ExtensibleObject  {
	
	String name
	Boolean enumerated
	Map enumerations

	static auditable = true
	
	static searchable = {
        content: spellCheck 'include'
    } 
	
    static constraints = {
		enumerations nullable: true
		name blank: false
    }
	
	static hasMany = {valueDomains: ValueDomain}
}
