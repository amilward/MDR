package uk.co.mdc.model

import org.grails.jaxrs.provider.DomainObjectNotFoundException

class ConceptualDomainResourceService {

    def create(ConceptualDomain dto) {
        dto.save()
    }

    def read(id) {
        def obj = ConceptualDomain.get(id)
        if (!obj) {
            throw new DomainObjectNotFoundException(ConceptualDomain.class, id)
        }
        obj
    }

    def readAll() {
        ConceptualDomain.findAll()
    }

    def update(ConceptualDomain dto) {
        def obj = ConceptualDomain.get(dto.id)
        if (!obj) {
            throw new DomainObjectNotFoundException(ConceptualDomain.class, dto.id)
        }
        obj.properties = dto.properties
        obj
    }

    void delete(id) {
        def obj = ConceptualDomain.get(id)
        if (obj) {
            obj.delete()
        }
    }
}
