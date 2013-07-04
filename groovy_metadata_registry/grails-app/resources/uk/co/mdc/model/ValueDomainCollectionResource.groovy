package uk.co.mdc.model

import static org.grails.jaxrs.response.Responses.*

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.POST
import javax.ws.rs.core.Response

@Path('/api/valueDomain')
@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class ValueDomainCollectionResource {

    def valueDomainResourceService

    @POST
    Response create(ValueDomain dto) {
        created valueDomainResourceService.create(dto)
    }

    @GET
    Response readAll() {
        ok valueDomainResourceService.readAll()
    }

    @Path('/{id}')
    ValueDomainResource getResource(@PathParam('id') Long id) {
        new ValueDomainResource(valueDomainResourceService: valueDomainResourceService, id:id)
    }
}
