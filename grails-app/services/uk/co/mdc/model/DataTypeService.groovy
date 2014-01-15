package uk.co.mdc.model

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional

/* *********************************************************************
 * This service allows the user to access the conceptual domain model
 * It will be called by the dataType controller and is written with
 * security considerations in mind
 * *************************************************************** */

class DataTypeService {

    static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
	
	
	/* **************************** ADD PERMISSIONS *****************************************
	 * calls add permission with the relevant permission when called with an integer 
	 * permission input
	 ********************************************************************************* */
		
	void addPermission(DataType dataType, String username, int permission){
		
		addPermission dataType, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#dataType, admin)")
	@Transactional
	void addPermission(DataType dataType, String username, Permission permission) {
	   aclUtilService.addPermission dataType, username, permission
	}
	
	
	/* ************************* CREATE DATA TYPES ***********************************
	 * requires that the authenticated user to have ROLE_USER to create a dataType
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	DataType create(Map parameters) {
		
		//save the dataType
		
		DataType dataTypeInstance = new DataType(parameters)
		if(!dataTypeInstance.save(flush:true)){
			return dataTypeInstance
		}
		
		// Grant the current user principal administrative permission
		
		addPermission dataTypeInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission dataTypeInstance, 'admin', BasePermission.ADMINISTRATION
		
		//return the dataType to the consumer (the controller)
		
		dataTypeInstance
		
		}
	
	
	
	/* ************************* GET DATA TYPES***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.model.DataType", read) or hasPermission(#id, "uk.co.mdc.model.DataType", admin)')
	DataType get(long id) {
	   DataType.get id
	   }
	
	
	/* ************************* SEARCH DATA TYPES***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each 
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned 
	 * List 
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<DataType> search(String sSearch) {
	   def searchResult = DataType.search(sSearch)
	   searchResult.results
	   }
	
	
	/* ************************* LIST DATA TYPES***********************************************
	 * requires that the authenticated user have ROLE_USER sand read or admin permission on each 
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned 
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<DataType> list(Map parameters) {
		DataType.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { DataType.count() }
	
	/* ************************* UPDATE DATA TYPES***********************************************
	 *  requires that the authenticated user have write or admin permission on the dataType instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#dataTypeInstance, write) or hasPermission(#dataTypeInstance, admin)")
	void update(DataType dataTypeInstance, Map parameters) {

	   dataTypeInstance.properties = parameters
	   
	   dataTypeInstance.save(flush: true)

	   }
	
	
	
	/* ************************* DELETE DATA TYPES***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to 
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#dataTypeInstance, delete) or hasPermission(#dataTypeInstance, admin)")
	void delete(DataType dataTypeInstance) {
		
		dataTypeInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl dataTypeInstance
   }
	
	
	/* ************************* REMOVE DATA ELEMENT FROM DATA TYPES***********************************************
	 * requires that the authenticated user have write or admin permission on the report instance to
	 * remove a data element
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#dataTypeInstance, write) or hasPermission(#dataTypeInstance, admin)")
	
	void  removeEnumeratedValue(DataType dataTypeInstance, String enumeratedValue){

		if(dataTypeInstance){
			dataTypeInstance.enumerations.remove(enumeratedValue)
		}
		
	}
	
	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#dataTypeInstance, admin)")
	void deletePermission(DataType dataTypeInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(dataTypeInstance)
		
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
