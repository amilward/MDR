package uk.co.mdc.model

import org.grails.jaxrs.provider.DomainObjectNotFoundException

class ValueDomainResourceService {

    def create(ValueDomain dto) {
        dto.save()
    }

    def read(id) {
        def obj = ValueDomain.get(id)
        if (!obj) {
            throw new DomainObjectNotFoundException(ValueDomain.class, id)
        }
        obj
    }

    def readAll() {
        ValueDomain.findAll()
    }

    def update(ValueDomain dto) {
        def obj = ValueDomain.get(dto.id)
        if (!obj) {
            throw new DomainObjectNotFoundException(ValueDomain.class, dto.id)
        }
        obj.properties = dto.properties
        obj
    }

    void delete(id) {
        def obj = ValueDomain.get(id)
        if (obj) {
            obj.delete()
        }
    }
}
