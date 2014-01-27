package uk.co.mdc.pathways

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import spock.lang.Specification


/**
 * Unit tests for the Pathway Service
 * Created by rb on 23/01/2014.
 */
@TestFor(PathwayService)
@Mock([ Pathway, Node, Link])
@TestMixin(DomainClassUnitTestMixin)
class PathwayServiceSpec extends Specification{

    def "topLevelPathways returns the correct number of draft pathways"(){
        when: "I create a single draft pathway"
        mockDomain(Pathway)
        def s = new Pathway(name:"Pathway 1", isDraft: true)
        s.save()

        then: "The pathway service returns a single pathway when queried for drafts"
        s != null
        Pathway.list().size() == 1
        service.topLevelPathways().size() == 1
        service.topLevelPathways([isDraft: true]).size() == 1

        and: "The pathway service returns nothing when queried for finalised pathways"
        service.topLevelPathways([isDraft: false]).size() == 0
    }

    def "topLevelPathways returns the correct number of finalised pathways"(){
        when: "I create two pathways and finalise them"
        new Pathway(name:"Pathway 1", isDraft: true).save()
        new Pathway(name:"Pathway 2", isDraft: false).save()
        new Pathway(name:"Pathway 3", isDraft: false).save()

        then: "The pathway service returns 3 pathways when queried for everything"
        Pathway.list().size() == 3
        service.topLevelPathways().size() == 3

        and: "There is 1 draft pathway"
        service.topLevelPathways([isDraft: true]).size() == 1

        and: "There are 2 finalised pathways"
        service.topLevelPathways([isDraft: false]).size() == 2
    }

    def "topLevelPathways doesn't include nodes, just pathways"(){
        when: "I create a single draft pathway"
        mockDomain(Pathway)
        Pathway p1 = new Pathway(name:"Pathway 1", isDraft: true).save()
        Node node1 = new Node(name:"Node 1").save()
        p1.addToNodes(node1)

        then: "The pathway service returns a single pathway when queried for drafts"
        Pathway.list().size() == 2
        service.topLevelPathways().size() == 1
    }

    /**
     * When a pathway is created
     * Then it is persisted
     *
     * When a pathway is deleted
     * and the user doesn't have delete permission
     * Then an exception is thrown
     *
     * When a pathway is deleted
     * and the user has the delete permission
     * The pathway is deleted
     *
     * When a pathway is updated
     * and the user doesn't have write permission
     * Then an exception is thrown
     *
     * When a pathways property is updated
     * and the user has write permission
     * Then the pathway is updated
     *
     * When a node is added to the pathway
     * and the user has write permission
     * Then the
     */
}
