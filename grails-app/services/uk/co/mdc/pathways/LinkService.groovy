package uk.co.mdc.pathways

import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.acls.model.Permission;
import org.springframework.transaction.annotation.Transactional;

class LinkService {
	
	static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
	

	/* **************************** ADD PERMISSIONS *****************************************
	 * calls add permission with the relevant permission when called with an integer
	 * permission input
	 ********************************************************************************* */
		
	void addPermission(Link link, String username, int permission){
		
		addPermission link, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#link, admin)")
	@Transactional
	void addPermission(Link link, String username, Permission permission) {
	   aclUtilService.addPermission link, username, permission
	}
	
	
	
	/* ************************* CREATE DATA ELEMENTS***********************************
	 * requires that the authenticated user to have ROLE_USER to create a data element
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	Link create(Map parameters) {

		def sourceNode
		def targetNode
		def linkInstance

		if(parameters.source){
			def sourceID = parameters.source.toString().replace( 'node', '' )
			sourceNode = Node.get(sourceID)
		}

		if(parameters.target){
			def targetID = parameters.target.toString().replace( 'node', '' )
			targetNode = Node.get(targetID)
		}

		if(sourceNode && targetNode){

			 linkInstance = new Link(
					name: parameters?.name,
					source: sourceNode,
					target: targetNode
					)

			if(!linkInstance.save(flush:true)){
				// FIXME should throw an error here with the errors from the instance
				return linkInstance
			}
			
			// Grant the current user principal administrative permission
			addPermission linkInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
			
			//Grant admin user administrative permissions
			addPermission linkInstance, 'admin', BasePermission.ADMINISTRATION
			
			//FIXME we are grainting all users all permissions at the moment
			addPermission linkInstance, 'user', BasePermission.ADMINISTRATION
		}
		
		if(linkInstance && parameters?.pathwaysModelId){

			def pathway = PathwaysModel.get(parameters?.pathwaysModelId)
			
			if(pathway){
				pathway.addToPathwayElements(linkInstance)
			}
		}
		//return the data element to the consumer (the controller)
		return linkInstance
	}
	
	
	/* ************************* GET VALUED DOMAINS ***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.pathways.Link", read) or hasPermission(#id, "uk.co.mdc.pathways.Link", admin)')
	Link get(long id) {
	   Link.get id
	   }
	
	
	/* ************************* SEARCH VALUED DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<Link> search(String sSearch) {
		def searchResult = Link.search(sSearch)
		searchResult.results
	}
	
	
	/* ************************* LIST VALUED DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<Link> list(Map parameters) {
		Link.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { Link.count() }
	
	
	/* ************************* UPDATE LINKS***********************************************
	 *  requires that the authenticated user have write or admin permission on the value domain instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#linkInstance, write) or hasPermission(#linkInstance, admin)")
	Link update(Link linkInstance, Map parameters) {

		linkInstance.name = parameters?.name
		linkInstance.description = parameters?.description
		
		def targetId = parameters.target.toString().replace( 'node', '' );

		if(linkInstance?.target?.id.toString()!=targetId){
			def newTargetNode = Node.get(targetId)
			if(newTargetNode){
				linkInstance.target = newTargetNode
			}
		}
		
		linkInstance.save()
		
		linkInstance
	}
	
	
	/* ************************* DELETE DATA ELEMENTS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#linkInstance, delete) or hasPermission(#linkInstance, admin)")
	void delete(Link linkInstance) {
		
		//linkInstance.prepareForDelete()
		linkInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl linkInstance
   }
	
	
	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#linkInstance, admin)")
	void deletePermission(Link linkInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(linkInstance)
		
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
