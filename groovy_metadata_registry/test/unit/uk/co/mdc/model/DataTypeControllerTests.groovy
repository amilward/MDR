package uk.co.mdc.model



import org.junit.*
import grails.test.mixin.*

@TestFor(DataTypeController)
@Mock(DataType)
class DataTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/dataType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.dataTypeInstanceList.size() == 0
        assert model.dataTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.dataTypeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.dataTypeInstance != null
        assert view == '/dataType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/dataType/show/1'
        assert controller.flash.message != null
        assert DataType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/dataType/list'

        populateValidParams(params)
        def dataType = new DataType(params)

        assert dataType.save() != null

        params.id = dataType.id

        def model = controller.show()

        assert model.dataTypeInstance == dataType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/dataType/list'

        populateValidParams(params)
        def dataType = new DataType(params)

        assert dataType.save() != null

        params.id = dataType.id

        def model = controller.edit()

        assert model.dataTypeInstance == dataType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/dataType/list'

        response.reset()

        populateValidParams(params)
        def dataType = new DataType(params)

        assert dataType.save() != null

        // test invalid parameters in update
        params.id = dataType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/dataType/edit"
        assert model.dataTypeInstance != null

        dataType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/dataType/show/$dataType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        dataType.clearErrors()

        populateValidParams(params)
        params.id = dataType.id
        params.version = -1
        controller.update()

        assert view == "/dataType/edit"
        assert model.dataTypeInstance != null
        assert model.dataTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/dataType/list'

        response.reset()

        populateValidParams(params)
        def dataType = new DataType(params)

        assert dataType.save() != null
        assert DataType.count() == 1

        params.id = dataType.id

        controller.delete()

        assert DataType.count() == 0
        assert DataType.get(dataType.id) == null
        assert response.redirectedUrl == '/dataType/list'
    }
}
