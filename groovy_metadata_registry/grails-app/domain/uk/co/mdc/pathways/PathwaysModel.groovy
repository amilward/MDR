package uk.co.mdc.pathways

class PathwaysModel {
	

	String refId
	String name
	String versionNo
	Boolean isDraft
	String description
	
	static hasMany = [pathwayElements : PathwayElement] 

    static constraints = {
		refId unique:true
		description nullable: true
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
