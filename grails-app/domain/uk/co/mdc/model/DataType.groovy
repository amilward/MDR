package uk.co.mdc.model

class DataType extends CatalogueElement  {
	
	String name
	Boolean enumerated
	Map enumerations

	static auditable = true
	
    static constraints = {
		enumerations nullable: true
		name blank: false
		extension nullable: true
    }
	
	static hasMany = {valueDomains: ValueDomain}
}
