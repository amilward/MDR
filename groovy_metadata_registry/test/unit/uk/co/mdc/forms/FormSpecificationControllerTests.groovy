package uk.co.mdc.forms



import org.junit.*
import grails.test.mixin.*

@TestFor(FormSpecificationController)
@Mock(FormSpecification)
class FormSpecificationControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/formSpecification/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.formSpecificationInstanceList.size() == 0
        assert model.formSpecificationInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.formSpecificationInstance != null
    }

    void testSave() {
        controller.save()

        assert model.formSpecificationInstance != null
        assert view == '/formSpecification/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/formSpecification/show/1'
        assert controller.flash.message != null
        assert FormSpecification.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/formSpecification/list'

        populateValidParams(params)
        def formSpecification = new FormSpecification(params)

        assert formSpecification.save() != null

        params.id = formSpecification.id

        def model = controller.show()

        assert model.formSpecificationInstance == formSpecification
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/formSpecification/list'

        populateValidParams(params)
        def formSpecification = new FormSpecification(params)

        assert formSpecification.save() != null

        params.id = formSpecification.id

        def model = controller.edit()

        assert model.formSpecificationInstance == formSpecification
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/formSpecification/list'

        response.reset()

        populateValidParams(params)
        def formSpecification = new FormSpecification(params)

        assert formSpecification.save() != null

        // test invalid parameters in update
        params.id = formSpecification.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/formSpecification/edit"
        assert model.formSpecificationInstance != null

        formSpecification.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/formSpecification/show/$formSpecification.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        formSpecification.clearErrors()

        populateValidParams(params)
        params.id = formSpecification.id
        params.version = -1
        controller.update()

        assert view == "/formSpecification/edit"
        assert model.formSpecificationInstance != null
        assert model.formSpecificationInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/formSpecification/list'

        response.reset()

        populateValidParams(params)
        def formSpecification = new FormSpecification(params)

        assert formSpecification.save() != null
        assert FormSpecification.count() == 1

        params.id = formSpecification.id

        controller.delete()

        assert FormSpecification.count() == 0
        assert FormSpecification.get(formSpecification.id) == null
        assert response.redirectedUrl == '/formSpecification/list'
    }
}
