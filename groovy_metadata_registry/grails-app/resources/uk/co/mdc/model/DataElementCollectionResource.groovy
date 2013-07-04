package uk.co.mdc.model

import static org.grails.jaxrs.response.Responses.*

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.POST
import javax.ws.rs.core.Response

@Path('/api/dataElement')
@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class DataElementCollectionResource {

    def dataElementResourceService

    @POST
    Response create(DataElement dto) {
        created dataElementResourceService.create(dto)
    }

    @GET
    Response readAll() {
        ok dataElementResourceService.readAll()
    }

    @Path('/{id}')
    DataElementResource getResource(@PathParam('id') Long id) {
        new DataElementResource(dataElementResourceService: dataElementResourceService, id:id)
    }
}
