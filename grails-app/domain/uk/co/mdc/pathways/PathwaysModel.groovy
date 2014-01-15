package uk.co.mdc.pathways

import uk.co.mdc.model.CatalogueElement;

class PathwaysModel extends CatalogueElement{
	
	String name
	String versionNo
	Boolean isDraft
	String description
	Node parentNode
	String auditLog
	
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
	
	//get the top level pathway that contains this particular pathways model
	
	PathwaysModel getTopLevelPathwaysModel(){
		
		if(getParentPathwaysModel()){
			
			//initiate the top level pathway as this pathway for loop
			PathwaysModel topLevelPathway = this
			
			//loop through all the parent pathways until you get to the top i.e. there is no higher level pathway
			while(topLevelPathway.getParentPathwaysModel()){
				topLevelPathway = topLevelPathway.getParentPathwaysModel()
			}
			
			return topLevelPathway
			
		}else{
			return null
		}
		
	}
	
	PathwaysModel getParentPathwaysModel(){
		if(parentNode?.pathwaysModel){
			return parentNode?.pathwaysModel
		}else{
			return null
		}
	}
	
	
}
