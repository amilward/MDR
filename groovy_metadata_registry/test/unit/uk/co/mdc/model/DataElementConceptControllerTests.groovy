package uk.co.mdc.model



import org.junit.*
import grails.test.mixin.*

@TestFor(DataElementConceptController)
@Mock(DataElementConcept)
class DataElementConceptControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/dataElementConcept/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.dataElementConceptInstanceList.size() == 0
        assert model.dataElementConceptInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.dataElementConceptInstance != null
    }

    void testSave() {
        controller.save()

        assert model.dataElementConceptInstance != null
        assert view == '/dataElementConcept/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/dataElementConcept/show/1'
        assert controller.flash.message != null
        assert DataElementConcept.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/dataElementConcept/list'

        populateValidParams(params)
        def dataElementConcept = new DataElementConcept(params)

        assert dataElementConcept.save() != null

        params.id = dataElementConcept.id

        def model = controller.show()

        assert model.dataElementConceptInstance == dataElementConcept
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/dataElementConcept/list'

        populateValidParams(params)
        def dataElementConcept = new DataElementConcept(params)

        assert dataElementConcept.save() != null

        params.id = dataElementConcept.id

        def model = controller.edit()

        assert model.dataElementConceptInstance == dataElementConcept
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/dataElementConcept/list'

        response.reset()

        populateValidParams(params)
        def dataElementConcept = new DataElementConcept(params)

        assert dataElementConcept.save() != null

        // test invalid parameters in update
        params.id = dataElementConcept.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/dataElementConcept/edit"
        assert model.dataElementConceptInstance != null

        dataElementConcept.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/dataElementConcept/show/$dataElementConcept.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        dataElementConcept.clearErrors()

        populateValidParams(params)
        params.id = dataElementConcept.id
        params.version = -1
        controller.update()

        assert view == "/dataElementConcept/edit"
        assert model.dataElementConceptInstance != null
        assert model.dataElementConceptInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/dataElementConcept/list'

        response.reset()

        populateValidParams(params)
        def dataElementConcept = new DataElementConcept(params)

        assert dataElementConcept.save() != null
        assert DataElementConcept.count() == 1

        params.id = dataElementConcept.id

        controller.delete()

        assert DataElementConcept.count() == 0
        assert DataElementConcept.get(dataElementConcept.id) == null
        assert response.redirectedUrl == '/dataElementConcept/list'
    }
}
