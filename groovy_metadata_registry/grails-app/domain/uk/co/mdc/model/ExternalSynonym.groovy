package uk.co.mdc.model

class ExternalSynonym {
	
	String name
	String url
	Map attributes
	
	static searchable = {
		content: spellCheck 'include'
	}
	
    static constraints = {
		attributes nullable:true
		name blank: false
    }
}
