package uk.co.mdc.pathways

import grails.rest.RestfulController

import grails.plugins.springsecurity.Secured
import grails.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_USER'])
class PathwayController extends RestfulController{

    def pathwayService

    PathwayController() {
        super(Pathway)
    }


    def show(Pathway pathway){
        // If we've asked to create a pathway, lets give that a go
        if(params.createPathway){
            pathway = pathwayService.create(params)
            if(pathway.hasErrors()){
                respond pathway.errors
            }
        }
        respond pathway
    }


    @Transactional
    def update(Pathway pathway){
        if(pathway == null){
            render status: NOT_FOUND
        }

        pathwayService.update(pathway)
        if (pathway.hasErrors()) {
            respond pathway.errors // if you have a static edit page add ", view: 'edit'"
        }
        respond pathway
    }

    def delete(Long id) {
        def pathway = findInstance(id)

        def model
        def msg

        if (!pathway) {
            msg = message(code: 'default.not.found.message', args: [message(code: 'pathway.label', default: 'Pathway'), id])
            model = [errors: true, details: msg]
            respond model
        }

        try {
            pathwayService.delete(pathway)
            msg = message(code: 'default.deleted.message', args: [message(code: 'pathway.label', default: 'Pathway'), id])
            model = [success: true, details: msg]
        }
        catch (DataIntegrityViolationException e) {
            msg = message(code: 'default.not.deleted.message', args: [message(code: 'pathway.label', default: 'Pathway'), id])
            model = [errors: true, details: msg]
        }

        respond model
    }

    /**
     * Utility method to return the current pathway instance. Uses PathwayService to honor security.
     * @return the pathway or null.
     */
    private Pathway findInstance() {
        return findInstance(params.long('id'))
    }

    /**
     * Utility method to return the current pathway instance. Uses PathwayService to honour security.
     * @return the pathway or null.
     */
    private Pathway findInstance(Long id) {
        def pathway = pathwayService.get(id)
        if (!pathway) {
            flash.message = "Pathway not found with id $params.id"
            redirect action: list
        }
        pathway
    }
}
