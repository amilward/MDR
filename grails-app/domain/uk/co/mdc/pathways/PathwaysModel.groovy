package uk.co.mdc.pathways

import uk.co.mdc.model.ModelElement;

class PathwaysModel extends ModelElement{
	
	String name
	String versionNo
	Boolean isDraft
	String description
	Node parentNode
	String auditLog
	
	static searchable = {
		content: spellCheck 'include'
	}
	
	static hasMany = [pathwayElements : PathwayElement]

    static constraints = {
		description nullable: true
		versionNo nullable:true  
		pathwayElements nullable:true
		parentNode nullable:true
		auditLog nullable:true
    }
	
	static mapping = {
		pathwayElements(sort:'name', order:'asc')
	}
	
	List getNodes(){
		
		def nodes = []
				
		this.pathwayElements.each{ element ->
			
			if(element instanceof Node){
			
				nodes.push(element)
			}
		}

		return nodes
		
	}
	
	List getLinks(){
		def links = []
				
		this.pathwayElements.each{ element ->
			
			if(element instanceof Link){
				links.push(element)
			}
		}

		return links
		
	}
	
	
}
