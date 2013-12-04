package uk.co.mdc.pathways

import grails.test.mixin.*
import org.junit.*

@TestFor(PathwaysModel)
class PathwaysModelSpec extends spock.lang.Specification {

	def "empty pathways models have no nodes" () {
		
		when: "we create a blank PathwaysModel"
			PathwaysModel pathwaysModel = new PathwaysModel()
		
		then: "there are no nodes or links"
			assert pathwaysModel.getNodes().isEmpty();
			assert pathwaysModel.getLinks().isEmpty();
		
	}
	
	def "pathways models can return a node placed into it" () {
		
		when: "we create a PathwaysModel with a node"
			PathwaysModel pathwaysModel = new PathwaysModel()
			Node node = new Node();
			pathwaysModel.pathwayElements = [ node ];
			
		then: "we can get the node back out"
			assert pathwaysModel.getNodes()[0] == node;		
	}
	
	def "pathways models can return a couple of nodes and a link placed into it" () {
		
		when: "we create a PathwaysModel with a node"
			PathwaysModel pathwaysModel = new PathwaysModel()
			Node node1 = new Node();
			Node node2 = new Node();
			Link link = new Link();
			link.source = node1;
			link.source = node2;
			
			pathwaysModel.pathwayElements = [ node1, node2, link ];
			
		then: "we can get the nodes and link back out"
			assert pathwaysModel.getNodes()[0] == node1;
			assert pathwaysModel.getNodes()[1] == node2;
			assert pathwaysModel.getLinks()[0] == link;
	}
	
//    void testSomething() {
//       fail "Implement me"
//    }
}
