package uk.co.mdc.model

class ExternalSynonym {
	
	String externalIdentifier
	String name
	String url
	Map attributes
	
	static auditable = true
	
	static searchable = {
		content: spellCheck 'include'
	}
	
    static constraints = {
		attributes nullable:true
		externalIdentifier nullable: true
		name blank: false
    }
}
