package uk.co.mdc.model



import org.junit.*
import grails.test.mixin.*

@TestFor(ExternalSynonymController)
@Mock(ExternalSynonym)
class ExternalSynonymControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/externalSynonym/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.externalSynonymInstanceList.size() == 0
        assert model.externalSynonymInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.externalSynonymInstance != null
    }

    void testSave() {
        controller.save()

        assert model.externalSynonymInstance != null
        assert view == '/externalSynonym/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/externalSynonym/show/1'
        assert controller.flash.message != null
        assert ExternalSynonym.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/externalSynonym/list'

        populateValidParams(params)
        def externalSynonym = new ExternalSynonym(params)

        assert externalSynonym.save() != null

        params.id = externalSynonym.id

        def model = controller.show()

        assert model.externalSynonymInstance == externalSynonym
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/externalSynonym/list'

        populateValidParams(params)
        def externalSynonym = new ExternalSynonym(params)

        assert externalSynonym.save() != null

        params.id = externalSynonym.id

        def model = controller.edit()

        assert model.externalSynonymInstance == externalSynonym
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/externalSynonym/list'

        response.reset()

        populateValidParams(params)
        def externalSynonym = new ExternalSynonym(params)

        assert externalSynonym.save() != null

        // test invalid parameters in update
        params.id = externalSynonym.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/externalSynonym/edit"
        assert model.externalSynonymInstance != null

        externalSynonym.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/externalSynonym/show/$externalSynonym.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        externalSynonym.clearErrors()

        populateValidParams(params)
        params.id = externalSynonym.id
        params.version = -1
        controller.update()

        assert view == "/externalSynonym/edit"
        assert model.externalSynonymInstance != null
        assert model.externalSynonymInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/externalSynonym/list'

        response.reset()

        populateValidParams(params)
        def externalSynonym = new ExternalSynonym(params)

        assert externalSynonym.save() != null
        assert ExternalSynonym.count() == 1

        params.id = externalSynonym.id

        controller.delete()

        assert ExternalSynonym.count() == 0
        assert ExternalSynonym.get(externalSynonym.id) == null
        assert response.redirectedUrl == '/externalSynonym/list'
    }
}
