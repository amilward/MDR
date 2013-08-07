package uk.co.mdc.model



import org.junit.*
import grails.test.mixin.*

@TestFor(CollectionController)
@Mock(Collection)
class CollectionControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/collection/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.collectionInstanceList.size() == 0
        assert model.collectionInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.collectionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.collectionInstance != null
        assert view == '/collection/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/collection/show/1'
        assert controller.flash.message != null
        assert Collection.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/collection/list'

        populateValidParams(params)
        def collection = new Collection(params)

        assert collection.save() != null

        params.id = collection.id

        def model = controller.show()

        assert model.collectionInstance == collection
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/collection/list'

        populateValidParams(params)
        def collection = new Collection(params)

        assert collection.save() != null

        params.id = collection.id

        def model = controller.edit()

        assert model.collectionInstance == collection
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/collection/list'

        response.reset()

        populateValidParams(params)
        def collection = new Collection(params)

        assert collection.save() != null

        // test invalid parameters in update
        params.id = collection.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/collection/edit"
        assert model.collectionInstance != null

        collection.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/collection/show/$collection.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        collection.clearErrors()

        populateValidParams(params)
        params.id = collection.id
        params.version = -1
        controller.update()

        assert view == "/collection/edit"
        assert model.collectionInstance != null
        assert model.collectionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/collection/list'

        response.reset()

        populateValidParams(params)
        def collection = new Collection(params)

        assert collection.save() != null
        assert Collection.count() == 1

        params.id = collection.id

        controller.delete()

        assert Collection.count() == 0
        assert Collection.get(collection.id) == null
        assert response.redirectedUrl == '/collection/list'
    }
}
