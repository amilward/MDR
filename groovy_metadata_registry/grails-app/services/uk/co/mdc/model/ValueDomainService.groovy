package uk.co.mdc.model

import java.util.List;
import java.util.Map;

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

class ValueDomainService {
	static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
    
	
	
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
		
		
		valueDomainInstance.save(flush:true)
		
		//link any value domains that were selected with data element
		
		linkDataElements(valueDomainInstance, parameters.dataElements)
		
		
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
	List<ValueDomain> search(String sSearch, Integer iDisplayLength) {
	   ValueDomain.search(sSearch, [max:iDisplayLength])
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
	
	/* ************************* UPDATE DATA ELEMENTS***********************************************
	 *  requires that the authenticated user have write or admin permission on the data element instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#valueDomainInstance, write) or hasPermission(#valueDomainInstance, admin)")
	void update(ValueDomain valueDomainInstance, Map parameters) {
		
		if(parameters?.dataType){
			DataType dataType = DataType.get(parameters?.dataType)
			parameters.dataType = dataType
		}
	   
	   //remove any external synonyms that have specified for removal
	   unLinkExternalSynonyms(valueDomainInstance, parameters?.externalSynonyms)
	   

	   valueDomainInstance.properties = parameters
	   
	   valueDomainInstance.save(flush: true)
	   
	   // add/remove value domains
	   linkDataElements(valueDomainInstance, parameters?.dataElements)
	   
	   ///////////////////
	   
	   
	   
	   valueDomainInstance.properties = params

	   if (!valueDomainInstance.save(flush: true)) {
		   render(view: "edit", model: [dataElements: DataElement.list(), dataTypes: DataType.list(), valueDomainInstance: valueDomainInstance])
		   return
	   }

   
	   linkDataElements(valueDomainInstance)

	   flash.message = message(code: 'default.updated.message', args: [message(code: 'valueDomain.label', default: 'ValueDomain'), valueDomainInstance.id])
	   redirect(action: "show", id: valueDomainInstance.id)
	   
	}
	
	/* ************************* VALUE DOMAIN LINK FUNCTIONS************************
	 * links the value domain with the data element specified via a link table
	 ********************************************************************************* */
	
	
	def linkDataElements(valueDomainInstance, dataElements){
		
		def associatedDataElements = valueDomainInstance.dataElementValueDomains()
		
		if(dataElements!=null){
			
			if (dataElements instanceof String) {
				
				DataElement dataElement =  DataElement.get(dataElements)
				
				associatedDataElements.each{ vd ->
					if(dataElements!=vd.id.toString()){
							DataElementValueDomain.unlink(vd, valueDomainInstance)
					}
				}

				if(dataElement){
					
					DataElementValueDomain.link(dataElement, valueDomainInstance)
					
				}
				
			} else if (dataElements instanceof String[]) {
			
			
				//remove all the value domains that aren't this one
				associatedDataElements.each{ vd ->
					if(!dataElements.contains(vd.id.toString())){
							DataElementValueDomain.unlink(vd, valueDomainInstance)
					}
				}
			
				  for (dataElementID in dataElements){
					  DataElement dataElement =  DataElement.get(dataElementID)
					  if(dataElement){
						  DataElementValueDomain.link(dataElement, valueDomainInstance)
					  }
				  }
			}

		}else{
			
			//remove all the data elements that aren't this one
			associatedDataElements.each{ vd ->
						DataElementValueDomain.unlink(vd, valueDomainInstance)
			}
			
		}
	}
	
	/* ************************* VALUE DOMAIN LINK FUNCTIONS************************
	 * unlinks the external sunonyms that have been removed during an update of the value domain
	 ********************************************************************************* */
	
	def unLinkExternalSynonyms(valueDomainInstance, pExternalSynonyms){
		
		//if there are no external synonyms i.e. ALL external synonyms need to be removed 
		//from the value domain after the edit (presuming there were external synonyms in the 
		//data element before the edit)
		
			
			if(pExternalSynonyms==null && valueDomainInstance?.externalSynonyms.size()>0){
				
				//pass all the objects external synonyms into a new array 
				//otherwise we get all sorts or problems (link to object rather than a new object)
				def externalSynonyms = []
				externalSynonyms += valueDomainInstance?.externalSynonyms
				
				//remove ALL the external synonyms
				externalSynonyms.each{ externalSynonym->
					valueDomainInstance.removeFromExternalSynonyms(externalSynonym)
				}
				
			//if there are some external synonyms (in the update)
			}else if(pExternalSynonyms){
		
			//but there are also synonyms to remove
				if(pExternalSynonyms.size() < valueDomainInstance?.externalSynonyms.size()){
			
				//pass all the objects external synonyms into a new array
				//(otherwise we get all sorts or problems)
				def externalSynonyms = []
				externalSynonyms += valueDomainInstance?.externalSynonyms
				
				//remove the external synonyms that need removing
				externalSynonyms.each{ externalSynonym->
					
	
					if(pExternalSynonyms instanceof String){
						
							if(pExternalSynonyms!=externalSynonym){
						
								valueDomainInstance.removeFromExternalSynonyms(externalSynonym)
							
							}
						
						}else{
							
							if(!pExternalSynonyms.contains(externalSynonym)){
								
								valueDomainInstance.removeFromExternalSynonyms(externalSynonym)
								
							}
						
						}
					}
			}
			}
	}
	
}
