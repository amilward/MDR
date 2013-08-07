package uk.co.mdc.model

import org.grails.jaxrs.provider.DomainObjectNotFoundException

class DataTypeResourceService {

    def create(Map dto) {


		
		
        //dto.save()
    }

    def read(id) {
        def obj = DataType.get(id)
        if (!obj) {
            throw new DomainObjectNotFoundException(DataType.class, id)
        }
        obj
    }

    def readAll() {
        DataType.findAll()
    }

    def update(DataType dto) {
        def obj = DataType.get(dto.id)
        if (!obj) {
            throw new DomainObjectNotFoundException(DataType.class, dto.id)
        }
        obj.properties = dto.properties
        obj
    }

    void delete(id) {
        def obj = DataType.get(id)
        if (obj) {
            obj.delete()
        }
    }
}
