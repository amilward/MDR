package uk.co.mdc.model

import static org.grails.jaxrs.response.Responses.*

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.POST
import javax.ws.rs.core.Response

@Path('/api/conceptualDomain')
@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class ConceptualDomainCollectionResource {

    def conceptualDomainResourceService

    @POST
    Response create(ConceptualDomain dto) {
        created conceptualDomainResourceService.create(dto)
    }

    @GET
    Response readAll() {
        ok conceptualDomainResourceService.readAll()
    }

    @Path('/{id}')
    ConceptualDomainResource getResource(@PathParam('id') Long id) {
        new ConceptualDomainResource(conceptualDomainResourceService: conceptualDomainResourceService, id:id)
    }
}
