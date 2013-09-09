package uk.co.mdc.model



import org.junit.*
import grails.test.mixin.*

@TestFor(ExternalReferenceController)
@Mock(ExternalReference)
class ExternalReferenceControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/externalReference/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.externalReferenceInstanceList.size() == 0
        assert model.externalReferenceInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.externalReferenceInstance != null
    }

    void testSave() {
        controller.save()

        assert model.externalReferenceInstance != null
        assert view == '/externalReference/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/externalReference/show/1'
        assert controller.flash.message != null
        assert ExternalReference.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/externalReference/list'

        populateValidParams(params)
        def externalReference = new ExternalReference(params)

        assert externalReference.save() != null

        params.id = externalReference.id

        def model = controller.show()

        assert model.externalReferenceInstance == externalReference
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/externalReference/list'

        populateValidParams(params)
        def externalReference = new ExternalReference(params)

        assert externalReference.save() != null

        params.id = externalReference.id

        def model = controller.edit()

        assert model.externalReferenceInstance == externalReference
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/externalReference/list'

        response.reset()

        populateValidParams(params)
        def externalReference = new ExternalReference(params)

        assert externalReference.save() != null

        // test invalid parameters in update
        params.id = externalReference.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/externalReference/edit"
        assert model.externalReferenceInstance != null

        externalReference.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/externalReference/show/$externalReference.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        externalReference.clearErrors()

        populateValidParams(params)
        params.id = externalReference.id
        params.version = -1
        controller.update()

        assert view == "/externalReference/edit"
        assert model.externalReferenceInstance != null
        assert model.externalReferenceInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/externalReference/list'

        response.reset()

        populateValidParams(params)
        def externalReference = new ExternalReference(params)

        assert externalReference.save() != null
        assert ExternalReference.count() == 1

        params.id = externalReference.id

        controller.delete()

        assert ExternalReference.count() == 0
        assert ExternalReference.get(externalReference.id) == null
        assert response.redirectedUrl == '/externalReference/list'
    }
}
