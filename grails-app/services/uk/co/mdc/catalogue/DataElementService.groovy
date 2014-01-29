package uk.co.mdc.catalogue

import grails.converters.JSON
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional
import grails.validation.ValidationException
import uk.co.mdc.catalogue.CatalogueElement
import uk.co.mdc.catalogue.DataElement

import static org.springframework.http.HttpStatus.NOT_FOUND


/* *********************************************************************
 * This service allows the user to access the data element catalogue
 * It will be called by the data element controller and is written with
 * security considerations in mind
 * *************************************************************** */


class DataElementService {

	static transactional = false

	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
	def catalogueElementService

	
	/* **************************** ADD PERMISSIONS *****************************************
	 * calls add permission with the relevant permission when called with an integer
	 * permission input
	 ********************************************************************************* */

	void addPermission(DataElement dataElement, String username, int permission){

		addPermission dataElement, username,
			aclPermissionFactory.buildFromMask(permission)

	}

	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */

	@PreAuthorize("hasPermission(#dataElement, admin)")
	@Transactional
	void addPermission(DataElement dataElement, String username, Permission permission) {
	   aclUtilService.addPermission dataElement, username, permission
	}
	
	/* ************************* CREATE DATA ELEMENTS***********************************
	 * requires that the authenticated user to have ROLE_USER to create a data element
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	DataElement create(Map request) {
		
		//save the dataElement
        def relations = request.relations
        request.remove('relations')

		DataElement dataElementInstance = new DataElement(request)

        if(dataElementInstance.save(flush:true, failOnErorr:true)){
            dataElementInstance = catalogueElementService.linkRelations(dataElementInstance, relations)
            // Grant the current user principal administrative permission

            addPermission dataElementInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION

            //Grant admin user administrative permissions

            addPermission dataElementInstance, 'admin', BasePermission.ADMINISTRATION
        }

		//return the data element to the consumer (the controller)

		dataElementInstance

		}


	/* ************************* GET DATA ELEMENTS***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */

	@PreAuthorize('hasPermission(#id, "uk.co.mdc.catalogue.DataElement", read) or hasPermission(#id, "uk.co.mdc.catalogue.DataElement", admin)')
	DataElement get(long id) {
	   DataElement.get id
	   }


	/* ************************* SEARCH DATA ELEMENTS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 * */

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<DataElement> search(String sSearch) {
		//searchableService.reindexAll()
	   def searchResults = DataElement.search(sSearch)

	   //refresh the objects to get the relational field (otherwise lazy and returns null)
	   //.......bit of a hack will try and have a play with modifying the
	   //searchable plugin code to load the objects with the link classes
	   searchResults.results.each { dataElement->
                dataElement.refresh()
            }

	   searchResults.results
	   }


	/* ************************* LIST DATA ELEMENTS***********************************************
	 * requires that the authenticated user have ROLE_USER sand read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 ******************************************************************************************** */

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<DataElement> list(Map parameters) {
		DataElement.list parameters
	}

	//no restrictions on the count method

	int count() { DataElement.count() }

	/* ************************* UPDATE DATA ELEMENTS***********************************************
	 *  requires that the authenticated user have write or admin permission on the data element instance to edit it
	 ******************************************************************************************** */

	@Transactional
	@PreAuthorize("hasPermission(#dataElementInstance, write) or hasPermission(#dataElementInstance, admin)")
	DataElement update(DataElement dataElementInstance, Map request) {


        //check that we have the right version i.e. no one else has updated the data element whilst we have been
        //looking at it

        if (request.version != null) {
            if (dataElementInstance.version > request.version) {
                dataElementInstance.errors.rejectValue("version", "Another user has updated this DataElement while you were editing")
                return dataElementInstance
            }
        }

        //only update a data element if the element is in draft otherwise only the status can be updated
		if(dataElementInstance.status==CatalogueElement.Status.DRAFT){

            if(request.dataElement?.status=='PENDING' || request.dataElement?.status=='DRAFT'){
                //def subElements = request.dataElement?.subElements
                //request.dataElement.remove('subElements')
                def relations = request.dataElement?.relations
                request.dataElement.remove('relations')
                //def synonyms = request.dataElement?.synonyms
                //request.dataElement.remove('synonyms')
                //def valueDomains = request.dataElement?.valueDomains
                //request.dataElement.remove('valueDomains')

               // remove any subelements that have been specified for removal
              // unLinkSubElements(dataElementInstance, subElements)

               dataElementInstance.properties = request.dataElement

               //update revision number
               dataElementInstance.revisionNumber++


               if(dataElementInstance.save(flush:true) && relations){

                  dataElementInstance = catalogueElementService.linkRelations(dataElementInstance, relations)

               }

               dataElementInstance

            }else if(request.dataElement?.status=="REMOVED"){

                dataElementInstance.status = request.dataElement?.status
                dataElementInstance.save(flush:true)

            }else{

                dataElementInstance.errors.rejectValue("status","message.code","Element must be approved before update. Please change status to pending")
                dataElementInstance

            }

        }else if(dataElementInstance.status==CatalogueElement.Status.PENDING){


            //check whether the data element is being changed to finalized or back to draft
            //- this is the only operation that a user can make to a pending object

            if(request.dataElement?.status=='FINALIZED' || request.dataElement?.status=='DRAFT' ){

                dataElementInstance.versionNumber++
                dataElementInstance.revisionNumber = 0

                dataElementInstance.status = request.dataElement?.status
                dataElementInstance.save(flush:true)

                dataElementInstance

            }else if(request.dataElement?.status==CatalogueElement.Status.REMOVED){

                dataElementInstance.status = request.dataElement?.status
                dataElementInstance.save(flush:true)

            }else{

                dataElementInstance.errors.rejectValue("status","dataElement.status.error.updatePending","You cannot edit properties of a pending object, either finalize or set back to draft")
                dataElementInstance
            }


        }else{
            dataElementInstance.errors.rejectValue("status","message.code","You cannot edit an object once it has been finalized or removed")
            dataElementInstance
        }

	}



	/* ************************* DELETE DATA ELEMENTS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#dataElementInstance, delete) or hasPermission(#dataElementInstance, admin)")
	void delete(DataElement dataElementInstance) {

        //only object in draft state can be deleted
        if(dataElementInstance.status != CatalogueElement.Status.DRAFT){
            dataElementInstance.status = CatalogueElement.Status.REMOVED
        }else{
            dataElementInstance.prepareForDelete()
            dataElementInstance.delete(flush: true)
            // Delete the ACL information as well
            aclUtilService.deleteAcl dataElementInstance
        }


   }


	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#dataElementInstance, admin)")
	void deletePermission(DataElement dataElementInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(dataElementInstance)
		
		// Remove all permissions associated with this particular
		// recipient (string equality to KISS)
		
		acl.entries.eachWithIndex {
			entry, i -> if (entry.sid.equals(recipient) && entry.permission.equals(permission)) {
				acl.deleteAce i
				}
			}
		
		aclService.updateAcl acl
		
		}

}
