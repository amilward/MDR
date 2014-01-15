package uk.co.mdc.model

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional
import uk.co.mdc.ModelBasket


	/* *********************************************************************
	 * This service allows the user to access the model model
	 * It will be called by the model controller and is written with
	 * security considerations in mind
	 * *************************************************************** */


class ModelService {

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
		
	void addPermission(Model model, String username, int permission){
		
		addPermission model, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#model, admin)")
	@Transactional
	void addPermission(Model model, String username, Permission permission) {
	   aclUtilService.addPermission model, username, permission
	}
	
	/* ************************* CREATE ModelS FROM Model BASKET***********************************
	 * requires that the authenticated user to have ROLE_USER to create a model
	 ********************************************************************************* */
	
	@Transactional 
	@PreAuthorize("hasRole('ROLE_USER')") 
	Model createFromBasket(Map parameters) { 
		
		//save the model
		
		Model modelInstance = new Model(name: parameters?.name, description: parameters?.description)
		
		if(modelInstance.save(flush:true)){
			
			Set dataElementIds = []

			if(parameters.dataElementIds instanceof String){
				dataElementIds.add(parameters.dataElementIds)
			}else{
				dataElementIds = parameters.dataElementIds
			}
			
			dataElementIds.each{ de ->

				def dataElementInstance = DataElement.get(de)

				def dataElementSchemaInfo = parameters["dataElement_" + de]
				
				def relationshipType = 'MandatoryModelElement'
				
				if(dataElementSchemaInfo=='required'){
				
					relationshipType = 'RequiredModelElement'
				
				}else if(dataElementSchemaInfo=='optional'){
				
					relationshipType = 'OptionalModelElement'
				
				}else if(dataElementSchemaInfo=='reference'){
				
					relationshipType = 'ReferenceModelElement'
				
				}
				
				//DataElementModel.link(dataElement, modelInstance, schemaSpecification)
				
				catalogueElementService.linkRelations(modelInstance, parameters?.synonyms, relationshipType)
				
			}
			
			def modelBasket = ModelBasket.get(parameters?.model_basket_id)
			modelBasket.dataElements.clear();
			modelBasket.save(flush: true)
			
		
			// Grant the current user principal administrative permission
			
			addPermission modelInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
			
			//Grant admin user administrative permissions
			
			addPermission modelInstance, 'admin', BasePermission.ADMINISTRATION
			
			//return the model to the consumer (the controller)
			
		}

		modelInstance 
		
		}
	
	
	/* ************************* CREATE ModelS ***********************************
	 * requires that the authenticated user to have ROLE_USER to create a model
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	Model create(Map parameters) {
		
		//save the model
		
		Model modelInstance = new Model(name: parameters?.name, description: parameters?.description)
		if(!modelInstance.save(flush:true)){
			return modelInstance
		}
		
		if(parameters?.requiredDataElements){
			catalogueElementService.linkRelations(modelInstance, parameters?.requiredDataElements, 'RequiredModelElement')
		}
		if(parameters?.optionalDataElements){
			catalogueElementService.linkRelations(modelInstance, parameters?.optionalDataElements, 'OptionalModelElement')
		}
		if(parameters?.referenceDataElements){
			catalogueElementService.linkRelations(modelInstance, parameters?.referenceDataElements, 'ReferenceModelElement')
		}
		if(parameters?.mandatoryDataElements){
			catalogueElementService.linkRelations(modelInstance, parameters?.mandatoryDataElements, 'MandatoryModelElement')
		}
		// Grant the current user principal administrative permission
		
		addPermission modelInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission modelInstance, 'admin', BasePermission.ADMINISTRATION
		
		//return the model to the consumer (the controller)
		
		modelInstance
		
		}
	
	
	
	/* ************************* GET ModelS***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.model.Model", read) or hasPermission(#id, "uk.co.mdc.model.Model", admin)')
	Model get(long id) {
	   Model.get id
	   }
	
	
	/* ************************* SEARCH ModelS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each 
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned 
	 * List 
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<Model> search(String sSearch) {
	   def searchResult = Model.search(sSearch)
	   searchResult.results
	   }
	
	
	/* ************************* LIST ModelS***********************************************
	 * requires that the authenticated user have ROLE_USER sand read or admin permission on each 
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned 
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<Model> list(Map parameters) {
		Model.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { Model.count() }
	
	/* ************************* UPDATE ModelS***********************************************
	 *  requires that the authenticated user have write or admin permission on the model instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#modelInstance, write) or hasPermission(#modelInstance, admin)")
	void update(Model modelInstance, Map parameters) {

	   modelInstance.properties = parameters
	   
	   modelInstance.save(flush: true)
	   
	   // add/remove value domains
	   if(parameters?.requiredDataElements){
			catalogueElementService.linkRelations(modelInstance, parameters?.requiredDataElements, 'RequiredModelElement')
		}
		if(parameters?.optionalDataElements){
			catalogueElementService.linkRelations(modelInstance, parameters?.optionalDataElements, 'OptionalModelElement')
		}
		if(parameters?.referenceDataElements){
			catalogueElementService.linkRelations(modelInstance, parameters?.referenceDataElements, 'ReferenceModelElement')
		}
		if(parameters?.mandatoryDataElements){
			catalogueElementService.linkRelations(modelInstance, parameters?.mandatoryDataElements, 'MandatoryModelElement')
		}
	}
	
	
	
	/* ************************* DELETE ModelS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to 
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#modelInstance, delete) or hasPermission(#modelInstance, admin)")
	void delete(Model modelInstance) {
		
		modelInstance.prepareForDelete()
		modelInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl modelInstance
   }
	

	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#modelInstance, admin)")
	void deletePermission(Model modelInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(modelInstance)
		
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
