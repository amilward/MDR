package uk.co.mdc.catalogue



import grails.test.mixin.*
import spock.lang.*

@TestFor(RelationshipTypeController)
@Mock(RelationshipType)
class RelationshipTypeControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The catalogue is correct"
            !model.relationshipTypeInstanceList
            model.relationshipTypeInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The catalogue is correctly created"
            model.relationshipTypeInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def relationshipType = new RelationshipType()
            relationshipType.validate()
            controller.save(relationshipType)

        then:"The create view is rendered again with the correct catalogue"
            model.relationshipTypeInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            relationshipType = new RelationshipType(params)

            controller.save(relationshipType)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/relationshipType/show/1'
            controller.flash.message != null
            RelationshipType.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def relationshipType = new RelationshipType(params)
            controller.show(relationshipType)

        then:"A catalogue is populated containing the domain instance"
            model.relationshipTypeInstance == relationshipType
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def relationshipType = new RelationshipType(params)
            controller.edit(relationshipType)

        then:"A catalogue is populated containing the domain instance"
            model.relationshipTypeInstance == relationshipType
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            status == 404

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def relationshipType = new RelationshipType()
            relationshipType.validate()
            controller.update(relationshipType)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.relationshipTypeInstance == relationshipType

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            relationshipType = new RelationshipType(params).save(flush: true)
            controller.update(relationshipType)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/relationshipType/show/$relationshipType.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            status == 404

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def relationshipType = new RelationshipType(params).save(flush: true)

        then:"It exists"
            RelationshipType.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(relationshipType)

        then:"The instance is deleted"
            RelationshipType.count() == 0
            response.redirectedUrl == '/relationshipType/index'
            flash.message != null
    }
}
