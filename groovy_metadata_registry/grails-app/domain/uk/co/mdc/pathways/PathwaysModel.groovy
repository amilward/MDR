package uk.co.mdc.pathways

import uk.co.mdc.model.ExtensibleObject;

class PathwaysModel  {
	
	String name
	String versionNo
	Boolean isDraft
	String description
	
	static searchable = {
		content: spellCheck 'include'
	}
	
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
	 * Static method parsing an input stream containing XML definitions conforming to the
	 * <code>http://pathways.mdc.co.uk/1/pm.xsd</code> schema.
	 * @author Charles Crichton
	 * @param inputStream The input stream to be parsed. 
	 * @return Collection of loaded PathwayModels
	 * @throws 
	 */
	static List<PathwaysModel> loadXML(InputStream inputStream) {
		
		final def ns_pm = "http://pathways.mdc.co.uk/1/pm.xsd"
		
		println(inputStream.getClass().toString())
		
		groovy.util.slurpersupport.NodeChild slurper = new XmlSlurper().parse(inputStream).declareNamespace(pm: ns_pm)
		
		def pathwaysModels = [];
		
		
		
		//Check that there is at least a root element with the namespace
		{
			def namespaceURI = slurper.namespaceURI();
		}
		if (namespaceURI.equals(ns_pm)) {
			throw new RuntimeException("Unknown namespace in supplied XML: "+namespaceURI)
		}
		}
		
		slurper."pm:PathwaysModels".each {		

			def pathwaysModel = new PathwaysModel()
			pathwaysModel.slurp(item)
						
			//We get this far if there are no exceptions, so add to the list
			pathwaysModels += pathwaysModel
		}
		
		return pathwaysModels;
	}
	
}
