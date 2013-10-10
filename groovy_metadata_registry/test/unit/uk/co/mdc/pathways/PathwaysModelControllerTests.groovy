package uk.co.mdc.pathways



import org.junit.*
import grails.test.mixin.*

@TestFor(PathwaysModelController)
@Mock(PathwaysModel)
class PathwaysModelControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/pathwaysModel/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.pathwaysModelInstanceList.size() == 0
        assert model.pathwaysModelInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.pathwaysModelInstance != null
    }

    void testSave() {
        controller.save()

        assert model.pathwaysModelInstance != null
        assert view == '/pathwaysModel/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/pathwaysModel/show/1'
        assert controller.flash.message != null
        assert PathwaysModel.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/pathwaysModel/list'

        populateValidParams(params)
        def pathwaysModel = new PathwaysModel(params)

        assert pathwaysModel.save() != null

        params.id = pathwaysModel.id

        def model = controller.show()

        assert model.pathwaysModelInstance == pathwaysModel
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/pathwaysModel/list'

        populateValidParams(params)
        def pathwaysModel = new PathwaysModel(params)

        assert pathwaysModel.save() != null

        params.id = pathwaysModel.id

        def model = controller.edit()

        assert model.pathwaysModelInstance == pathwaysModel
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/pathwaysModel/list'

        response.reset()

        populateValidParams(params)
        def pathwaysModel = new PathwaysModel(params)

        assert pathwaysModel.save() != null

        // test invalid parameters in update
        params.id = pathwaysModel.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/pathwaysModel/edit"
        assert model.pathwaysModelInstance != null

        pathwaysModel.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/pathwaysModel/show/$pathwaysModel.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        pathwaysModel.clearErrors()

        populateValidParams(params)
        params.id = pathwaysModel.id
        params.version = -1
        controller.update()

        assert view == "/pathwaysModel/edit"
        assert model.pathwaysModelInstance != null
        assert model.pathwaysModelInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/pathwaysModel/list'

        response.reset()

        populateValidParams(params)
        def pathwaysModel = new PathwaysModel(params)

        assert pathwaysModel.save() != null
        assert PathwaysModel.count() == 1

        params.id = pathwaysModel.id

        controller.delete()

        assert PathwaysModel.count() == 0
        assert PathwaysModel.get(pathwaysModel.id) == null
        assert response.redirectedUrl == '/pathwaysModel/list'
    }
}
