package uk.co.mdc.pathways

import grails.test.mixin.*
import org.junit.*
import groovy.xml.XmlUtil;

/**
 * Unit tests for XML Slurping of Pathways Models
 * @author Charles Crichton (Charles.Crichton@ndm.ox.ac.uk)
 */
@TestFor(PathwaysModel)
class PathwaysModelSlurperSpec extends spock.lang.Specification {
	
	def "load pathway model that is empty" () {
		when: "presented with an imput stream containing valid xml"
		
			def pathwaysModels = PathwaysModel.loadXML(
				XmlUtil.asStreamSource(xml_pathways_models_empty).inputStream
			);	
		
		then: "an empty collection of pathway models should be returned"
		
			pathwaysModels == [];		
	} 
	
	def "throw and exception when xml has no namespace" () {
		when: "presented with an imput stream containing invalid xml"
		
			def pathwaysModels = PathwaysModel.loadXML(
				XmlUtil.asStreamSource(xml_invalid_no_namespace).inputStream
			);
		
		then: "an empty collection of pathway models should be returned"		
			Throwable e = thrown()	
	}
	
	/* Example pathways models for using in tests */
	def xml_invalid_no_namespace = """
		<PathwaysModels>
			
		</PathwaysModels>
	"""
	
	def xml_pathways_models_empty = """
		<PathwaysModels xmlns="http://pathways.mdc.co.uk/1">
			
		</PathwaysModels>
"""
	

}
