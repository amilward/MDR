package uk.co.mdc.model

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional
import uk.co.mdc.CollectionBasket


	/* *********************************************************************
	 * This service allows the user to access the collection model
	 * It will be called by the collection controller and is written with
	 * security considerations in mind
	 * *************************************************************** */


class CollectionService {

    static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
	
	
	/* **************************** ADD PERMISSIONS *****************************************
	 * calls add permission with the relevant permission when called with an integer 
	 * permission input
	 ********************************************************************************* */
		
	void addPermission(Collection collection, String username, int permission){
		
		addPermission collection, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#collection, admin)")
	@Transactional
	void addPermission(Collection collection, String username, Permission permission) {
	   aclUtilService.addPermission collection, username, permission
	}
	
	/* ************************* CREATE COLLECTIONS FROM COLLECTION BASKET***********************************
	 * requires that the authenticated user to have ROLE_USER to create a collection
	 ********************************************************************************* */
	
	@Transactional 
	@PreAuthorize("hasRole('ROLE_USER')") 
	Collection createFromBasket(Map parameters) { 
		
		//save the collection
		
		Collection collectionInstance = new Collection(refId: parameters?.refId, name: parameters?.name, description: parameters?.description)
		
		if(collectionInstance.save(flush:true)){
			
			parameters.dataElementIds.each{ de ->
				
				def dataElement = DataElement.get(de)
				
				def dataElementSchemaInfo = parameters["dataElement_" + de]
				
				def schemaSpecification = SchemaSpecification.MANDATORY
				
				if(dataElementSchemaInfo=='required'){
				
					schemaSpecification = SchemaSpecification.REQUIRED
				
				}else if(dataElementSchemaInfo=='optional'){
				
					schemaSpecification = SchemaSpecification.OPTIONAL
				
				}else if(dataElementSchemaInfo=='reference'){
				
					schemaSpecification = SchemaSpecification.X
				
				}
				
				DataElementCollection.link(dataElement, collectionInstance, schemaSpecification)
				
				def collectionBasket = CollectionBasket.get(parameters?.collection_basket_id)
				
				collectionBasket.dataElements.clear();
				collectionBasket.save(flush: true)
				
			}
			
		}
		
		// Grant the current user principal administrative permission 
		
		addPermission collectionInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission collectionInstance, 'admin', BasePermission.ADMINISTRATION
		
		//return the collection to the consumer (the controller)
		
		collectionInstance 
		
		}
	
	
	/* ************************* CREATE COLLECTIONS ***********************************
	 * requires that the authenticated user to have ROLE_USER to create a collection
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	Collection create(Map parameters) {
		
		//save the collection
		
		Collection collectionInstance = new Collection(parameters)
		if(!collectionInstance.save(flush:true)){
			return collectionInstance
		}
		
		linkDataElements(collectionInstance, parameters?.mandatoryDataElements, parameters?.requiredDataElements, parameters?.optionalDataElements, parameters?.referenceDataElements)

		// Grant the current user principal administrative permission
		
		addPermission collectionInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission collectionInstance, 'admin', BasePermission.ADMINISTRATION
		
		//return the collection to the consumer (the controller)
		
		collectionInstance
		
		}
	
	
	
	/* ************************* GET COLLECTIONS***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.model.Collection", read) or hasPermission(#id, "uk.co.mdc.model.Collection", admin)')
	Collection get(long id) {
	   Collection.get id
	   }
	
	
	/* ************************* SEARCH COLLECTIONS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each 
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned 
	 * List 
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<Collection> search(String sSearch) {
	   def searchResult = Collection.search(sSearch)
	   searchResult.results
	   }
	
	
	/* ************************* LIST COLLECTIONS***********************************************
	 * requires that the authenticated user have ROLE_USER sand read or admin permission on each 
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned 
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<Collection> list(Map parameters) {
		Collection.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { Collection.count() }
	
	/* ************************* UPDATE COLLECTIONS***********************************************
	 *  requires that the authenticated user have write or admin permission on the collection instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#collectionInstance, write) or hasPermission(#collectionInstance, admin)")
	void update(Collection collectionInstance, Map parameters) {

	   collectionInstance.properties = parameters
	   
	   collectionInstance.save(flush: true)
	   
	   // add/remove value domains
	   linkDataElements(collectionInstance, parameters?.mandatoryDataElements, parameters?.requiredDataElements, parameters?.optionalDataElements, parameters?.referenceDataElements)
	   
	}
	
	
	
	/* ************************* DELETE COLLECTIONS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to 
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#collectionInstance, delete) or hasPermission(#collectionInstance, admin)")
	void delete(Collection collectionInstance) {
		
		collectionInstance.prepareForDelete()
		collectionInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl collectionInstance
   }
	
	
	/* ************************* REMOVE DATA ELEMENT FROM COLLECTIONS***********************************************
	 * requires that the authenticated user have write or admin permission on the report instance to
	 * remove a data element
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#collectionInstance, write) or hasPermission(#collectionInstance, admin)")
	
	void  removeDataElement(Collection collectionInstance, DataElement dataElement){
	
		collectionInstance.removeFromDataElementCollections(dataElement)
	
	}
	
	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#collectionInstance, admin)")
	void deletePermission(Collection collectionInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(collectionInstance)
		
		// Remove all permissions associated with this particular
		// recipient (string equality to KISS)
		
		acl.entries.eachWithIndex {
			entry, i -> if (entry.sid.equals(recipient) && entry.permission.equals(permission)) {
				acl.deleteAce i
				}
			}
		
		aclService.updateAcl acl
		
		}
	
	
	
	/* ************************* COLLECTION LINKAGE FUNCTIONS************************
	 * links the collection with the data elements specified via a link table
	 ********************************************************************************* */
	
	def linkDataElements(collectionInstance, mandatoryDataElements, requiredDataElements, optionalDataElements, referenceDataElements){
		
		def currentMandatoryElements = collectionInstance.mandatoryDataElementCollections()
		//unlink mandatory data elements if they have been removed during an update
		unlinkExtraDataElements(mandatoryDataElements, currentMandatoryElements, collectionInstance)
		
		def currentRequiredElements = collectionInstance.requiredDataElementCollections()
		//unlink required data elements if they have been removed during an update
		unlinkExtraDataElements(requiredDataElements, currentRequiredElements, collectionInstance)
		
		def currentOptionalElements = collectionInstance.optionalDataElementCollections()
		//unlink optional data elements if they have been removed during an update
		unlinkExtraDataElements(optionalDataElements, currentOptionalElements, collectionInstance)
			
		def currentReferenceElements = collectionInstance.referenceDataElementCollections()
		//unlink reference data elements if they have been removed during an update
		unlinkExtraDataElements(referenceDataElements, currentReferenceElements, collectionInstance)
		
		
		if(mandatoryDataElements && mandatoryDataElements!=null){
			
				linkDataElementType(collectionInstance, mandatoryDataElements, SchemaSpecification.MANDATORY)
			
		}
		
		if(requiredDataElements && requiredDataElements!=null){
			
				linkDataElementType(collectionInstance, requiredDataElements, SchemaSpecification.REQUIRED)
			
		}
		
		if(optionalDataElements && optionalDataElements!=null){
			
				linkDataElementType(collectionInstance, optionalDataElements, SchemaSpecification.OPTIONAL)
			
		}
		
		if(referenceDataElements && referenceDataElements!=null){
			
				linkDataElementType(collectionInstance, referenceDataElements, SchemaSpecification.X)
			
		}
		
	}
	
	/* ************************* COLLECTION LINKAGE FUNCTIONS************************
	 * unlinks the data elements that have been removed during an update of the collection
	 ********************************************************************************* */
	
	def unlinkExtraDataElements(newDataElements, currentElements, collectionInstance){
		
		if(newDataElements!=null){
			
		if (newDataElements instanceof String) {
			
			//remove all the data elements from the collections that aren't this one
			currentElements.each{ de ->
				if(newDataElements!=de.id.toString()){
					DataElementCollection.unlink(de, collectionInstance)
				}
			}
			
		}
		
		if (newDataElements instanceof String[]) {
			
			//remove all the data elements in the collections that aren't this one
			currentElements.each{ de ->
				if(!newDataElements.contains(de.id.toString())){
						DataElementCollection.unlink(de, collectionInstance)
				}
			}
		
		}
			
		}else{
		
			//remove all the data elements collections
			currentElements.each{ de ->
						DataElementCollection.unlink(de, collectionInstance)
			}
		}
		
	}
	
	/* ************************* COLLECTION LINKAGE FUNCTIONS************************
	 * links the collection with the data elements with regards to the schema specification
	 * i.e. mandatory/required/optional/reference. this is done specified via using a link 
	 * table (and link class)
	 * ********************* */
	
	def linkDataElementType(collectionInstance, dataElements, schemaSpecification){
		
		if(dataElements!=null){
			
			
			if (dataElements instanceof String) {
				DataElement dataElement =  DataElement.get(dataElements)
				if(dataElement){
					DataElementCollection.link(dataElement, collectionInstance, schemaSpecification)
				}
			} else if (dataElements instanceof String[]) {
			
			
				  for (dataElementID in dataElements){
					  DataElement dataElement =  DataElement.get(dataElementID)
					  if(dataElement){
						  DataElementCollection.link(dataElement, collectionInstance, schemaSpecification)
					  }
				  }
			}
			
		}
	}
	
}
