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
	static List<PathwaysModel> loadXML(InputStream inputStream) {
		def (List<PathwaysModel> pathwaysModels, List<HashMap<String,PathwayElement>> idToPathwayElementList) = mappedLoadXML(inputStream)

		return pathwaysModels
	}

	/** This method also exports the mapping used from the node identifiers to the PathwayElements
	 * This is useful in testing the results. The method is protected as it is really only for
	 * testing purposes and may change in the future.
	 * @returns a list containing two elements: [List<PathwaysModel>,List<HashMap<String,PathwayElement>>]
	 */
	protected static def mappedLoadXML(InputStream inputStream) {

		//Load the inputstream into an XmlSlurper object.
		groovy.util.slurpersupport.NodeChild slurper;

		try {
			slurper = new XmlSlurper().parse(inputStream).declareNamespace(pm: NS_PATHWAYS_MODEL)
		} catch(e) {
			throw new RuntimeException("Unable to load XML",e);
		}

		//Define a list to hold the resulting PathwaysModels - this will be returned.
		List<PathwaysModel> pathwaysModels = []
		List<HashMap<String,PathwayElement>> idToPathwayElementList = []
		
		//Check that there is at least a root element with the namespace
		String nsXml = slurper.namespaceURI().toString()

		//test: "throw an exception when xml has no namespace"
		if (!nsXml.equals(NS_PATHWAYS_MODEL)) {
			throw new RuntimeException("Unknown namespace in supplied XML: '"+nsXml+"' (Expected: '"+NS_PATHWAYS_MODEL+"')")
		}

		//test: "throw an exception when root element is not a PathwaysModels element"
		final String EXPECTED_ROOT_ELEMENT_NAME = "PathwaysModels"
		String rootElementName = slurper.name()

		if (!rootElementName.equals(EXPECTED_ROOT_ELEMENT_NAME)) {
			throw new RuntimeException("Unknown root element name in supplied XML: '"+rootElementName+"' (Expected: '"+EXPECTED_ROOT_ELEMENT_NAME+"')")
		}

		/* For each pm:PathwaysModel element in the pathways models :
		 *  Create an empty pathway model
		 *  Load (slurp) the pathway model nodes
		 *  Load (slurp) the pathway model links
		 *  [If there is an exception in slurp it implicitly gets passed up.]
		 */
		slurper."pm:PathwaysModel".each { pathwaysModelElement ->

			def pathwaysModel = new PathwaysModel()
			def idToPathwayElement = new HashMap<String,PathwayElement>();
			model_slurpModelsAndNodes(idToPathwayElement, pathwaysModel, pathwaysModelElement)

			//Work out a mapping from IDs to nodes
			//HashMap<String,Node> idRefToNode = new HashMap<String,Node>()
			//addPathwaysModelID(pathwaysModel,idToPathwayElement);
			//This is now done as it loads

			model_slurpLinks(idToPathwayElement, pathwaysModel,  pathwaysModelElement)

			//We get this far if there are no exceptions, so add to the list
			pathwaysModels += pathwaysModel
			idToPathwayElementList += idToPathwayElement
		}

		
		return [pathwaysModels, idToPathwayElementList]
	}

	protected static def model_slurpModelsAndNodes(HashMap<String,PathwayElement> idToPathwayElement, PathwaysModel pathwaysModel, groovy.util.slurpersupport.NodeChild pathwaysModelElement) {

		//We must have a name
		if (pathwaysModelElement.attributes().get('name') == null) {
			throw new RuntimeException("Missing PathwaysModel/@name attribute")
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
			throw new RuntimeException("Too many Description elements. A maximum of one Description element is allowed in each PathwaysModel element.")
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
			node_slurpModelsAndNodes(idToPathwayElement, node, it)

			//Add to pathway elements
			pathwaysModel.pathwayElements += node
			node.pathwaysModel = pathwaysModel
		}
	}

	protected static def model_slurpLinks(HashMap<String,PathwayElement> idToPathwayElement, PathwaysModel pathwaysModel, groovy.util.slurpersupport.NodeChild pathwaysModelElement) {

		assert idToPathwayElement != null

		//Create links
		pathwaysModelElement.Link.each { linkElement ->

			//Create an empty node
			uk.co.mdc.pathways.Link link = new uk.co.mdc.pathways.Link()



			//Slurp data into it
			link_slurpLinks(idToPathwayElement, link, linkElement)

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
			Node node = idToPathwayElement[nodeIdRef]

			//Slurp link data into it
			node_slurpLinks(idToPathwayElement, node, nodeElement)
		}
	}

	/* NODE */

	protected static def node_slurpModelsAndNodes(HashMap<String,PathwayElement> idToPathwayElement, Node node, groovy.util.slurpersupport.NodeChild nodeElement) {

		//We must have an id
		if (nodeElement.attributes().get('id') == null) {
			throw new RuntimeException("Missing Node/@id attribute")
		}
	 

		String tId = nodeElement.@id.toString()
		if (idToPathwayElement[tId] != null) {
			throw new RuntimeException("There are two nodes with the same id='"+tId+"'")
		}
		idToPathwayElement[tId] = node
		
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
		def numberOfsubModelElements = nodeElement."pm:PathwaysModel".size()
		if (numberOfsubModelElements != 0 && numberOfsubModelElements !=1) {
			throw new RuntimeException("A node may only contain a maximum of 1 PathwaysModel.")
		}
		if (numberOfsubModelElements )
		//Load any submodels if present
		/* For each pm:PathwaysModel element in the pathways models :
		 *  Create an empty pathway model
		 *  Load (slurp) the pathway model
		 *  [If there is an exception in slurp it implicitly gets passed up.]
		 */
		nodeElement."pm:PathwaysModel".each { pathwaysModelElement ->

			def createdPathwaysModel = new PathwaysModel()
			model_slurpModelsAndNodes(idToPathwayElement,createdPathwaysModel,pathwaysModelElement)

			//We get this far if there are no exceptions, so add to the list
			node.subModel = createdPathwaysModel
			createdPathwaysModel.parentNode = node

		}
	}

	protected static def node_slurpLinks(HashMap<String,PathwayElement> idToPathwayElement, Node node, groovy.util.slurpersupport.NodeChild nodeElement) {
		nodeElement."pm:PathwaysModel".each { pathwaysModelElement ->
			/* We know that the submodel must exist as we have already loaded it */
			model_slurpLinks(idToPathwayElement, node.subModel, pathwaysModelElement)
		}
	}

	/* LINK */

	protected static def link_slurpLinks(HashMap<String,PathwayElement> idToPathwayElement, Link link, groovy.util.slurpersupport.NodeChild linkElement) {

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
		if (!idToPathwayElement.containsKey(sourceRefId)) {

			throw new RuntimeException("Unable to find Node for Link with source='"+sourceRefId+"'")
		}
		if (!idToPathwayElement.containsKey(targetRefId)) {

			throw new RuntimeException("Unable to find Node for Link with target='"+targetRefId+"'")
		}

		//Actually dereference
		link.source = idToPathwayElement[sourceRefId]
		link.target = idToPathwayElement[targetRefId]

		//Load id and name if available		
		String tId = linkElement.attributes().get('id').toString()
		if (tId != null) {
			if (idToPathwayElement[tId] != null) {
				throw new RuntimeException("There are two links with the same id='"+tId+"'")
			}
			idToPathwayElement[tId] = link
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