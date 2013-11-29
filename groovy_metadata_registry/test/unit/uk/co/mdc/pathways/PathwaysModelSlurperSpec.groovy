


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
			def pathwaysModels = loadPathwaysModels(xml_pathways_models_empty)
					
		then: "an empty collection of pathway models should be returned"		
			pathwaysModels == [];		
	} 
	
	def "throw an exception when xml has no namespace" () {
		when: "presented with an imput stream containing invalid xml (no namespace)"
			def pathwaysModels = loadPathwaysModels(xml_invalid_no_namespace)
		
		then: "an exception should be thrown"		
			Throwable e = thrown()	
	}
	
	
		
	
	/* XML Constants */
	static final def xml_pi = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
	static final def ns_pm = "http://pathways.mdc.co.uk/1/pm.xsd"
	static final def quoted_pmxsdns = "\""+ns_pm+"\""
	
	/* Turn Strings into streams */
	
	def List<PathwaysModel> loadPathwaysModels (String text) {
		return PathwaysModel.loadXML(
				convertToInputStream(xml_pathways_models_empty)
		);	
	}
	
	def InputStream convertToInputStream(String text) {
		return new ByteArrayInputStream(text.getBytes(java.nio.charset.Charset.forName("UTF-8")))		
	}
	
	/* Example pathways models for using in tests */	
	static final def xml_invalid_corrupt = xml_pi+"""
		<This is not valid XML>
		<& neither is this!>
	"""
	
	static final def xml_invalid_no_namespace = xml_pi+"""
		<PathwaysModels>
			
		</PathwaysModels>
	"""
	
	static final String xml_pathways_models_empty = xml_pi+"""
		<PathwaysModels xmlns="""+quoted_pmxsdns+""">
			
		</PathwaysModels>
"""
	

}
