package uk.co.mdc.forms



import org.junit.*
import grails.test.mixin.*

@TestFor(FieldController)
@Mock(Field)
class FieldControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/field/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.fieldInstanceList.size() == 0
        assert model.fieldInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.fieldInstance != null
    }

    void testSave() {
        controller.save()

        assert model.fieldInstance != null
        assert view == '/field/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/field/show/1'
        assert controller.flash.message != null
        assert Field.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/field/list'

        populateValidParams(params)
        def field = new Field(params)

        assert field.save() != null

        params.id = field.id

        def model = controller.show()

        assert model.fieldInstance == field
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/field/list'

        populateValidParams(params)
        def field = new Field(params)

        assert field.save() != null

        params.id = field.id

        def model = controller.edit()

        assert model.fieldInstance == field
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/field/list'

        response.reset()

        populateValidParams(params)
        def field = new Field(params)

        assert field.save() != null

        // test invalid parameters in update
        params.id = field.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/field/edit"
        assert model.fieldInstance != null

        field.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/field/show/$field.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        field.clearErrors()

        populateValidParams(params)
        params.id = field.id
        params.version = -1
        controller.update()

        assert view == "/field/edit"
        assert model.fieldInstance != null
        assert model.fieldInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/field/list'

        response.reset()

        populateValidParams(params)
        def field = new Field(params)

        assert field.save() != null
        assert Field.count() == 1

        params.id = field.id

        controller.delete()

        assert Field.count() == 0
        assert Field.get(field.id) == null
        assert response.redirectedUrl == '/field/list'
    }
}
