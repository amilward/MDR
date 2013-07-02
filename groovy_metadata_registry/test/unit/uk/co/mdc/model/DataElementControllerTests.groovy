package uk.co.mdc.model



import org.junit.*
import grails.test.mixin.*

@TestFor(DataElementController)
@Mock(DataElement)
class DataElementControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/dataElement/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.dataElementInstanceList.size() == 0
        assert model.dataElementInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.dataElementInstance != null
    }

    void testSave() {
        controller.save()

        assert model.dataElementInstance != null
        assert view == '/dataElement/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/dataElement/show/1'
        assert controller.flash.message != null
        assert DataElement.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/dataElement/list'

        populateValidParams(params)
        def dataElement = new DataElement(params)

        assert dataElement.save() != null

        params.id = dataElement.id

        def model = controller.show()

        assert model.dataElementInstance == dataElement
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/dataElement/list'

        populateValidParams(params)
        def dataElement = new DataElement(params)

        assert dataElement.save() != null

        params.id = dataElement.id

        def model = controller.edit()

        assert model.dataElementInstance == dataElement
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/dataElement/list'

        response.reset()

        populateValidParams(params)
        def dataElement = new DataElement(params)

        assert dataElement.save() != null

        // test invalid parameters in update
        params.id = dataElement.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/dataElement/edit"
        assert model.dataElementInstance != null

        dataElement.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/dataElement/show/$dataElement.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        dataElement.clearErrors()

        populateValidParams(params)
        params.id = dataElement.id
        params.version = -1
        controller.update()

        assert view == "/dataElement/edit"
        assert model.dataElementInstance != null
        assert model.dataElementInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/dataElement/list'

        response.reset()

        populateValidParams(params)
        def dataElement = new DataElement(params)

        assert dataElement.save() != null
        assert DataElement.count() == 1

        params.id = dataElement.id

        controller.delete()

        assert DataElement.count() == 0
        assert DataElement.get(dataElement.id) == null
        assert response.redirectedUrl == '/dataElement/list'
    }
}
