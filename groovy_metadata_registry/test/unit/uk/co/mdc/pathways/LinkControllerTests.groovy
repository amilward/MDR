package uk.co.mdc.pathways



import org.junit.*
import grails.test.mixin.*

@TestFor(LinkController)
@Mock(Link)
class LinkControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/link/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.linkInstanceList.size() == 0
        assert model.linkInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.linkInstance != null
    }

    void testSave() {
        controller.save()

        assert model.linkInstance != null
        assert view == '/link/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/link/show/1'
        assert controller.flash.message != null
        assert Link.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/link/list'

        populateValidParams(params)
        def link = new Link(params)

        assert link.save() != null

        params.id = link.id

        def model = controller.show()

        assert model.linkInstance == link
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/link/list'

        populateValidParams(params)
        def link = new Link(params)

        assert link.save() != null

        params.id = link.id

        def model = controller.edit()

        assert model.linkInstance == link
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/link/list'

        response.reset()

        populateValidParams(params)
        def link = new Link(params)

        assert link.save() != null

        // test invalid parameters in update
        params.id = link.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/link/edit"
        assert model.linkInstance != null

        link.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/link/show/$link.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        link.clearErrors()

        populateValidParams(params)
        params.id = link.id
        params.version = -1
        controller.update()

        assert view == "/link/edit"
        assert model.linkInstance != null
        assert model.linkInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/link/list'

        response.reset()

        populateValidParams(params)
        def link = new Link(params)

        assert link.save() != null
        assert Link.count() == 1

        params.id = link.id

        controller.delete()

        assert Link.count() == 0
        assert Link.get(link.id) == null
        assert response.redirectedUrl == '/link/list'
    }
}
