package uk.co.mdc.model



import org.junit.*
import grails.test.mixin.*

@TestFor(ValueDomainController)
@Mock(ValueDomain)
class ValueDomainControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/valueDomain/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.valueDomainInstanceList.size() == 0
        assert model.valueDomainInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.valueDomainInstance != null
    }

    void testSave() {
        controller.save()

        assert model.valueDomainInstance != null
        assert view == '/valueDomain/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/valueDomain/show/1'
        assert controller.flash.message != null
        assert ValueDomain.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/valueDomain/list'

        populateValidParams(params)
        def valueDomain = new ValueDomain(params)

        assert valueDomain.save() != null

        params.id = valueDomain.id

        def model = controller.show()

        assert model.valueDomainInstance == valueDomain
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/valueDomain/list'

        populateValidParams(params)
        def valueDomain = new ValueDomain(params)

        assert valueDomain.save() != null

        params.id = valueDomain.id

        def model = controller.edit()

        assert model.valueDomainInstance == valueDomain
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/valueDomain/list'

        response.reset()

        populateValidParams(params)
        def valueDomain = new ValueDomain(params)

        assert valueDomain.save() != null

        // test invalid parameters in update
        params.id = valueDomain.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/valueDomain/edit"
        assert model.valueDomainInstance != null

        valueDomain.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/valueDomain/show/$valueDomain.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        valueDomain.clearErrors()

        populateValidParams(params)
        params.id = valueDomain.id
        params.version = -1
        controller.update()

        assert view == "/valueDomain/edit"
        assert model.valueDomainInstance != null
        assert model.valueDomainInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/valueDomain/list'

        response.reset()

        populateValidParams(params)
        def valueDomain = new ValueDomain(params)

        assert valueDomain.save() != null
        assert ValueDomain.count() == 1

        params.id = valueDomain.id

        controller.delete()

        assert ValueDomain.count() == 0
        assert ValueDomain.get(valueDomain.id) == null
        assert response.redirectedUrl == '/valueDomain/list'
    }
}
