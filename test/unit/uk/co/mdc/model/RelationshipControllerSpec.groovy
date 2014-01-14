package uk.co.mdc.model



import grails.test.mixin.*
import spock.lang.*

@TestFor(RelationshipController)
@Mock(Relationship)
class RelationshipControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.relationshipInstanceList
            model.relationshipInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.relationshipInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def relationship = new Relationship()
            relationship.validate()
            controller.save(relationship)

        then:"The create view is rendered again with the correct model"
            model.relationshipInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            relationship = new Relationship(params)

            controller.save(relationship)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/relationship/show/1'
            controller.flash.message != null
            Relationship.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def relationship = new Relationship(params)
            controller.show(relationship)

        then:"A model is populated containing the domain instance"
            model.relationshipInstance == relationship
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def relationship = new Relationship(params)
            controller.edit(relationship)

        then:"A model is populated containing the domain instance"
            model.relationshipInstance == relationship
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            status == 404

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def relationship = new Relationship()
            relationship.validate()
            controller.update(relationship)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.relationshipInstance == relationship

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            relationship = new Relationship(params).save(flush: true)
            controller.update(relationship)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/relationship/show/$relationship.id"
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
            def relationship = new Relationship(params).save(flush: true)

        then:"It exists"
            Relationship.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(relationship)

        then:"The instance is deleted"
            Relationship.count() == 0
            response.redirectedUrl == '/relationship/index'
            flash.message != null
    }
}
