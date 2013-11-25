
package uk.co.mdc.pathways

import grails.test.mixin.*

import uk.co.mdc.model.Collection

/**
 * Simple unit tests for pathway links
 * @author Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
 *
 */
@TestFor(Link)
@Mock(Node)
class LinkSpec extends spock.lang.Specification {

	def "Links are simple objects"(){

		//
		// Test a standard object
		when: 'everything is fine'
		
		def link = new Link(refId: 11, name: "Special link")
		def source = new Node()
		def target = new Node()
		
		link.source = source
		link.target = target
		
		link.validate()
		
		then: 'the node should validate and contain the right things'
		
		!link.hasErrors()
		link.source == source
		link.target == target
		
	}
	
	def "nodes can't be null"(){
		when: 'source node is null'
		def link = new Link(refId: 11, name: "Special link")
		link.source = null
		link.target = new Node()
		link.validate()
		
		then: 'validation fails'
		!link.validate()
		link.errors.hasFieldErrors("source")
		
		//similarly
		when: 'target node is null'
		link = new Link(refId: 21, name: "Special link")
		link.source = new Node()
		link.target = null
		link.validate()
		
		then: 'validation fails'
		!link.validate()
		link.errors.hasFieldErrors("target")
		
		
	}
}
