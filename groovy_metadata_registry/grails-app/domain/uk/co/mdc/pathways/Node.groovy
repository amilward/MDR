package uk.co.mdc.pathways

import java.util.Map;

import uk.co.mdc.model.Collection;

class Node extends PathwayElement{
	
	String x //ISSUE It seems strange to me that x and y are not integers. (@charlescrichton)
	String y
	PathwaysModel subModel
	//PathwaysModel pathwaysModel - what is this for??

	static belongsTo = [pathwaysModel: PathwaysModel]
	
	static hasOne = [subModel: PathwaysModel]
	
	public Node(String refId, String name,String x, String y, String desc, Collection peCollection){
		super( refId, name, desc, peCollection)
		this.x = x
		this.y = y
	}
	
	static hasMany = [
		mandatoryInputs: Collection,
		mandatoryOutputs: Collection,
		optionalInputs: Collection,
		optionalOutputs: Collection]

    static constraints = {
		pathwaysModel nullable: true
		subModel nullable: true
		x nullable:true
		y nullable:true
    }
	
	static mapping = {
		sort "name"
	}
	
	protected def slurpModelsAndNodes(groovy.util.slurpersupport.NodeChild nodeElement) {
		
		//We must have an id
		if (nodeElement.attributes().get('id') == null) {
			throw new RuntimeException("Missing Node/@id attribute")
		}
		this.transientId = nodeElement.@id.toString()
		
		//We must have a name
		if (nodeElement.attributes().get('name') == null) {
			throw new RuntimeException("Missing Node/@name attribute")
		}
		this.name = nodeElement.@name.toString()
		
		//Load x and y if available
		this.x = nodeElement.attributes().get('x')?.toString()
		this.y = nodeElement.attributes().get('y')?.toString()
		
		//Description when present (We only allow one description item)
		def descriptionCount = nodeElement.Description.size()
		  
		if (descriptionCount > 1) {
			throw new RuntimeException("Too many Description elements. A maximum of one Description element is allowed in each Node element.")
		} else if (descriptionCount == 1) {
			this.description = nodeElement.Description.text()
		}
		
		//Make sure there is a submodel list to put items into
		if (nodeElement."pm:PathwaysModel".size() > 0) {
			this.subModel = []
		}
		//Load any submodels if present
		/* For each pm:PathwaysModel element in the pathways models :
		 *  Create an empty pathway model
		 *  Load (slurp) the pathway model
		 *  [If there is an exception in slurp it implicitly gets passed up.]
		 */
		nodeElement."pm:PathwaysModel".each { pathwaysModelElement ->

			def pathwaysModel = new PathwaysModel()
			pathwaysModel.slurpModelsAndNodes(pathwaysModelElement)

			//We get this far if there are no exceptions, so add to the list
			subModel = pathwaysModel
		}
	}
	
	protected def slurpLinks(HashMap<String,Node> idRefToNode, groovy.util.slurpersupport.NodeChild nodeElement) {
		nodeElement."pm:PathwaysModel".each { pathwaysModelElement ->			
			/* We know that the submodel must exist as we have already loaded it */
			subModel.slurpLinks(idRefToNode, pathwaysModelElement)						
		}		
	}

}
