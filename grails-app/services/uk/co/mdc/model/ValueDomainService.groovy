package uk.co.mdc.model

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional


	/* *********************************************************************
	 * This service allows the user to access the value domain model
	 * It will be called by the value domain controller and is written with
	 * security considerations in mind
	 * *************************************************************** */

class ValueDomainService {
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
		
	void addPermission(ValueDomain valueDomain, String username, int permission){
		
		addPermission valueDomain, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#valueDomain, admin)")
	@Transactional
	void addPermission(ValueDomain valueDomain, String username, Permission permission) {
	   aclUtilService.addPermission valueDomain, username, permission
	}
	
	
	
	/* ************************* CREATE DATA ELEMENTS***********************************
	 * requires that the authenticated user to have ROLE_USER to create a data element
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	ValueDomain create(Map parameters) {
		
		if(parameters?.dataType){
			DataType dataType = DataType.get(parameters?.dataType)
			parameters.dataType = dataType
		}
		def valueDomainInstance = new ValueDomain(parameters)
		
		//save the dataElement
		
		
		if(!valueDomainInstance.save(flush:true)){
			return valueDomainInstance
		}
		
		//link any value domains that were selected with data element
		catalogueElementService.linkRelations(valueDomainInstance, parameters?.dataElements, "DataValue")
		//linkDataElements(valueDomainInstance, parameters.dataElements)
		
		
		// Grant the current user principal administrative permission
		
		addPermission valueDomainInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission valueDomainInstance, 'admin', BasePermission.ADMINISTRATION
		
		//return the data element to the consumer (the controller)
		
		valueDomainInstance
		

		}
	
	
	/* ************************* GET VALUED DOMAINS ***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.model.ValueDomain", read) or hasPermission(#id, "uk.co.mdc.model.ValueDomain", admin)')
	ValueDomain get(long id) {
	   ValueDomain.get id
	   }
	
	
	/* ************************* SEARCH VALUED DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<ValueDomain> search(String sSearch) {
		def searchResult = ValueDomain.search(sSearch)
	    searchResult.results
	}
	
	
	/* ************************* LIST VALUED DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<ValueDomain> list(Map parameters) {
		ValueDomain.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { ValueDomain.count() }
	
	
	/* ************************* UPDATE VALUE DOMAINS***********************************************
	 *  requires that the authenticated user have write or admin permission on the value domain instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#valueDomainInstance, write) or hasPermission(#valueDomainInstance, admin)")
	void update(ValueDomain valueDomainInstance, Map parameters) {

	   
	   //remove any external synonyms that have specified for removal
	   //unLinkExternalReferences(valueDomainInstance, parameters?.externalReferences)
	   
	   valueDomainInstance.properties = parameters
	   
	   if(valueDomainInstance.save(flush: true)){
		   // add/remove value domains
		   catalogueElementService.linkRelations(valueDomainInstance, parameters?.dataElements, "DataValue")
		  // linkDataElements(valueDomainInstance, parameters?.dataElements)
	   }
	   
	}
	
	
	/* ************************* DELETE DATA ELEMENTS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#valueDomainInstance, delete) or hasPermission(#valueDomainInstance, admin)")
	void delete(ValueDomain valueDomainInstance) {
		
		valueDomainInstance.prepareForDelete()
		valueDomainInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl valueDomainInstance
   }
	
	
	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#valueDomainInstance, admin)")
	void deletePermission(ValueDomain valueDomainInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(valueDomainInstance)
		
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

	