


package uk.co.mdc.pathways

import grails.test.mixin.*

import org.junit.*

import com.sun.xml.internal.ws.client.sei.ResponseBuilder.InputStreamBuilder;

import groovy.xml.XmlUtil;

/**
 * Unit tests for XML Slurping of Pathways Models
 * @author Charles Crichton (Charles.Crichton@ndm.ox.ac.uk)
 */
@TestFor(PathwaysModel)
class PathwaysModelSlurperSpec extends spock.lang.Specification {

	//Section: Test basic loading and saving
		
	def "load pathway model that is empty" () {
		when: "presented with an imput stream containing valid xml"		
			def pathwaysModels = loadPathwaysModels(XML_PATHWAYS_MODELS_EMPTY)
					
		then: "an empty collection of pathway models should be returned"		
			pathwaysModels == [];		
	} 
	
	def "throw an exception when xml has no namespace" () {
		when: "presented with an imput stream containing invalid xml (no namespace)"
			def pathwaysModels = loadPathwaysModels(XML_INVALID_NO_NAMESPACE)
		
		then: "an exception should be thrown"		
			RuntimeException e = thrown()	
	}
	
	def "throw an exception when root element is not a PathwaysModels element" () {
		when: "presented with an imput stream containing invalid xml (incorrect root element name)"
			def pathwaysModels = loadPathwaysModels(XML_INVALID_INCORRECT_ROOT_ELEMENT_NAME)
		
		then: "an exception should be thrown"
			RuntimeException e = thrown()
	}
			
	/* XML Constants */
	static final def XML_PI = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
	static final def NS_PATHWAYS_MODEL = "http://pathways.mdc.co.uk/1/pm.xsd"
	static final def QUOTED_NS_PATHWAYS_MODEL = "\""+NS_PATHWAYS_MODEL+"\""
	
	/* Turn Strings into streams */
	
	def List<PathwaysModel> loadPathwaysModels (String text) {
		return PathwaysModel.loadXML(
				convertToInputStream(text)
		);	
	}
	
	def InputStream convertToInputStream(String text) {
		return new ByteArrayInputStream(text.getBytes(java.nio.charset.Charset.forName("UTF-8")))		
	}
	
	/* Example pathways models for using in tests */	
	static final def XML_INVALID_CORRUPT = XML_PI+"""
		<This is not valid XML>
		<& neither is this!>
	"""
	
	static final def XML_INVALID_NO_NAMESPACE = XML_PI+"""
		<PathwaysModels>
			
		</PathwaysModels>
	"""
	
	static final String XML_PATHWAYS_MODELS_EMPTY = XML_PI+"""
		<PathwaysModels xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
			
		</PathwaysModels>
	"""
	
	static final String XML_INVALID_INCORRECT_ROOT_ELEMENT_NAME = XML_PI+"""
		<OtherModel xmlns="""+QUOTED_NS_PATHWAYS_MODEL+""">
			
		</OtherModel>
	"""

}
