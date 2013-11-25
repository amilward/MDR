
package uk.co.mdc.pathways

import grails.test.mixin.*

import uk.co.mdc.model.Collection

/**
 * Simple unit tests for pathway nodes
 * @author Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
 *
 */
@TestFor(Node)
@Mock(Collection)
class NodeSpec extends spock.lang.Specification {

	def "Nodes are simple objects with getters and setters"(){

		setup:
		def node1

		def expected = [
			refId: 124152,
			name: "Bill",
			desc: "One half of the flowerpot men",
			x: 10,
			y: 15,
			peCollection: new Collection()
		]
		
		//
		// Test a standard object
		when: 'everything is fine'
		node1 = new Node(expected)

		then: 'the node should validate and contain the right things'
		node1.validate()
		!node1.hasErrors()
		node1.name == expected.name
		node1.description == expected.description
		node1.x.toInteger() == expected.x
		node1.y.toInteger() == expected.y
		node1.peCollection == expected.peCollection

		//
		// Ensure name can't be null
		when: 'name is null'
		expected.name = null
		node1 = new Node(expected)
		
		then:'the object should fail validation'
		!node1.validate()
		node1.errors.hasFieldErrors("name")
		
		//
		// But x and y as we;; as pathwaysModel
		when: 'x and y are null'
		expected.name = "Bob"
		expected.x = null
		expected.y = null
		node1 = new Node(expected)
		
		then: 'it is all fine'
		node1.validate()
		!node1.hasErrors()
	}
}
