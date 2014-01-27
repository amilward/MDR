package uk.co.mdc.catalogue

import uk.co.mdc.catalogue.Document

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional

/* *********************************************************************
 * This service allows the user to access the conceptual domain catalogue
 * It will be called by the dataType controller and is written with
 * security considerations in mind
 * *************************************************************** */

class DocumentService {

    static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
	
	
	/* **************************** ADD PERMISSIONS *****************************************
	 * calls add permission with the relevant permission when called with an integer 
	 * permission input
	 ********************************************************************************* */
		
	void addPermission(Document document, String username, int permission){
		
		addPermission document, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#document, admin)")
	@Transactional
	void addPermission(Document document, String username, Permission permission) {
	   aclUtilService.addPermission document, username, permission
	}
	
	
	/* ************************* CREATE DOCUMENTS ***********************************
	 * requires that the authenticated user to have ROLE_USER to create a document
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	Document create(Map parameters) {
		
		//save the document
		
		Document documentInstance = new Document(parameters)
		if(!documentInstance.save(flush:true)){
			return documentInstance
		}
		
		// Grant the current user principal administrative permission
		
		addPermission documentInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission documentInstance, 'admin', BasePermission.ADMINISTRATION
		
		//return the document to the consumer (the controller)
		
		documentInstance
		
		}
	
	
	
	/* ************************* GET DOCUMENTS***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.catalogue.Document", read) or hasPermission(#id, "uk.co.mdc.catalogue.Document", admin)')
	Document get(long id) {
	   Document.get id
	   }
	
	
	/* ************************* SEARCH DOCUMENTS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each 
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned 
	 * List 
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<Document> search(String sSearch) {
	   def searchResult = Document.search(sSearch)
	   searchResult.results
	   }
	
	
	/* ************************* LIST DOCUMENTS***********************************************
	 * requires that the authenticated user have ROLE_USER sand read or admin permission on each 
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned 
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<Document> list(Map parameters) {
		Document.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { Document.count() }
	
	/* ************************* UPDATE DOCUMENTS***********************************************
	 *  requires that the authenticated user have write or admin permission on the document instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#documentInstance, write) or hasPermission(#documentInstance, admin)")
	void update(Document documentInstance, Map parameters) {
		
		if(parameters.content.isEmpty()){
			documentInstance.description = parameters?.description
			documentInstance.name = parameters?.name
			documentInstance.fileName = parameters?.fileName
			documentInstance.contentType = parameters?.contentType
			documentInstance.version = parameters?.version
		}else{
		
			documentInstance.properties = parameters
		
		}
	   
	   documentInstance.save(flush: true)

	   }
	
	
	
	/* ************************* DELETE DOCUMENTS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to 
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#documentInstance, delete) or hasPermission(#documentInstance, admin)")
	void delete(Document documentInstance) {
		
		documentInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl documentInstance
   }
	
	
	/* ************************* REMOVE DATA ELEMENT FROM DOCUMENTS***********************************************
	 * requires that the authenticated user have write or admin permission on the report instance to
	 * remove a data element
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#documentInstance, write) or hasPermission(#documentInstance, admin)")
	
	void  removeEnumeratedValue(Document documentInstance, String enumeratedValue){

		if(documentInstance){
			documentInstance.enumerations.remove(enumeratedValue)
		}
		
	}
	
	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#documentInstance, admin)")
	void deletePermission(Document documentInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(documentInstance)
		
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
