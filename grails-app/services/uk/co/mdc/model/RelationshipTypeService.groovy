package uk.co.mdc.model

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional

/* *********************************************************************
	 * This service allows the user to access the data element model
	 * It will be called by the data element controller and is written with
	 * security considerations in mind
	 * *************************************************************** */


class RelationshipTypeService {

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

	void addPermission(RelationshipType relationshipType, String username, int permission){

		addPermission relationshipType, username,
			aclPermissionFactory.buildFromMask(permission)

	}

	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */

	@PreAuthorize("hasPermission(#relationshipType, admin)")
	@Transactional
	void addPermission(RelationshipType relationshipType, String username, Permission permission) {
	   aclUtilService.addPermission relationshipType, username, permission
	}
	
	/* ************************* CREATE DATA ELEMENTS***********************************
	 * requires that the authenticated user to have ROLE_USER to create a data element
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	RelationshipType create(Map parameters) { 
		
		//save the relationshipType

		RelationshipType relationshipTypeInstance = new RelationshipType(parameters)

		if(!relationshipTypeInstance.save(flush:true)){
			return relationshipTypeInstance
		}

		// Grant the current user principal administrative permission

		addPermission relationshipTypeInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION

		//Grant admin user administrative permissions

		addPermission relationshipTypeInstance, 'admin', BasePermission.ADMINISTRATION

		//return the data element to the consumer (the controller)

		relationshipTypeInstance
		
		}
	
	
	/* ************************* GET DATA ELEMENTS***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.model.RelationshipType", read) or hasPermission(#id, "uk.co.mdc.model.RelationshipType", admin)')
	RelationshipType get(long id) {
	   RelationshipType.get id
	   }
	
	
	/* ************************* SEARCH DATA ELEMENTS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each 
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned 
	 * List 
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<RelationshipType> search(String sSearch) {
		//searchableService.reindexAll()
	   def searchResults = RelationshipType.search(sSearch)

	   //refresh the objects to get the relational field (otherwise lazy and returns null)
	   //.......bit of a hack will try and have a play with modifying the
	   //searchable plugin code to load the objects with the link classes
	   searchResults.results.each { relationshipType->
                relationshipType.refresh()
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
	List<RelationshipType> list(Map parameters) {
		RelationshipType.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { RelationshipType.count() }
	
	/* ************************* UPDATE DATA ELEMENTS***********************************************
	 *  requires that the authenticated user have write or admin permission on the data element instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#relationshipTypeInstance, write) or hasPermission(#relationshipTypeInstance, admin)")
	RelationshipType update(RelationshipType relationshipTypeInstance, Map parameters) {

	   
	   relationshipTypeInstance.properties = parameters

	   relationshipTypeInstance
	   
	}
	
	
	
	/* ************************* DELETE DATA ELEMENTS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to 
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#relationshipTypeInstance, delete) or hasPermission(#relationshipTypeInstance, admin)")
	void delete(RelationshipType relationshipTypeInstance) {
		
		relationshipTypeInstance.prepareForDelete()
		relationshipTypeInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl relationshipTypeInstance
   }
	
	
	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#relationshipTypeInstance, admin)")
	void deletePermission(RelationshipType relationshipTypeInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(relationshipTypeInstance)
		
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
