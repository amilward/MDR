package uk.co.mdc.model

import java.util.List;
import java.util.Map;

import org.springframework.security.acls.model.Permission;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission



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
	
	/* ************************* CREATE DATA ELEMENTS***********************************
	 * requires that the authenticated user to have ROLE_USER to create a data element
	 ********************************************************************************* */
	
	@Transactional 
	@PreAuthorize("hasRole('ROLE_USER')") 
	ExternalReference create(Map parameters) { 
		
		//save the externalReference
		
		ExternalReference externalReferenceInstance = new ExternalReference(parameters) 
		externalReferenceInstance.save(flush:true)
		
		//link any value domains that were selected with data element
		
		linkValueDomains(externalReferenceInstance, parameters?.valueDomains)
		
		//link any synonyms that were selected with data element
		
		linkSynonyms(externalReferenceInstance, parameters?.synonyms)
		
		// Grant the current user principal administrative permission 
		
		addPermission externalReferenceInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission externalReferenceInstance, 'admin', BasePermission.ADMINISTRATION
		
		//return the data element to the consumer (the controller)
		
		externalReferenceInstance 
		
		}
	
	
	/* ************************* GET DATA ELEMENTS***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.model.ExternalReference", read) or hasPermission(#id, "uk.co.mdc.model.ExternalReference", admin)')
	ExternalReference get(long id) {
	   ExternalReference.get id
	   }
	
	
	/* ************************* SEARCH DATA ELEMENTS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each 
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned 
	 * List 
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<ExternalReference> search(String sSearch, Map displayLength) {
	   ExternalReference.search(sSearch, displayLength)
	   }
	
	
	/* ************************* LIST DATA ELEMENTS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each 
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
	
	/* ************************* UPDATE DATA ELEMENTS***********************************************
	 *  requires that the authenticated user have write or admin permission on the data element instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#externalReferenceInstance, write) or hasPermission(#externalReferenceInstance, admin)")
	void update(ExternalReference externalReferenceInstance, Map parameters) {

	   // remove any subelements that have specified for removal
	   unLinkSubElements(externalReferenceInstance, parameters?.subElements)
	   
	   //remove any external references that have specified for removal
	   unLinkExternalReferences(externalReferenceInstance, parameters?.externalReferences)
	   
	   //add/remove synonyms that have specified for addition or removal
	   linkSynonyms(externalReferenceInstance, parameters?.synonyms)

	   externalReferenceInstance.properties = parameters
	   
	   externalReferenceInstance.save(flush: true)
	   
	   // add/remove value domains
	   linkValueDomains(externalReferenceInstance, parameters?.valueDomains)
	   
	}
	
	
	
	/* ************************* DELETE DATA ELEMENTS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to 
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#externalReferenceInstance, delete) or hasPermission(#externalReferenceInstance, admin)")
	void delete(ExternalReference externalReferenceInstance) {
		
		externalReferenceInstance.prepareForDelete()
		externalReferenceInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl externalReferenceInstance
   }
	
	
	/* ************************* DELETE DATA ELEMENTS***********************************************
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

