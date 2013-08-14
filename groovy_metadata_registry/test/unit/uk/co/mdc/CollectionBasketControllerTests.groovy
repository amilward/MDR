package uk.co.mdc



import org.junit.*
import grails.test.mixin.*

@TestFor(CollectionBasketController)
@Mock(CollectionBasket)
class CollectionBasketControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/collectionBasket/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.collectionBasketInstanceList.size() == 0
        assert model.collectionBasketInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.collectionBasketInstance != null
    }

    void testSave() {
        controller.save()

        assert model.collectionBasketInstance != null
        assert view == '/collectionBasket/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/collectionBasket/show/1'
        assert controller.flash.message != null
        assert CollectionBasket.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/collectionBasket/list'

        populateValidParams(params)
        def collectionBasket = new CollectionBasket(params)

        assert collectionBasket.save() != null

        params.id = collectionBasket.id

        def model = controller.show()

        assert model.collectionBasketInstance == collectionBasket
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/collectionBasket/list'

        populateValidParams(params)
        def collectionBasket = new CollectionBasket(params)

        assert collectionBasket.save() != null

        params.id = collectionBasket.id

        def model = controller.edit()

        assert model.collectionBasketInstance == collectionBasket
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/collectionBasket/list'

        response.reset()

        populateValidParams(params)
        def collectionBasket = new CollectionBasket(params)

        assert collectionBasket.save() != null

        // test invalid parameters in update
        params.id = collectionBasket.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/collectionBasket/edit"
        assert model.collectionBasketInstance != null

        collectionBasket.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/collectionBasket/show/$collectionBasket.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        collectionBasket.clearErrors()

        populateValidParams(params)
        params.id = collectionBasket.id
        params.version = -1
        controller.update()

        assert view == "/collectionBasket/edit"
        assert model.collectionBasketInstance != null
        assert model.collectionBasketInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/collectionBasket/list'

        response.reset()

        populateValidParams(params)
        def collectionBasket = new CollectionBasket(params)

        assert collectionBasket.save() != null
        assert CollectionBasket.count() == 1

        params.id = collectionBasket.id

        controller.delete()

        assert CollectionBasket.count() == 0
        assert CollectionBasket.get(collectionBasket.id) == null
        assert response.redirectedUrl == '/collectionBasket/list'
    }
}
