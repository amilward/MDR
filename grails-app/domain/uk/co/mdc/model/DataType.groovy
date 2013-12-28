package uk.co.mdc.model

class DataType extends ModelElement  {
	
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
		extension nullable: true
		relations nullable: true
    }
	
	static hasMany = {valueDomains: ValueDomain}
}
