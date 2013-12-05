package uk.co.mdc.pathways

import uk.co.mdc.model.ExtensibleObject;

class PathwaysModel  {

	String name
	String versionNo
	Boolean isDraft
	String description

	static searchable = { content: spellCheck 'include' }

	static hasMany = [pathwayElements : PathwayElement]

	static constraints = {
		description nullable: true
		versionNo nullable:true
		pathwayElements nullable:true
	}

	static mapping = {
		
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
	 
		//Load the inputstream into an XmlSlurper object.
		groovy.util.slurpersupport.NodeChild slurper;
		
		try {
			slurper = new XmlSlurper().parse(inputStream).declareNamespace(pm: NS_PATHWAYS_MODEL)
		} catch(e) {
			throw new RuntimeException("Unable to load XML",e);
		}
		 
		//Define a list to hold the resulting PathwaysModels - this will be returned.
		def pathwaysModels = []

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
			pathwaysModel.slurpModelsAndNodes(pathwaysModelElement)
			
			//Work out a mapping from IDs to nodes
			HashMap<String,Node> idRefToNode = new HashMap<String,Node>()				
			addPathwaysModelID(pathwaysModel,idRefToNode);				
									
			pathwaysModel.slurpLinks(idRefToNode, pathwaysModelElement)
			
			//We get this far if there are no exceptions, so add to the list
			pathwaysModels += pathwaysModel
		}
		
		//TODO Something to do with the security domain
		
		//TODO Save the pathways into hibernate (as one transaction)
		
		return pathwaysModels;
		
	}

	private static def addPathwaysModelID(PathwaysModel pm, HashMap<String,uk.co.mdc.pathways.Node> map) { 
		pm.getNodes().each { n ->
			addNodeID(n,map)
		}
	}
	
	private static def addNodeID(uk.co.mdc.pathways.Node n, HashMap<String,uk.co.mdc.pathways.Node> map) {
		map[ n.transientId ] = n 
		if (n.subModel != null) {
			addPathwaysModelID(n.subModel,map)
		}
	}
	
	protected def slurpModelsAndNodes(groovy.util.slurpersupport.NodeChild pathwaysModelElement) {
		
		//We must have a name
		if (pathwaysModelElement.attributes().get('name') == null) {
			throw new RuntimeException("Missing PathwaysModel/@name attribute")
		}
		this.name = pathwaysModelElement.@name
		
		//VersionNo can be null
		this.versionNo = pathwaysModelElement.attributes().get('versionNo')
		
		//isDraft when true
		String isDraftValue = pathwaysModelElement.attributes().get('isDraft')		
		this.isDraft = isDraftValue != null && isDraftValue.toLowerCase().equals("true")
			
		//Description when present (We only allow one description item)
		def descriptionCount = pathwaysModelElement.Description.size()
		  
		if (descriptionCount > 1) {
			throw new RuntimeException("Too many Description elements. A maximum of one Description element is allowed in each PathwaysModel element.")
		} else if (descriptionCount == 1) {
			this.description = pathwaysModelElement.Description.text()
		}
		
		//Create a list to store the pathwaysModelElements
		if (this.pathwayElements == null && pathwaysModelElement.Node.size() > 0) {
			this.pathwayElements = []
		}
		
		//Create nodes
		pathwaysModelElement.Node.each {
			
			//Create an empty node
			uk.co.mdc.pathways.Node node = new uk.co.mdc.pathways.Node()
			
			//Slurp data into it
			node.slurpModelsAndNodes(it)
			
			//Add to pathway elements
			this.pathwayElements += node
		}
	}
	
	protected def slurpLinks(HashMap<String,Node> idRefToNode, groovy.util.slurpersupport.NodeChild pathwaysModelElement) {
		
		assert idRefToNode != null
		
		//Create links
		pathwaysModelElement.Link.each { linkElement ->
			
			//Create an empty node
			uk.co.mdc.pathways.Link link = new uk.co.mdc.pathways.Link()
		
			//Slurp data into it
			link.slurpLinks(idRefToNode, linkElement)
			
			//Add to pathway elements
			this.pathwayElements += link
		}
		
		//Create a list to store the pathwaysModelElements
		if ( this.pathwayElements == null && pathwaysModelElement.Link.size() > 0) {
			this.pathwayElements = []
		}
		
		//Traverse nodes
		pathwaysModelElement.Node.each { nodeElement ->
			
			//Find the idRef (we know it must exist at this point as it has already been loaded once)
			String nodeIdRef = nodeElement.@id.toString()
						
			//Find the node
			Node node = idRefToNode[nodeIdRef]
			
			//Slurp link data into it
			node.slurpLinks(idRefToNode, nodeElement)
		}
	}
	
}
