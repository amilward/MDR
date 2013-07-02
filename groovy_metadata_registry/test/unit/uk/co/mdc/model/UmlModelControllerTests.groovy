package uk.co.mdc.model



import org.junit.*
import grails.test.mixin.*

@TestFor(UmlModelController)
@Mock(UmlModel)
class UmlModelControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/umlModel/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.umlModelInstanceList.size() == 0
        assert model.umlModelInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.umlModelInstance != null
    }

    void testSave() {
        controller.save()

        assert model.umlModelInstance != null
        assert view == '/umlModel/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/umlModel/show/1'
        assert controller.flash.message != null
        assert UmlModel.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/umlModel/list'

        populateValidParams(params)
        def umlModel = new UmlModel(params)

        assert umlModel.save() != null

        params.id = umlModel.id

        def model = controller.show()

        assert model.umlModelInstance == umlModel
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/umlModel/list'

        populateValidParams(params)
        def umlModel = new UmlModel(params)

        assert umlModel.save() != null

        params.id = umlModel.id

        def model = controller.edit()

        assert model.umlModelInstance == umlModel
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/umlModel/list'

        response.reset()

        populateValidParams(params)
        def umlModel = new UmlModel(params)

        assert umlModel.save() != null

        // test invalid parameters in update
        params.id = umlModel.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/umlModel/edit"
        assert model.umlModelInstance != null

        umlModel.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/umlModel/show/$umlModel.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        umlModel.clearErrors()

        populateValidParams(params)
        params.id = umlModel.id
        params.version = -1
        controller.update()

        assert view == "/umlModel/edit"
        assert model.umlModelInstance != null
        assert model.umlModelInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/umlModel/list'

        response.reset()

        populateValidParams(params)
        def umlModel = new UmlModel(params)

        assert umlModel.save() != null
        assert UmlModel.count() == 1

        params.id = umlModel.id

        controller.delete()

        assert UmlModel.count() == 0
        assert UmlModel.get(umlModel.id) == null
        assert response.redirectedUrl == '/umlModel/list'
    }
}
