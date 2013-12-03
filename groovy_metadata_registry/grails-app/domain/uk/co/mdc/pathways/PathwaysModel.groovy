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
	 * @throws 
	 */
	static List<PathwaysModel> loadXML(InputStream inputStream) {

		//Load the inputstream into an XmlSlurper object.
		groovy.util.slurpersupport.NodeChild slurper = new XmlSlurper().parse(inputStream).declareNamespace(pm: NS_PATHWAYS_MODEL)

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
		 *  Load (slurp) the 
		 * 
		 * 
		 */
		slurper."pm:PathwaysModels". {

			def pathwaysModel = new PathwaysModel()
			pathwaysModel.slurp(item)

			//We get this far if there are no exceptions, so add to the list
			pathwaysModels += pathwaysModel
		}

		return pathwaysModels;
	}

}
