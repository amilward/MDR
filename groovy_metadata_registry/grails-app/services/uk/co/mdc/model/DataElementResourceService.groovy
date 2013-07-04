package uk.co.mdc.model

import org.grails.jaxrs.provider.DomainObjectNotFoundException

class DataElementResourceService {

    def create(DataElement dto) {
        dto.save()
    }

    def read(id) {
        def obj = DataElement.get(id)
        if (!obj) {
            throw new DomainObjectNotFoundException(DataElement.class, id)
        }
        obj
    }

    def readAll() {
        DataElement.findAll()
    }

    def update(DataElement dto) {
        def obj = DataElement.get(dto.id)
        if (!obj) {
            throw new DomainObjectNotFoundException(DataElement.class, dto.id)
        }
        obj.properties = dto.properties
        obj
    }

    void delete(id) {
        def obj = DataElement.get(id)
        if (obj) {
            obj.delete()
        }
    }
}
