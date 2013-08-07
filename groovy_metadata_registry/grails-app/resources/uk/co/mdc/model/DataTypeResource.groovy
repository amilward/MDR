package uk.co.mdc.model

import static org.grails.jaxrs.response.Responses.*

import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.PUT
import javax.ws.rs.core.Response

import org.grails.jaxrs.provider.DomainObjectNotFoundException

@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class DataTypeResource {

    def dataTypeResourceService
    def id

    @GET
    Response read() {
        ok dataTypeResourceService.read(id)
    }

    @PUT
    Response update(DataType dto) {
        dto.id = id
        ok dataTypeResourceService.update(dto)
    }

    @DELETE
    void delete() {
        dataTypeResourceService.delete(id)
    }
}
