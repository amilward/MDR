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
	 * It will be called by the conceptualDomain controller and is written with
	 * security considerations in mind
	 * *************************************************************** */

class ConceptualDomainService {

    static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
	
	
	/* **************************** ADD PERMISSIONS *****************************************
	 * calls add permission with the relevant permission when called with an integer 
	 * permission input
	 ********************************************************************************* */
		
	void addPermission(ConceptualDomain conceptualDomain, String username, int permission){
		
		addPermission conceptualDomain, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#conceptualDomain, admin)")
	@Transactional
	void addPermission(ConceptualDomain conceptualDomain, String username, Permission permission) {
	   aclUtilService.addPermission conceptualDomain, username, permission
	}
	
	
	/* ************************* CREATE CONCEPTUAL DOMAINS ***********************************
	 * requires that the authenticated user to have ROLE_USER to create a conceptualDomain
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	ConceptualDomain create(Map parameters) {
		
		//save the conceptualDomain
		
		def valueDomains = parameters?.valueDomains
		parameters.remove('valueDomains')
		
		ConceptualDomain conceptualDomainInstance = new ConceptualDomain(parameters)
		
		if(conceptualDomainInstance.save(flush:true)){
			
			linkValueDomains(conceptualDomainInstance, valueDomains)
			
		}else{
		
			return conceptualDomainInstance
		}
		
		// Grant the current user principal administrative permission
		
		addPermission conceptualDomainInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission conceptualDomainInstance, 'admin', BasePermission.ADMINISTRATION
		
		//return the conceptualDomain to the consumer (the controller)
		
		conceptualDomainInstance
		
		}
	
	
	
	/* ************************* GET CONCEPTUAL DOMAINS***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.model.ConceptualDomain", read) or hasPermission(#id, "uk.co.mdc.model.ConceptualDomain", admin)')
	ConceptualDomain get(long id) {
	   ConceptualDomain.get id
	   }
	
	
	/* ************************* SEARCH CONCEPTUAL DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each 
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned 
	 * List 
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<ConceptualDomain> search(String sSearch) {
	   def searchResult = ConceptualDomain.search(sSearch)
	   searchResult.results
	   }
	
	
	/* ************************* LIST CONCEPTUAL DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER sand read or admin permission on each 
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned 
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<ConceptualDomain> list(Map parameters) {
		ConceptualDomain.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { ConceptualDomain.count() }
	
	/* ************************* UPDATE CONCEPTUAL DOMAINS***********************************************
	 *  requires that the authenticated user have write or admin permission on the conceptualDomain instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#conceptualDomainInstance, write) or hasPermission(#conceptualDomainInstance, admin)")
	ConceptualDomain update(ConceptualDomain conceptualDomainInstance, Map parameters) {

		def valueDomains = parameters?.valueDomains
		parameters.remove('valueDomains')
		
		// check if updated conceptual domain has removed valueDomains and remove
		unlinkValueDomains(conceptualDomainInstance, parameters?.valueDomains)
		
		conceptualDomainInstance.properties = parameters
	   
		
		if(conceptualDomainInstance.save(flush:true)){
			
			linkValueDomains(conceptualDomainInstance, valueDomains)
			
		}
		
		conceptualDomainInstance

	   }
	
	
	
	/* ************************* DELETE CONCEPTUAL DOMAINS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to 
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#conceptualDomainInstance, delete) or hasPermission(#conceptualDomainInstance, admin)")
	void delete(ConceptualDomain conceptualDomainInstance) {
		
		conceptualDomainInstance.prepareForDelete()
		conceptualDomainInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl conceptualDomainInstance
   }
	
	
	/* ************************* REMOVE DATA ELEMENT FROM CONCEPTUAL DOMAINS***********************************************
	 * requires that the authenticated user have write or admin permission on the report instance to
	 * remove a data element
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#conceptualDomainInstance, write) or hasPermission(#conceptualDomainInstance, admin)")
	
	void  removeValueDomain(ConceptualDomain conceptualDomainInstance, ValueDomain valueDomain){
	
		conceptualDomainInstance.removeFromValueDomains(valueDomain)
	
	}
	
	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#conceptualDomainInstance, admin)")
	void deletePermission(ConceptualDomain conceptualDomainInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(conceptualDomainInstance)
		
		// Remove all permissions associated with this particular
		// recipient (string equality to KISS)
		
		acl.entries.eachWithIndex {
			entry, i -> if (entry.sid.equals(recipient) && entry.permission.equals(permission)) {
				acl.deleteAce i
				}
			}
		
		aclService.updateAcl acl
		
		}
	
	
	
	def linkValueDomains(conceptualDomainInstance, valueDomains){
		
		//There is a bug in hibernate/grails hasMany on object during creation,
		//so we need to add the value domains with addToValueDomains
		//to ensure hibernate makes two way connection
		// between vd and cd (only needed when creating)
		
		if(valueDomains instanceof String){
			def vd = ValueDomain.get(valueDomains)
			conceptualDomainInstance.addToValueDomains(vd)
		}else{
			valueDomains.each{ valueDomainId ->
				def vd = ValueDomain.get(valueDomainId)
				if(vd){
					conceptualDomainInstance.addToValueDomains(vd)
				}
				
			}
		}
		
	}
	
	/* ************************* CONCEPTUAL DOMAIN LINKAGE FUNCTIONS************************
	 * unlinks the value domains that have been removed during an update of the conceptualDomain
	 ********************************************************************************* */
	
	def unlinkValueDomains(conceptualDomainInstance, pValueDomains){
		
		//if all data elements need to be removed or only a few elements need to be removed
		
			if(pValueDomains==null && conceptualDomainInstance?.valueDomains.size()>0){
				
				def valueDomains = []
				valueDomains += conceptualDomainInstance?.valueDomains
				
				valueDomains.each{ valueDomain->
					conceptualDomainInstance.removeFromValueDomains(valueDomain)
				}
				
	
			}else if(pValueDomains){
		
			
				def valueDomains = []
				
				valueDomains += conceptualDomainInstance?.valueDomains
				
				valueDomains.each{ valueDomain->
					
	
					if(pValueDomains instanceof String){
						
							if(pValueDomains!=valueDomain.id.toString()){
						
								conceptualDomainInstance.removeFromValueDomains(valueDomain)
							
							}
						
						}else{
							
							if(!pValueDomains.contains(valueDomain.id.toString())){
								
								conceptualDomainInstance.removeFromValueDomains(valueDomain)
								
							}
						
						}
					}
			
			
		}
	}
}
