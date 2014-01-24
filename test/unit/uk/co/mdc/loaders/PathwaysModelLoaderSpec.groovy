
package uk.co.mdc.loaders

import grails.test.mixin.*

import org.junit.*

import com.sun.xml.internal.ws.client.sei.ResponseBuilder.InputStreamBuilder;

import groovy.xml.XmlUtil;
import uk.co.mdc.pathways.*;

/**
 * Unit tests for XML Slurping of Pathways Models
 * @author Charles Crichton (Charles.Crichton@ndm.ox.ac.uk)
 */
//@TestFor(PathwayLoader)
class PathwaysModelLoaderSpec extends spock.lang.Specification {

	/* XML Constants */
	
	static final def XML_PI = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
	static final def NS_PATHWAYS_MODEL = "http://pathways.mdc.co.uk/1/pm.xsd"
	static final def QUOTED_NS_PATHWAYS_MODEL = "\""+NS_PATHWAYS_MODEL+"\""
	
	/* Loading XML */
	
	static final def XML_INVALID_CORRUPT = XML_PI+"""
		<This is not valid XML>
		<& neither is this!>
	"""
	
	static final def XML_INVALID_NO_NAMESPACE = XML_PI+"""
		<Pathways>			
		</Pathways>
	"""
	
	static final String XML_INVALID_INCORRECT_ROOT_ELEMENT_NAME = XML_PI+"""
		<OtherModel xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		</OtherModel>
	"""
	
	def "throw an exception when xml is corrupt" () {
		when: "presented with an imput stream containing invalid xml (corrupt)"			
			PathwaysAndMapping pmam = loadPathways (XML_INVALID_CORRUPT)
			
		then: "an exception should be thrown"
			RuntimeException e = thrown()
	}
	
	def "throw an exception when xml has no namespace" () {
		when: "presented with an imput stream containing invalid xml (no namespace)"
			PathwaysAndMapping pmam = loadPathways(XML_INVALID_NO_NAMESPACE)
		
		then: "an exception should be thrown"		
			RuntimeException e = thrown()	
	}
	
	def "throw an exception when root element is not a Pathways element" () {
		when: "presented with an imput stream containing invalid xml (incorrect root element name)"
			PathwaysAndMapping pmam = loadPathways(XML_INVALID_INCORRECT_ROOT_ELEMENT_NAME)
		
		then: "an exception should be thrown"
			RuntimeException e = thrown()
	}
	
	/* Load empty model */
			
	static final String XML_PATHWAYS_MODELS_EMPTY = XML_PI+"""
		<Pathways xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		
		</Pathways>
	"""
	
	def "load pathway model that is empty" () {
		when: "presented with an imput stream containing valid xml"
			PathwaysAndMapping pmam = loadPathways(XML_PATHWAYS_MODELS_EMPTY)
					
		then: "an empty collection of pathway models should be returned"
			pmam.pathwaysModels == [];
	}
	
	/* Load single pathway */
	
	static final String XML_PATHWAYS_MODEL_NO_ATTRIBUTES = XML_PI+"""
	<Pathways xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		<Pathway/>
	</Pathways>
	"""
	 	
	static final String XML_PATHWAYS_MODEL_JUST_NAME_ATTRIBUTE = XML_PI+"""
	<Pathways xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		<Pathway name="name"/>
	</Pathways>
	"""
	
	static final String XML_PATHWAYS_MODEL_WITH_ATTRIBUTES_1 = XML_PI+"""
	<Pathways xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		<Pathway versionNo="1.0" isDraft="true" name="name"/>
	</Pathways>
	"""
	
	static final String XML_PATHWAYS_MODEL_WITH_ATTRIBUTES_2 = XML_PI+"""
	<Pathways xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		<Pathway versionNo="1.0" isDraft="false" name="name"/>
	</Pathways>
	"""
	
	static final String XML_PATHWAYS_MODEL_WITH_DESCRIPTION_1 = XML_PI+"""
	<Pathways xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		<Pathway versionNo="1.0" isDraft="false" name="name">
			<Description>desc</Description>
		</Pathway>
	</Pathways>
	"""
	
	static final String XML_PATHWAYS_MODEL_WITH_TOO_MANY_DESCRIPTIONS = XML_PI+"""
	<Pathways xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		<Pathway versionNo="1.0" isDraft="false" name="name">
			<Description>desc</Description>
			<Description>desc</Description>
		</Pathway>
	</Pathways>
	"""
	
	def "Pathway with no name attribute is invalid" () {
		when: "Pathway has no attributes"			
			PathwaysAndMapping pmam = loadPathways(XML_PATHWAYS_MODEL_NO_ATTRIBUTES)
						
		then: "an exception should be thrown"
			RuntimeException e = thrown()				
	}

	def "Pathway with just name attribute" () {
		when: "Pathway has no attributes"
			PathwaysAndMapping pmam = loadPathways(XML_PATHWAYS_MODEL_JUST_NAME_ATTRIBUTE)
		
		then: "the fields are instantiated"
			Pathway pathwaysModel = pmam.pathwaysModels[0]
		
			assert pathwaysModel.name.equals("name")
			assert pathwaysModel.versionNo == null
			assert pathwaysModel.isDraft == false
			assert pathwaysModel.description == null
			assert pathwaysModel.getNodes().isEmpty()
			assert pathwaysModel.getLinks().isEmpty()
	}
	
	def "Pathway has some attributes 1" () {
		when: "Pathway has some attributes 1"
			PathwaysAndMapping pmam = loadPathways(XML_PATHWAYS_MODEL_WITH_ATTRIBUTES_1)
		
		then: "the fields are instantiated"
			Pathway pathwaysModel = pmam.pathwaysModels[0]
		
			assert pathwaysModel.name.equals("name")
			assert pathwaysModel.versionNo.equals("1.0")
			assert pathwaysModel.isDraft == true
			assert pathwaysModel.description == null
			assert pathwaysModel.getNodes().isEmpty()
			assert pathwaysModel.getLinks().isEmpty()
	}
	
	def "Pathway has some attributes 2" () {
		when: "Pathway has some attributes 2"
			PathwaysAndMapping pmam = loadPathways(XML_PATHWAYS_MODEL_WITH_ATTRIBUTES_2)
		
		then: "the fields are instantiated"
			Pathway pathwaysModel = pmam.pathwaysModels[0]
		
			assert pathwaysModel.name.equals("name")
			assert pathwaysModel.versionNo.equals("1.0")
			assert pathwaysModel.isDraft == false
			assert pathwaysModel.description == null
			assert pathwaysModel.getNodes().isEmpty()
			assert pathwaysModel.getLinks().isEmpty()
	}
	
	def "Pathway has a description 1" () {
		when: "Pathway has a description 1"
			PathwaysAndMapping pmam = loadPathways(XML_PATHWAYS_MODEL_WITH_DESCRIPTION_1)
		
		then: "the fields are instantiated"
			Pathway pathwaysModel = pmam.pathwaysModels[0]
		
			assert pathwaysModel.name.equals("name")
			assert pathwaysModel.versionNo.equals("1.0")
			assert pathwaysModel.isDraft == false
			assert pathwaysModel.description.equals("desc")
			assert pathwaysModel.getNodes().isEmpty()
			assert pathwaysModel.getLinks().isEmpty()
	}
	
	def "Pathway has too many descriptions" () {
		when: "Pathway has too many descriptions"
			PathwaysAndMapping pmam = loadPathways(XML_PATHWAYS_MODEL_WITH_TOO_MANY_DESCRIPTIONS)
		
		then: "an exception should be thrown"
			RuntimeException e = thrown()	
	}
	
	/* Pathways with nodes and links */
	
	static final String XML_PATHWAYS_MODEL_WITH_ONE_NODE = XML_PI+"""
	<Pathways xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		<Pathway versionNo="1.0" isDraft="false" name="name">
			<Node id="id.1" name="A"/>			 
		</Pathway>
	</Pathways>
	"""
	
	static final String XML_PATHWAYS_MODEL_TWO_NODES_ONE_LINK = XML_PI+"""
	<Pathways xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		<Pathway versionNo="1.0" isDraft="false" name="name">
			<Node id="id.1" name="A"/>
			<Node id="id.2" name="B"/>
			<Link source="id.1" target="id.2"/>
		</Pathway>
	</Pathways>
	"""
	
	
	def "Pathway has a node" () {
		when: "Pathway has a node"
			PathwaysAndMapping pmam = loadPathways(XML_PATHWAYS_MODEL_WITH_ONE_NODE)
		
		then: "the tId is instantiated"
			Pathway pathwaysModel = pmam.pathwaysModels[0]
			HashMap<String,Node> idToNode = pmam.idToNodeList[0]
			Node node1 = pathwaysModel.getNodes()[0]			
			assert idToNode["id.1"] == node1
			assert node1.pathwaysModel == pathwaysModel
			assert pathwaysModel.parentNode == null	
	}
	
	def "Pathway has two nodes and a link" () {
		when: "Pathway has two nodes and one link"
			PathwaysAndMapping pmam = loadPathways(XML_PATHWAYS_MODEL_TWO_NODES_ONE_LINK)
		
		then: "the link contains the nodes"
			Pathway pathwaysModel = pmam.pathwaysModels[0]
			HashMap<String,Node> idToNode = pmam.idToNodeList[0]
			
			Node node1 = idToNode["id.1"]  
			Node node2 = idToNode["id.2"]  
			Link link1 = pathwaysModel.getLinks()[0];			
			assert link1.source == node1
			assert link1.target == node2
			assert node1.pathwaysModel == pathwaysModel		
	}
		
	/* Pathways with sub-pathways */
	
	static final String XML_PATHWAYS_MODEL_SUBPATHWAY_TWO_NODES_ONE_LINK = XML_PI+"""
	<Pathways xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		<Pathway versionNo="1.0" isDraft="false" name="name">
			<Node id="id.1" name="A1">
				<Pathway name="A2">
					<Node id="id.2" name="B"/>
					<Node id="id.3" name="C"/>
					<Link id="l.1" source="id.2" target="id.3"/>
				</Pathway>
			</Node>
		</Pathway>
	</Pathways>
	"""
	
	static final String XML_PATHWAYS_MODEL_MULTIPLE_SUBPATHWAY = XML_PI+"""
	<Pathways xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		<Pathway versionNo="1.0" isDraft="false" name="name">
			<Node id="id.1" name="A">
				<Pathway name="B"/>
				<!-- This next pathways model is not allowed -->
				<Pathway name="C"/>
			</Node>
		</Pathway>
	</Pathways>
	"""
	
	static final String XML_PATHWAYS_MODEL_SUBPATHWAY_FOUR_NODES_THREE_LINKS= XML_PI+"""
	<Pathways xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
		<Pathway versionNo="1.0" isDraft="false" name="name">
			<Node id="id.1" name="A1">
				<Pathway name="A2">
					<Node id="id.2" name="B"/>
					<Node id="id.3" name="C"/>
					<Link  id="l.1" source="id.2" target="id.3"/>
					<!-- Yes a multiple level link! -->
					<Link  id="l.2" source="id.3" target="id.4"/>
				</Pathway>
			</Node>
			<Link  id="l.3" source="id.1" target="id.4"/>
			<Node id="id.4" name="D"/>
		</Pathway>
	</Pathways>
	"""
	
	def "Pathway has a node with a subpathway that has two nodes and a link" () {
		when: "Pathway has two nodes and one link"
			PathwaysAndMapping pmam = loadPathways(XML_PATHWAYS_MODEL_SUBPATHWAY_TWO_NODES_ONE_LINK)
		
		then: "the pathway is set up as follows"
			Pathway pathwaysModel = pmam.pathwaysModels[0]
			HashMap<String,Node> idToNode = pmam.idToNodeList[0]
			
			Node node1 = idToNode["id.1"]
			Pathway subPathway = node1.subModel
			assert subPathway != null
			
			Node node2 = idToNode["id.2"]
			assert node2 != null
						
			Node node3 = idToNode["id.3"]
			assert node3 != null
			
			Link link1 = idToNode["l.1"]
			assert link1 != null
			
			assert link1.source == node2
			assert link1.target == node3
			
			assert pathwaysModel.parentNode == null
			assert subPathway.parentNode == node1	
			assert node1.pathwaysModel == pathwaysModel
			assert node2.pathwaysModel == subPathway
			assert node3.pathwaysModel == subPathway
			
	}
	
 	def "Pathway has a node with two subpathways is not allowed" () {
		when: "Pathway has two nodes and one link"
			PathwaysAndMapping pmam = loadPathways(XML_PATHWAYS_MODEL_MULTIPLE_SUBPATHWAY)
				
		then: "an exception should be thrown"
		RuntimeException e = thrown()			
	}
	
	def "Pathway has two nodes with a subpathway that has two nodes and 3 links" () {
		when: "Pathway has two nodes and one link"
			PathwaysAndMapping pmam = loadPathways(XML_PATHWAYS_MODEL_SUBPATHWAY_FOUR_NODES_THREE_LINKS)
		
		then: "the pathway is set up as follows"
			Pathway pathwaysModel = pmam.pathwaysModels[0]
			HashMap<String,Node> idToNode = pmam.idToNodeList[0]
			
			Node node1 = idToNode["id.1"]
			assert node1 != null
			
			
			Pathway subPathway = node1.subModel
			assert subPathway != null
			
			Node node2 = idToNode["id.2"]
			assert node2 != null
						
			Node node3 = idToNode["id.3"] 
			assert node3 != null
			
			Node node4 = idToNode["id.4"]
			assert node1 != null
			
			Link link1 = idToNode["l.1"]
			assert link1 != null

			Link link2 = idToNode["l.2"]
			assert link2 != null

			Link link3 = idToNode["l.3"]
			assert link3 != null
					
			assert link1.source == node2
			assert link1.target == node3

			assert link2.source == node3
			assert link2.target == node4
			
			assert link3.source == node1
			assert link3.target == node4
			
			assert pathwaysModel.parentNode == null
			assert subPathway.parentNode == node1
			assert node1.pathwaysModel == pathwaysModel
			assert node2.pathwaysModel == subPathway
			assert node3.pathwaysModel == subPathway
			assert node4.pathwaysModel == pathwaysModel
			
	}
	
	
	/* Load multiple pathways */
	
	/* Helper methods: Turn Strings into streams and load pathways */
	
	def InputStream convertToInputStream(String text) {
		return new ByteArrayInputStream(text.getBytes(java.nio.charset.Charset.forName("UTF-8")))		
	}
	
	/**
	 * Returns a list containing two items. The first is a list of loaded pathways the second item is a corresponding 
	 * list of mappings from is to Node.
	 * To use: 	
	 * <code>PathwaysAndMapping pmam = loadPathways (...)</code>
	 * @param text The XML to be parsed.
	 * @return
	 */
	
	
	protected PathwaysAndMapping loadPathways (String text) {
		
		return new PathwaysAndMapping(PathwayLoader.mappedLoadXML(
				convertToInputStream(text)
		));
	}
	
	//Needed to work around issues with spock and returning multiple values from methods.
	class PathwaysAndMapping {
		
		public PathwaysAndMapping(List<Object> obj) {
			this.pathwaysModels = obj[0]
			this.idToNodeList = obj[1]			
		}
		
		List<Pathway> pathwaysModels;
		List<HashMap<String,Node>> idToNodeList 		
	}
}
