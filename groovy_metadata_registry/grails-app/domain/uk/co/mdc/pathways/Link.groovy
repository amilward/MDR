package uk.co.mdc.pathways

import java.util.Map;

import uk.co.mdc.model.Collection;

class Link extends PathwayElement{
	
	Node source
	Node target

	static belongsTo = [pathwaysModel: PathwaysModel]
	
	static constraints = {
		pathwaysModel nullable: true
	}
	
	protected def slurpLinks(HashMap<String,Node> idRefToNode, groovy.util.slurpersupport.NodeChild linkElement) {
		
		//There must be a source and a target link id
		if (linkElement.attributes().get('source') == null) {
			throw new RuntimeException("Missing Link/@source attribute")
		}
		
		String sourceRefId = linkElement.@source.toString()

		if (linkElement.attributes().get('target') == null) {
			throw new RuntimeException("Missing Link/@target attribute")
		}
		
		String targetRefId = linkElement.@target.toString()
		
		//We must be able to dereference the ids
		if (!idRefToNode.containsKey(sourceRefId)) {
					
			throw new RuntimeException("Unable to find Node for Link with source='"+sourceRefId+"'")			
		}
		if (!idRefToNode.containsKey(targetRefId)) {
						
			throw new RuntimeException("Unable to find Node for Link with target='"+targetRefId+"'")
		}
	 
		//Actually dereference
		this.source = idRefToNode[sourceRefId]
		this.target = idRefToNode[targetRefId]
			
		//Load id and name if available	 
		this.refId = linkElement.attributes().get('id')?.toString()
		this.name = linkElement.attributes().get('name')?.toString()
		
		//Description when present (We only allow one description item)
		def descriptionCount = linkElement.Description.size()
		  
		if (descriptionCount > 1) {
			throw new RuntimeException("Too many Description elements. A maximum of one Description element is allowed in each Link element.")
		} else if (descriptionCount == 1) {
			this.description = linkElement.Description.text()
		}
	}
	
}
