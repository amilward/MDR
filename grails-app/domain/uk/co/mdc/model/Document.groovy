package uk.co.mdc.model

class Document extends ModelElement  {

	String name
	String description
	String contentType
	String fileName
	byte[] content
	
	static auditable = true
	
    static constraints = {
		name blank: false, nullable: false, size: 2..20
		description blank: false, nullable: false, size: 2..500
		content maxSize: 1024 * 1024 * 2
    }
	
	static mapping = {
		description type: 'text'
	}
	
}

