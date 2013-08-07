package uk.co.mdc.model

class ExternalSynonym {
	
	String name
	String url
	Map attributes
	
    static constraints = {
		attributes nullable:true
		name blank: false
    }
}
