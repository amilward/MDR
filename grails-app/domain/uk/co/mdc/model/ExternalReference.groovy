package uk.co.mdc.model

import java.util.Map;

class ExternalReference extends ExtensibleObject  {

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
