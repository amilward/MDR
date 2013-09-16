package uk.co.mdc.model

import java.util.List;
import java.util.Map;

import org.springframework.security.acls.model.Permission;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission

/* *********************************************************************
 * This service allows the user to access the external reference model
 * It will be called by the externalReference controller and is written with
 * security considerations in mind
 * *************************************************************** */

class ExternalReferenceService {

  
	static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
	
	
	/* **************************** ADD PERMISSIONS *****************************************
	 * calls add permission with the relevant permission when called with an integer
	 * permission input
	 ********************************************************************************* */
		
	void addPermission(ExternalReference externalReference, String username, int permission){
		
		addPermission externalReference, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#externalReference, admin)")
	@Transactional
	void addPermission(ExternalReference externalReference, String username, Permission permission) {
	   aclUtilService.addPermission externalReference, username, permission
	}
	
	
	/* ************************* CREATE EXTERNAL REFERENCES ***********************************
	 * requires that the authenticated user to have ROLE_USER to create a externalReference
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	ExternalReference create(Map parameters) {
		
		//save the externalReference
		
		ExternalReference externalReferenceInstance = new ExternalReference(parameters)
		if(!externalReferenceInstance.save(flush:true)){
			return externalReferenceInstance
		}
		
		// Grant the current user principal administrative permission
		
		addPermission externalReferenceInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission externalReferenceInstance, 'admin', BasePermission.ADMINISTRATION
		
		//return the externalReference to the consumer (the controller)
		
		externalReferenceInstance
		
		}
	
	
	
	/* ************************* GET EXTERNAL REFERENCES***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.model.ExternalReference", read) or hasPermission(#id, "uk.co.mdc.model.ExternalReference", admin)')
	ExternalReference get(long id) {
	   ExternalReference.get id
	   }
	
	
	/* ************************* SEARCH EXTERNAL REFERENCES***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<ExternalReference> search(String sSearch) {
	   def searchResult = ExternalReference.search(sSearch)
	   searchResult.results
	   }
	
	
	/* ************************* LIST EXTERNAL REFERENCES***********************************************
	 * requires that the authenticated user have ROLE_USER sand read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<ExternalReference> list(Map parameters) {
		ExternalReference.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { ExternalReference.count() }
	
	/* ************************* UPDATE EXTERNAL REFERENCES***********************************************
	 *  requires that the authenticated user have write or admin permission on the externalReference instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#externalReferenceInstance, write) or hasPermission(#externalReferenceInstance, admin)")
	void update(ExternalReference externalReferenceInstance, Map parameters) {

	   externalReferenceInstance.properties = parameters
	   
	   externalReferenceInstance.save(flush: true)

	   }
	
	
	
	/* ************************* DELETE EXTERNAL REFERENCES***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#externalReferenceInstance, delete) or hasPermission(#externalReferenceInstance, admin)")
	void delete(ExternalReference externalReferenceInstance) {
		
		externalReferenceInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl externalReferenceInstance
   }
	
	
	/* ************************* REMOVE DATA ELEMENT FROM EXTERNAL REFERENCES***********************************************
	 * requires that the authenticated user have write or admin permission on the report instance to
	 * remove a data element
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#externalReferenceInstance, write) or hasPermission(#externalReferenceInstance, admin)")
	
	void  removeAttribute(ExternalReference externalReferenceInstance, String attribute){

		if(externalReferenceInstance){
			externalReferenceInstance.attributes.remove(attribute)
		}
		
	}
	
	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#externalReferenceInstance, admin)")
	void deletePermission(ExternalReference externalReferenceInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(externalReferenceInstance)
		
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

