package uk.co.mdc.pathways

import uk.co.mdc.model.ExtensibleObject;

class PathwaysModel  {
	

	String refId
	String name
	String versionNo
	Boolean isDraft
	String description
	
	static searchable = {
		content: spellCheck 'include'
	}
	
	static hasMany = [pathwayElements : PathwayElement] 

    static constraints = {
		refId unique:true, nullable: true
		description nullable: true
		versionNo nullable:true  
		pathwayElements nullable:true
    }
	
	static mapping = {
	}
	
	List getNodes(){
		
		def nodes = []
				
		this.pathwayElements.each{ element ->
			
			if(element.instanceOf(Node)){
			
				nodes.push(element)
			}
		}

		return nodes
		
	}
	
	List getLinks(){
		def links = []
				
		this.pathwayElements.each{ element ->
			
			if(element.instanceOf(Link)){
				links.push(element)
			}
		}

		return links
		
	}
	
	
}
