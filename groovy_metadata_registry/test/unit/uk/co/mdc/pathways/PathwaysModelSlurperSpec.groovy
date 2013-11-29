package uk.co.mdc.pathways

import grails.test.mixin.*
import org.junit.*


/**
 * Unit tests for XML Slurping of Pathways Models
 * @author Charles Crichton (Charles.Crichton@ndm.ox.ac.uk)
 */
@TestFor(PathwaysModel)
class PathwaysModelSlurperSpec extends spock.lang.Specification {
	
	def "load pathway model that is empty" () {
		when: "presented with an imput stream containing valid xml"
		
			def inputStream = new ByteArrayInputStream(string.getBytes(Charset.forName("UTF-8")));						
			def pathwaysModels = PathwayModel.loadXML(inputStream);
		
		then: "an empty collection of pathway models should be returned"
		
			pathwaysModels == [];		
	} 
	
	/* Example pathways models for using in tests */
	def xml_pathways_models_empty = """
		<PathwaysModels xmlns="http://pathways.mdc.co.uk/1">
			
		</PathwaysModels>
"""
}
