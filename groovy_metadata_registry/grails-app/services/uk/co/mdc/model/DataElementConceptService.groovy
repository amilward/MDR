package uk.co.mdc.model

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional


/* *********************************************************************
 * This service allows the user to access the data element concept concept model
 * It will be called by the data element concept concept controller and is written with
 * security considerations in mind
 * *************************************************************** */

class DataElementConceptService {

   static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
    
	
	
	/* **************************** ADD PERMISSIONS *****************************************
	 * calls add permission with the relevant permission when called with an integer
	 * permission input
	 ********************************************************************************* */
		
	void addPermission(DataElementConcept dataElementConcept, String username, int permission){
		
		addPermission dataElementConcept, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#dataElementConcept, admin)")
	@Transactional
	void addPermission(DataElementConcept dataElementConcept, String username, Permission permission) {
	   aclUtilService.addPermission dataElementConcept, username, permission
	}
	
	
	
	/* ************************* CREATE data element conceptS***********************************
	 * requires that the authenticated user to have ROLE_USER to create a data element concept
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	DataElementConcept create(Map parameters) {
		
		def dataElementConceptInstance = new DataElementConcept(parameters)
		
		//save the dataElement
		
		if(!dataElementConceptInstance.save(flush:true)){
			return dataElementConceptInstance
		}
		
		// Grant the current user principal administrative permission
		
		addPermission dataElementConceptInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission dataElementConceptInstance, 'admin', BasePermission.ADMINISTRATION
		
		//return the data element concept to the consumer (the controller)
		
		dataElementConceptInstance
		

		}
	
	
	/* ************************* GET VALUED DOMAINS ***********************************************
	 * requires that the authenticated user have read or admin permission on the specified data element concept
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.model.DataElementConcept", read) or hasPermission(#id, "uk.co.mdc.model.DataElementConcept", admin)')
	DataElementConcept get(long id) {
	   DataElementConcept.get id
	   }
	
	
	/* ************************* SEARCH VALUED DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned data element concept; instances that don't have granted permissions will be removed from the returned
	 * List
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<DataElementConcept> search(String sSearch) {
		def searchResult = DataElementConcept.search(sSearch)
	    searchResult.results
	}
	
	
	/* ************************* LIST VALUED DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned data element concept; instances that don't have granted permissions will be removed from the returned
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<DataElementConcept> list(Map parameters) {
		DataElementConcept.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { DataElementConcept.count() }
	
	
	/* ************************* UPDATE data element conceptS***********************************************
	 *  requires that the authenticated user have write or admin permission on the data element concept instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#dataElementConceptInstance, write) or hasPermission(#dataElementConceptInstance, admin)")
	void update(DataElementConcept dataElementConceptInstance, Map parameters) {
		  
	   //remove data elements (if needed)
		
		unLinkDataElements(dataElementConceptInstance, parameters.dataElements)
		
		//remove sub concepts (if needed)
		
		unLinkSubConcepts(dataElementConceptInstance, parameters.subConcepts)
		
	   dataElementConceptInstance.properties = parameters
	   
	   dataElementConceptInstance.save(flush: true)   
	   
	}
	
	
	/* ************************* DELETE data element conceptS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#dataElementConceptInstance, delete) or hasPermission(#dataElementConceptInstance, admin)")
	void delete(DataElementConcept dataElementConceptInstance) {
		
		dataElementConceptInstance.prepareForDelete()
		dataElementConceptInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl dataElementConceptInstance
   }
	
	
	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#dataElementConceptInstance, admin)")
	void deletePermission(DataElementConcept dataElementConceptInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(dataElementConceptInstance)
		
		// Remove all permissions associated with this particular
		// recipient (string equality to KISS)
		
		acl.entries.eachWithIndex {
			entry, i -> if (entry.sid.equals(recipient) && entry.permission.equals(permission)) {
				acl.deleteAce i
				}
			}
		
		aclService.updateAcl acl
		
		}
	
	
	/* ************************* data element concept LINK FUNCTIONS************************
	 * unlinks the data elements that have been removed from the data element concept during an update
	 ********************************************************************************* */
	
	
	def unLinkDataElements(dataElementConceptInstance, pDataElements){
		
			//if all data elements need to be removed or only a few elements need to be removed
			
			if(pDataElements==null && dataElementConceptInstance?.dataElements.size()>0){
				
				def dataElements = []
				dataElements += dataElementConceptInstance?.dataElements
				
				dataElements.each{ dataElement->
					dataElementConceptInstance.removeFromDataElements(dataElement)
				}
				
	
			}else if(pDataElements){
		
				if(pDataElements.size() < dataElementConceptInstance?.dataElements.size()){
			
				def dataElements = []
				
				dataElements += dataElementConceptInstance?.dataElements
				
				dataElements.each{ dataElement->
					
	
					if(pDataElements instanceof String){
						
							if(pDataElements!=dataElement){
						
								dataElementConceptInstance.removeFromDataElements(dataElement)
							
							}
						
						}else{
							
							if(!pDataElements.contains(dataElement)){
								
								dataElementConceptInstance.removeFromDataElements(dataElement)
								
							}
						
						}
					}
			}
			}
	}
	
	/* ************************* data element concept LINK FUNCTIONS************************
	 * unlinks the sub concepts that have been removed from the data element concept during an update
	 ********************************************************************************* */
	
	def unLinkSubConcepts(subConceptConceptInstance, pSubConcepts){
		
			//if all data elements need to be removed or only a few elements need to be removed
			
			if(pSubConcepts==null && subConceptConceptInstance?.subConcepts.size()>0){
				
				def subConcepts = []
				subConcepts += subConceptConceptInstance?.subConcepts
				
				subConcepts.each{ subConcept->
					subConceptConceptInstance.removeFromSubConcepts(subConcept)
				}
				
	
			}else if(pSubConcepts){
		
				if(pSubConcepts.size() < subConceptConceptInstance?.subConcepts.size()){
			
				def subConcepts = []
				
				subConcepts += subConceptConceptInstance?.subConcepts
				
				subConcepts.each{ subConcept->
					
	
					if(pSubConcepts instanceof String){
						
							if(pSubConcepts!=subConcept){
						
								subConceptConceptInstance.removeFromSubConcepts(subConcept)
							
							}
						
						}else{
							
							if(!pSubConcepts.contains(subConcept)){
								
								subConceptConceptInstance.removeFromSubConcepts(subConcept)
								
							}
						
						}
					}
			}
			}
	}
	
}
