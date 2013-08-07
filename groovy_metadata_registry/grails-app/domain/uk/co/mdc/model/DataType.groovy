package uk.co.mdc.model

class DataType {
	
	String name
	Boolean enumerated
	Map enumerations

	static searchable = true
	
    static constraints = {
		enumerations nullable: true
		name blank: false
    }
	
	static hasMany = {valueDomains: ValueDomain}
}
