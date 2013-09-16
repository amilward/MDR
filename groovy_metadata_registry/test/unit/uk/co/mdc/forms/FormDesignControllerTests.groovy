package uk.co.mdc.forms



import org.junit.*
import grails.test.mixin.*

@TestFor(FormDesignController)
@Mock(FormDesign)
class FormDesignControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/formDesign/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.formDesignInstanceList.size() == 0
        assert model.formDesignInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.formDesignInstance != null
    }

    void testSave() {
        controller.save()

        assert model.formDesignInstance != null
        assert view == '/formDesign/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/formDesign/show/1'
        assert controller.flash.message != null
        assert FormDesign.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/formDesign/list'

        populateValidParams(params)
        def formDesign = new FormDesign(params)

        assert formDesign.save() != null

        params.id = formDesign.id

        def model = controller.show()

        assert model.formDesignInstance == formDesign
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/formDesign/list'

        populateValidParams(params)
        def formDesign = new FormDesign(params)

        assert formDesign.save() != null

        params.id = formDesign.id

        def model = controller.edit()

        assert model.formDesignInstance == formDesign
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/formDesign/list'

        response.reset()

        populateValidParams(params)
        def formDesign = new FormDesign(params)

        assert formDesign.save() != null

        // test invalid parameters in update
        params.id = formDesign.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/formDesign/edit"
        assert model.formDesignInstance != null

        formDesign.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/formDesign/show/$formDesign.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        formDesign.clearErrors()

        populateValidParams(params)
        params.id = formDesign.id
        params.version = -1
        controller.update()

        assert view == "/formDesign/edit"
        assert model.formDesignInstance != null
        assert model.formDesignInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/formDesign/list'

        response.reset()

        populateValidParams(params)
        def formDesign = new FormDesign(params)

        assert formDesign.save() != null
        assert FormDesign.count() == 1

        params.id = formDesign.id

        controller.delete()

        assert FormDesign.count() == 0
        assert FormDesign.get(formDesign.id) == null
        assert response.redirectedUrl == '/formDesign/list'
    }
}
