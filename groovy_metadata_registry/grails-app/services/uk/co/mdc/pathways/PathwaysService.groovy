package uk.co.mdc.pathways

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional

import uk.co.mdc.pathways.PathwaysModel;


class PathwaysService {

   	static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
    
	def nodeService
	def linkService

	/* **************************** ADD PERMISSIONS *****************************************
	 * calls add permission with the relevant permission when called with an integer
	 * permission input
	 ********************************************************************************* */
		
	void addPermission(PathwaysModel pathwaysModel, String username, int permission){
		
		addPermission pathwaysModel, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#pathwaysModel, admin)")
	@Transactional
	void addPermission(PathwaysModel pathwaysModel, String username, Permission permission) {
	   aclUtilService.addPermission pathwaysModel, username, permission
	}
	
	
	
	/* ************************* CREATE DATA ELEMENTS***********************************
	 * requires that the authenticated user to have ROLE_USER to create a data element
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	PathwaysModel create(Map parameters) {
		
		def pathwaysModelInstance = new PathwaysModel(
			name: parameters?.name,
			description: parameters?.description,
			isDraft: parameters?.isDraft
			);
		
		if(parameters?.parentNodeId){
			def parentNode = nodeService.get(parameters?.parentNodeId)
			pathwaysModelInstance.parentNode = parentNode
		}
		
		println(pathwaysModelInstance.parentNode)
		
		//save the dataElement
		if(!pathwaysModelInstance.save(flush:true)){
			// FIXME should throw an error here with the errors from the instance
			return pathwaysModelInstance
		}
		
		// Grant the current user principal administrative permission
		addPermission pathwaysModelInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		addPermission pathwaysModelInstance, 'admin', BasePermission.ADMINISTRATION
		
		
		//FIXME we are grantin all users all permissions at the moment
		addPermission  pathwaysModelInstance, 'user', BasePermission.ADMINISTRATION
		
		//return the data element to the consumer (the controller)
		pathwaysModelInstance
	}
	
	
	/* ************************* GET VALUED DOMAINS ***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.pathways.PathwaysModel", read) or hasPermission(#id, "uk.co.mdc.pathways.PathwaysModel", admin)')
	PathwaysModel get(long id) {
	   PathwaysModel.get id
	   }
	
	
	/* ************************* SEARCH VALUED DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<PathwaysModel> search(String sSearch) {
		def searchResult = PathwaysModel.search(sSearch)
	    searchResult.results
	}
	
	
	/* ************************* LIST VALUED DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<PathwaysModel> list(Map parameters) {
		PathwaysModel.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { PathwaysModel.count() }
	
	
	/* ************************* UPDATE VALUE DOMAINS***********************************************
	 *  requires that the authenticated user have write or admin permission on the value domain instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#pathwaysModelInstance, write) or hasPermission(#pathwaysModelInstance, admin)")
	PathwaysModel update(PathwaysModel pathwaysModelInstance, Map parameters) {
				
		//update nodes
		
		//println(parameters)
		
		def updatedNodes = parameters.nodes
		
		
		println(pathwaysModelInstance?.parentNode)
		
		updatedNodes.each { updatedNode ->

			def nodeInstance = nodeService.get(updatedNode.id)
			
			if(updatedNode?.subPathwayId){
				
				def subPathwayId = updatedNode?.subPathwayId
				def subPathway = this.get(subPathwayId)
				def node = nodeService.update(nodeInstance, updatedNode, subPathway)
				
			}else{
				println('before?')
				println(pathwaysModelInstance?.parentNode)
				def node = nodeService.update(nodeInstance, updatedNode)
				println('why the hell?')
				println(pathwaysModelInstance?.parentNode)
			}

		}
		
		
		//update links
		
		//update nodes
		
		def updatedLinks = parameters.links
		
		updatedLinks.each { updatedLink ->
			
			def linkInstance = linkService.get(updatedLink.id)
			
			def link = linkService.update(linkInstance, updatedLink)
			
		}
		
	   pathwaysModelInstance.properties = parameters
	   pathwaysModelInstance.save();
	   
	   pathwaysModelInstance
	   
	}
	
	
	/* ************************* DELETE DATA ELEMENTS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#pathwaysModelInstance, delete) or hasPermission(#pathwaysModelInstance, admin)")
	void delete(PathwaysModel pathwaysModelInstance) {
		
		//pathwaysModelInstance.prepareForDelete()
		pathwaysModelInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl pathwaysModelInstance
   }
	
	
	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#pathwaysModelInstance, admin)")
	void deletePermission(PathwaysModel pathwaysModelInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(pathwaysModelInstance)
		
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
