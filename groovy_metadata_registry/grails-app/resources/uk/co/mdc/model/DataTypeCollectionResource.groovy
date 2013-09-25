package uk.co.mdc.model

import static org.grails.jaxrs.response.Responses.*

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.POST
import javax.ws.rs.core.Response

@Path('/api/dataType')
@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class DataTypeCollectionResource {

    def dataTypeResourceService

    @POST
    Response create(Map dto) {
		println(dto)
		//created dataTypeResourceService.create(dto)
    }

    @GET
    Response readAll() {
        ok dataTypeResourceService.readAll()
    }

    @Path('/{id}')
    DataTypeResource getResource(@PathParam('id') Long id) {
        new DataTypeResource(dataTypeResourceService: dataTypeResourceService, id:id)
    }
	
}
