package uk.co.mdc.model



import org.junit.*
import grails.test.mixin.*

@TestFor(ConceptualDomainController)
@Mock(ConceptualDomain)
class ConceptualDomainControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/conceptualDomain/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.conceptualDomainInstanceList.size() == 0
        assert model.conceptualDomainInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.conceptualDomainInstance != null
    }

    void testSave() {
        controller.save()

        assert model.conceptualDomainInstance != null
        assert view == '/conceptualDomain/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/conceptualDomain/show/1'
        assert controller.flash.message != null
        assert ConceptualDomain.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/conceptualDomain/list'

        populateValidParams(params)
        def conceptualDomain = new ConceptualDomain(params)

        assert conceptualDomain.save() != null

        params.id = conceptualDomain.id

        def model = controller.show()

        assert model.conceptualDomainInstance == conceptualDomain
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/conceptualDomain/list'

        populateValidParams(params)
        def conceptualDomain = new ConceptualDomain(params)

        assert conceptualDomain.save() != null

        params.id = conceptualDomain.id

        def model = controller.edit()

        assert model.conceptualDomainInstance == conceptualDomain
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/conceptualDomain/list'

        response.reset()

        populateValidParams(params)
        def conceptualDomain = new ConceptualDomain(params)

        assert conceptualDomain.save() != null

        // test invalid parameters in update
        params.id = conceptualDomain.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/conceptualDomain/edit"
        assert model.conceptualDomainInstance != null

        conceptualDomain.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/conceptualDomain/show/$conceptualDomain.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        conceptualDomain.clearErrors()

        populateValidParams(params)
        params.id = conceptualDomain.id
        params.version = -1
        controller.update()

        assert view == "/conceptualDomain/edit"
        assert model.conceptualDomainInstance != null
        assert model.conceptualDomainInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/conceptualDomain/list'

        response.reset()

        populateValidParams(params)
        def conceptualDomain = new ConceptualDomain(params)

        assert conceptualDomain.save() != null
        assert ConceptualDomain.count() == 1

        params.id = conceptualDomain.id

        controller.delete()

        assert ConceptualDomain.count() == 0
        assert ConceptualDomain.get(conceptualDomain.id) == null
        assert response.redirectedUrl == '/conceptualDomain/list'
    }
}
