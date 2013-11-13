package uk.co.mdc.model

import grails.converters.JSON
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


class DataElementService {

	static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
	
	
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
	DataElement create(Map parameters) { 
		
		//save the dataElement
		
		DataElement dataElementInstance = new DataElement(parameters) 
		
		if(!dataElementInstance.save(flush:true)){
			return dataElementInstance
		}
		
		//link any value domains that were selected with data element
		
		linkValueDomains(dataElementInstance, parameters?.valueDomains)
		
		//link any synonyms that were selected with data element
		
		linkSynonyms(dataElementInstance, parameters?.synonyms)
		
		// Grant the current user principal administrative permission 
		
		addPermission dataElementInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission dataElementInstance, 'admin', BasePermission.ADMINISTRATION
		
		//return the data element to the consumer (the controller)
		
		dataElementInstance 
		
		}
	
	
	/* ************************* GET DATA ELEMENTS***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.model.DataElement", read) or hasPermission(#id, "uk.co.mdc.model.DataElement", admin)')
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
	void update(DataElement dataElementInstance, Map parameters) {

	   // remove any subelements that have specified for removal
	   unLinkSubElements(dataElementInstance, parameters?.subElements)
	   
	   //remove any external references that have specified for removal
	   unLinkExternalReferences(dataElementInstance, parameters?.externalReferences)
	   
	   dataElementInstance.properties = parameters
	   
	   if(dataElementInstance.save(flush: true)){
		   //add/remove synonyms that have specified for addition or removal
		   linkSynonyms(dataElementInstance, parameters?.synonyms)
	   }
	   
	   if(dataElementInstance.save(flush: true)){
		   // add/remove value domains
		   linkValueDomains(dataElementInstance, parameters?.valueDomains)
	   }
	   
	}
	
	
	
	/* ************************* DELETE DATA ELEMENTS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to 
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#dataElementInstance, delete) or hasPermission(#dataElementInstance, admin)")
	void delete(DataElement dataElementInstance) {
		
		dataElementInstance.prepareForDelete()
		dataElementInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl dataElementInstance
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
	
	
	/* ************************* DATA ELEMENT LINKAGE FUNCTIONS************************
	 * links the data element with the synonyms specified via a link table
	 ********************************************************************************* */
	
	def linkSynonyms(dataElementInstance, synonyms){
		
		//get the synonyms already associated with the data element before the update
		def associatedSynonyms = dataElementInstance.synonyms()
		
		//if there are no new synonyms i.e. all have been removed in the edit data element form, then 
		//remove all synonyms from the data element
		
		if(synonyms!=null){
			
			//if there is only one synonym (rather than a list of them)
			
			if (synonyms instanceof String) {
				
				DataElement synonym =  DataElement.get(synonyms)
				
				//remove any synonyms that aren't this one
				associatedSynonyms.each{ vd ->
					if(synonyms!=vd.id.toString()){
							Synonym.unlink(dataElementInstance, vd)
					}
				}
				
				//add this one to the data element
				
				if(synonym){					
					Synonym.link(dataElementInstance, synonym)
				}
				
			}
			
			//if there is a list of synonyms
			
			if (synonyms instanceof String[]) {
				
				//remove all the synonyms that aren't in the list
				associatedSynonyms.each{ vd ->
					if(!synonyms.contains(vd.id.toString())){
							Synonym.unlink(dataElementInstance, vd)
					}
				}
				
				//add all the synonyms in the list
				
				  for (synonymID in synonyms){
					  DataElement synonym =  DataElement.get(synonymID)
					  if(synonym){
						  	Synonym.link(dataElementInstance, synonym)
						}
				  }
  
				  
			}

		}else{
		
		//remove all the synonyms that aren't this one
		associatedSynonyms.each{ vd ->
			
				Synonym.unlink(dataElementInstance, vd)

		}
		
		}
		
	}
	
	/* ************************* DATA ELEMENT LINKAGE FUNCTIONS************************
	 * links the data element with the value domains specified via a link table
	 ********************************************************************************* */
	
	def linkValueDomains(dataElementInstance, valueDomains){
		
		//get the synonyms value domains already associated with the data element before the update
		
		def associatedValueDomains = dataElementInstance.dataElementValueDomains()
		
		//if there are no new value domains i.e. all have been removed in the edit data element form, then
		//remove all value domains from the data element
		
		if(valueDomains!=null){
			
			//if there is only one value domain (rather than a list of them)
			
			if (valueDomains instanceof String) {
				
				ValueDomain valueDomain =  ValueDomain.get(valueDomains)
				
				//remove all the value domains that aren't this one
				associatedValueDomains.each{ vd ->
					if(valueDomains!=vd.id.toString()){
							DataElementValueDomain.unlink(dataElementInstance, vd)
					}
				}
				
				//add this one to the data element
				
				if(valueDomain){
					
					DataElementValueDomain.link(dataElementInstance, valueDomain)
				}
				
			}
			
			//if there is a list of synonyms
			
			if (valueDomains instanceof String[]) {
				
				//remove all the value domains that aren't this one
				associatedValueDomains.each{ vd ->
					if(!valueDomains.contains(vd.id.toString())){
							DataElementValueDomain.unlink(dataElementInstance, vd)
					}
				}
				
				//add all the value domains in the list
				
				  for (valueDomainID in valueDomains){
					  ValueDomain valueDomain =  ValueDomain.get(valueDomainID)
					  if(valueDomain){
							DataElementValueDomain.link(dataElementInstance, valueDomain)
						}
				  }
  
				  
			}

		}else{
		
		//remove all the value domains that aren't this one
		associatedValueDomains.each{ vd ->
					DataElementValueDomain.unlink(dataElementInstance, vd)
		}
		
		}
	}
	
	/* ************************* DATA ELEMENT LINKAGE FUNCTIONS************************
	 * unlinks the sub elements that have been removed during an update of the data element
	 ********************************************************************************* */
	
	
	def unLinkSubElements(dataElementInstance, pSubElements){
		
		//if there are no sub elements i.e. ALL sub elements need to be removed 
		//from the data element after the edit (presuming there were sub elements in the data element before the edit)
		
			if(pSubElements==null && dataElementInstance?.subElements.size()>0){
				
				//pass all the objects sub elements into a new array (otherwise we get all sorts or problems)
				def subElements = []
				subElements += dataElementInstance?.subElements
				
				//remove ALL of the subelements
				
				subElements.each{ subElement->
					dataElementInstance.removeFromSubElements(subElement)
				}
				
			//else if there are some sub elements
				
			}else if(pSubElements){
			
			//but there are also sub elements to remove
			//NEED TO DOUBLE CHECK THIS
			//!!!!!!! NEED what if they are the same size 
		
		//	if(pSubElements.size() < dataElementInstance?.subElements.size()){
			
				//pass all the objects sub elements into a new array (otherwise we get all sorts or problems)
				def subElements = []				
				subElements += dataElementInstance?.subElements
				
				//remove the sub elements that need removing
				subElements.each{ subElement->
					
					//check if only one sub element has been added
					if(pSubElements instanceof String){
							if(pSubElements!=subElement){
								dataElementInstance.removeFromSubElements(subElement)
							}						
						}else{							
							if(!pSubElements.contains(subElement)){								
								dataElementInstance.removeFromSubElements(subElement)								
							}						
						}
					}
			//}
			
		}
	}
	
	
	/* ************************* DATA ELEMENT LINKAGE FUNCTIONS************************
	 * unlinks the external references that have been removed from the data element during an update
	 ********************************************************************************* */
	
	def unLinkExternalReferences(dataElementInstance, extReferences){
		
		//if there are no external references i.e. ALL external references need to be removed 
		//from the data element after the edit (presuming there were external references in the data element before the edit)
		
			if(extReferences==null && dataElementInstance?.externalReferences.size()>0){
				
				//pass all the objects external synonyms into a new array (otherwise we get all sorts or problems)
				def externalReferences = []
				externalReferences += dataElementInstance?.externalReferences
				
				//remove ALL the external references
				externalReferences.each{ externalReference->
					dataElementInstance.removeFromExternalReferences(externalReference)
				}
				
	
			//if there are some external references
			}else if(extReferences){
			
				//but there are also sub elements to remove
				//if(extReferences.size() < dataElementInstance?.externalReferences.size()){
			
					//pass all the objects external references into a new array (otherwise we get all sorts or problems)	
					def externalReferences = []
					externalReferences += dataElementInstance?.externalReferences
				
					
					//remove the external references that need removing
					
					externalReferences.each{ externalReference->
					
					if(extReferences instanceof String){						
							if(extReferences!=externalReference){						
								dataElementInstance.removeFromExternalReferences(externalReference)							
							}						
						}else{							
							if(!extReferences.contains(externalReference)){								
								dataElementInstance.removeFromExternalReferences(externalReference)								
							}						
						}
					}
				//}
			}
	}
	
	
}
