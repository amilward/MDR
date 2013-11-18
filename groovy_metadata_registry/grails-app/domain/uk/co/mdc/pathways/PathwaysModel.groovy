package uk.co.mdc.pathways

import uk.co.mdc.model.ExtensibleObject;

class PathwaysModel extends ExtensibleObject  {
	

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
		refId unique:true
		description nullable: true
    }
	
	static mapping = {
		pathwayElements cascade: 'all-delete-orphan'
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
