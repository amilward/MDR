package uk.co.mdc.loaders

import uk.co.mdc.pathways.*;

class PathwaysModelLoader {

	/**
	 * Namespace string of the pathways model.
	 */
	public static final def NS_PATHWAYS_MODEL = "http://pathways.mdc.co.uk/1/pm.xsd"

	/**
	 * Static method parsing an input stream containing XML definitions conforming to the
	 * <code>http://pathways.mdc.co.uk/1/pm.xsd</code> schema.
	 * @author Charles Crichton
	 * @param inputStream The input stream to be parsed. 
	 * @return Collection of loaded PathwayModels
	 * @throws RuntimeException
	 */
	static List<Pathway> loadXML(InputStream inputStream) {
		def (List<Pathway> pathwaysModels, List<HashMap<String,Node>> idToNodeList) = mappedLoadXML(inputStream)

		return pathwaysModels
	}

	/** This method also exports the mapping used from the node identifiers to the Nodes
	 * This is useful in testing the results. The method is protected as it is really only for
	 * testing purposes and may change in the future.
	 * @returns a list containing two elements: [List<Pathway>,List<HashMap<String,Node>>]
	 */
	protected static def mappedLoadXML(InputStream inputStream) {

		//Load the inputstream into an XmlSlurper object.
		groovy.util.slurpersupport.NodeChild slurper;

		try {
			slurper = new XmlSlurper().parse(inputStream).declareNamespace(pm: NS_PATHWAYS_MODEL)
		} catch(e) {
			throw new RuntimeException("Unable to load XML",e);
		}

		//Define a list to hold the resulting Pathways - this will be returned.
		List<Pathway> pathwaysModels = []
		List<HashMap<String,Node>> idToNodeList = []
		
		//Check that there is at least a root element with the namespace
		String nsXml = slurper.namespaceURI().toString()

		//test: "throw an exception when xml has no namespace"
		if (!nsXml.equals(NS_PATHWAYS_MODEL)) {
			throw new RuntimeException("Unknown namespace in supplied XML: '"+nsXml+"' (Expected: '"+NS_PATHWAYS_MODEL+"')")
		}

		//test: "throw an exception when root element is not a Pathways element"
		final String EXPECTED_ROOT_ELEMENT_NAME = "Pathways"
		String rootElementName = slurper.name()

		if (!rootElementName.equals(EXPECTED_ROOT_ELEMENT_NAME)) {
			throw new RuntimeException("Unknown root element name in supplied XML: '"+rootElementName+"' (Expected: '"+EXPECTED_ROOT_ELEMENT_NAME+"')")
		}

		/* For each pm:Pathway element in the pathways models :
		 *  Create an empty pathway model
		 *  Load (slurp) the pathway model nodes
		 *  Load (slurp) the pathway model links
		 *  [If there is an exception in slurp it implicitly gets passed up.]
		 */
		slurper."pm:Pathway".each { pathwaysModelElement ->

			def pathwaysModel = new Pathway()
			def idToNode = new HashMap<String,Node>();
			model_slurpModelsAndNodes(idToNode, pathwaysModel, pathwaysModelElement)

			//Work out a mapping from IDs to nodes
			//HashMap<String,Node> idRefToNode = new HashMap<String,Node>()
			//addPathwayID(pathwaysModel,idToNode);
			//This is now done as it loads

			model_slurpLinks(idToNode, pathwaysModel,  pathwaysModelElement)

			//We get this far if there are no exceptions, so add to the list
			pathwaysModels += pathwaysModel
			idToNodeList += idToNode
		}

		
		return [pathwaysModels, idToNodeList]
	}

	protected static def model_slurpModelsAndNodes(HashMap<String,Node> idToNode, Pathway pathwaysModel, groovy.util.slurpersupport.NodeChild pathwaysModelElement) {

		//We must have a name
		if (pathwaysModelElement.attributes().get('name') == null) {
			throw new RuntimeException("Missing Pathway/@name attribute")
		}
		pathwaysModel.name = pathwaysModelElement.@name

		//VersionNo can be null
		pathwaysModel.versionNo = pathwaysModelElement.attributes().get('versionNo')

		//isDraft when true
		String isDraftValue = pathwaysModelElement.attributes().get('isDraft')
		pathwaysModel.isDraft = isDraftValue != null && isDraftValue.toLowerCase().equals("true")

		//Description when present (We only allow one description item)
		def descriptionCount = pathwaysModelElement.Description.size()

		if (descriptionCount > 1) {
			throw new RuntimeException("Too many Description elements. A maximum of one Description element is allowed in each Pathway element.")
		} else if (descriptionCount == 1) {
			pathwaysModel.description = pathwaysModelElement.Description.text()
		}

		//Create a list to store the pathwaysModelElements
		if (pathwaysModel.pathwayElements == null && pathwaysModelElement.Node.size() > 0) {
			pathwaysModel.pathwayElements = []
		}

		//Create nodes
		pathwaysModelElement.Node.each {

			//Create an empty node
			uk.co.mdc.pathways.Node node = new uk.co.mdc.pathways.Node()

			//Slurp data into it
			node_slurpModelsAndNodes(idToNode, node, it)

			//Add to pathway elements
			pathwaysModel.pathwayElements += node
			node.pathwaysModel = pathwaysModel
		}
	}

	protected static def model_slurpLinks(HashMap<String,Node> idToNode, Pathway pathwaysModel, groovy.util.slurpersupport.NodeChild pathwaysModelElement) {

		assert idToNode != null

		//Create links
		pathwaysModelElement.Link.each { linkElement ->

			//Create an empty node
			uk.co.mdc.pathways.Link link = new uk.co.mdc.pathways.Link()



			//Slurp data into it
			link_slurpLinks(idToNode, link, linkElement)

			//Add to pathway elements
			pathwaysModel.pathwayElements += link
			link.pathwaysModel = pathwaysModel
		}

		//Create a list to store the pathwaysModelElements
		if ( pathwaysModel.pathwayElements == null && pathwaysModelElement.Link.size() > 0) {
			pathwaysModel.pathwayElements = []
		}

		//Traverse nodes
		pathwaysModelElement.Node.each { nodeElement ->

			//Find the idRef (we know it must exist at this point as it has already been loaded once)
			String nodeIdRef = nodeElement.@id.toString()

			//Find the node
			Node node = idToNode[nodeIdRef]

			//Slurp link data into it
			node_slurpLinks(idToNode, node, nodeElement)
		}
	}

	/* NODE */

	protected static def node_slurpModelsAndNodes(HashMap<String,Node> idToNode, Node node, groovy.util.slurpersupport.NodeChild nodeElement) {

		//We must have an id
		if (nodeElement.attributes().get('id') == null) {
			throw new RuntimeException("Missing Node/@id attribute")
		}
	 

		String tId = nodeElement.@id.toString()
		if (idToNode[tId] != null) {
			throw new RuntimeException("There are two nodes with the same id='"+tId+"'")
		}
		idToNode[tId] = node
		
		//We must have a name
		if (nodeElement.attributes().get('name') == null) {
			throw new RuntimeException("Missing Node/@name attribute")
		}
		node.name = nodeElement.@name.toString()

		//Load x and y if available
		node.x = nodeElement.attributes().get('x')?.toString()
		node.y = nodeElement.attributes().get('y')?.toString()

		//Description when present (We only allow one description item)
		def descriptionCount = nodeElement.Description.size()

		if (descriptionCount > 1) {
			throw new RuntimeException("Too many Description elements. A maximum of one Description element is allowed in each Node element.")
		} else if (descriptionCount == 1) {
			node.description = nodeElement.Description.text()
		}

		//Make sure there is a submodel list to put items into
		def numberOfsubModelElements = nodeElement."pm:Pathway".size()
		if (numberOfsubModelElements != 0 && numberOfsubModelElements !=1) {
			throw new RuntimeException("A node may only contain a maximum of 1 Pathway.")
		}
		if (numberOfsubModelElements )
		//Load any submodels if present
		/* For each pm:Pathway element in the pathways models :
		 *  Create an empty pathway model
		 *  Load (slurp) the pathway model
		 *  [If there is an exception in slurp it implicitly gets passed up.]
		 */
		nodeElement."pm:Pathway".each { pathwaysModelElement ->

			def createdPathway = new Pathway()
			model_slurpModelsAndNodes(idToNode,createdPathway,pathwaysModelElement)

			//We get this far if there are no exceptions, so add to the list
			node.subModel = createdPathway
			createdPathway.parentNode = node

		}
	}

	protected static def node_slurpLinks(HashMap<String,Node> idToNode, Node node, groovy.util.slurpersupport.NodeChild nodeElement) {
		nodeElement."pm:Pathway".each { pathwaysModelElement ->
			/* We know that the submodel must exist as we have already loaded it */
			model_slurpLinks(idToNode, node.subModel, pathwaysModelElement)
		}
	}

	/* LINK */

	protected static def link_slurpLinks(HashMap<String,Node> idToNode, Link link, groovy.util.slurpersupport.NodeChild linkElement) {

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
		if (!idToNode.containsKey(sourceRefId)) {

			throw new RuntimeException("Unable to find Node for Link with source='"+sourceRefId+"'")
		}
		if (!idToNode.containsKey(targetRefId)) {

			throw new RuntimeException("Unable to find Node for Link with target='"+targetRefId+"'")
		}

		//Actually dereference
		link.source = idToNode[sourceRefId]
		link.target = idToNode[targetRefId]

		//Load id and name if available		
		String tId = linkElement.attributes().get('id').toString()
		if (tId != null) {
			if (idToNode[tId] != null) {
				throw new RuntimeException("There are two links with the same id='"+tId+"'")
			}
			idToNode[tId] = link
		}
		
		link.name = linkElement.attributes().get('name')?.toString()

		//Description when present (We only allow one description item)
		def descriptionCount = linkElement.Description.size()

		if (descriptionCount > 1) {
			throw new RuntimeException("Too many Description elements. A maximum of one Description element is allowed in each Link element.")
		} else if (descriptionCount == 1) {
			link.description = linkElement.Description.text()
		}
	}
}