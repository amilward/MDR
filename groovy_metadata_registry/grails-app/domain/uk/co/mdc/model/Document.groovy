package uk.co.mdc.model

class Document {
	
	String refId
	String name
	String description
	String contentType
	String fileName
	byte[] content
	
    static constraints = {
		name blank: false, nullable: false, size: 2..20
		description blank: false, nullable: false, size: 2..500
		content maxSize: 1024 * 1024 * 2
		refId unique: true
    }
	
	static mapping = {
		description type: 'text'
	}
	
}

