package uk.co.mdc.model

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional


/*
 * This service allows the user to access the data Element model
 * It will be called by the data element controller and is written with
 * security considerations in mind
 * 
 * */


class DataElementService {

	static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
	
	
	/*
	 * calls add permission with the relevant permission when called with an integer 
	 * permission input
	 * */
		
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
	
	/*
	 * requires that the authenticated user to have ROLE_USER
	 * */
	
	@Transactional 
	@PreAuthorize("hasRole('ROLE_USER')") 
	DataElement create(Map parameters) { 
		
		
		//save the dataElement
		
		DataElement dataElementInstance = new DataElement(params)
		
		if (!dataElementInstance.save(flush: true)) {
			render(view: "create", model: [dataElementInstance: dataElementInstance, valueDomains: ValueDomain.list(), dataElements: DataElement.list()])
			return
		}
		
		//link selected value domains with  data element
		
		linkValueDomains(dataElementInstance)
		
		//link selected synonyms with  data element
		
		linkSynonyms(dataElementInstance)
		
		//redirect with message

		flash.message = message(code: 'default.created.message', args: [message(code: 'dataElement.label', default: 'DataElement'), dataElementInstance.id])
		redirect(action: "show", id: dataElementInstance.id, model: [valueDomains: ValueDomain.list()])
		
		DataElement dataElement = new DataElement(parameters) 
		dataElement.save()
		
		// Grant the current principal administrative permission 
		
		addPermission report, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		dataElement 
		
		}
	
	
		
}
