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
		
	void addPermission(PathwaysModel pathwaysModel, String roleOrUsername, int permission){
		
		addPermission pathwaysModel, roleOrUsername,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	/* **************************** ADD GROUP PERMISSIONS *****************************************
	 * adds permission to the relevant groups
	 ********************************************************************************* */
	
	void addGroupPermissions(PathwaysModel pathwaysModelInstance, Collection roles){
		
		//Grant admin users administrative permissions
		addPermission pathwaysModelInstance, 'ROLE_ADMIN', BasePermission.ADMINISTRATION
		
		//grant users in the same groups as the current user read/write/delete permissions on the pathwaysModelInstance
		roles.each{ role ->
			role = role.toString()
			if(role!="ROLE_USER"){
				addPermission pathwaysModelInstance, role , BasePermission.READ
				addPermission pathwaysModelInstance, role , BasePermission.WRITE
				addPermission pathwaysModelInstance, role , BasePermission.DELETE
			}
		}
		
	}
	
	
	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#pathwaysModel, admin)")
	@Transactional
	void addPermission(PathwaysModel pathwaysModel, String roleOrUsername, Permission permission) {
	   aclUtilService.addPermission pathwaysModel, roleOrUsername, permission
	}
	

	/* ************************* CREATE PATHWAY***********************************
	 * requires that the authenticated user to have ROLE_USER to create a data element
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	PathwaysModel create(Map parameters) {
		
		def pathwaysModelInstance = new PathwaysModel(
			name: parameters?.name,
			description: parameters?.description,
			versionNo: parameters?.versionNo,
			isDraft: parameters?.isDraft
			);
		
		if(parameters?.parentNodeId){
			def parentNode = nodeService.get(parameters?.parentNodeId)
			pathwaysModelInstance.parentNode = parentNode
		}
		
		//save the dataElement
		if(!pathwaysModelInstance.save(flush:true)){
			// FIXME should throw an error here with the errors from the instance
			return pathwaysModelInstance
		}
		
		// Grant the current user principal administrative permission
		addPermission pathwaysModelInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		// Grant all users in the same group apart from the ROLE_USER group the current user principal permission to read and write  and delete
		addGroupPermissions pathwaysModelInstance, springSecurityService.principal.getAuthorities()
		
		//return the data element to the consumer (the controller)
		pathwaysModelInstance
	}

    /**
     * Return a list of top-level pathways, with a given set of searach criteria
     * @param criteria The search criteria
     * @return a list of pathways (top level only)
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    List<PathwaysModel> getTopLevelPathways() {
        return PathwaysModel.findAllByParentNode(null);
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
	
	/* ************************* LIST VALUED DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<PathwaysModel> findAll(Closure closure) {
		PathwaysModel.findAll closure
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
		

		
		def updatedNodes = parameters.nodes

		
		

		
		updatedNodes.each { updatedNode ->

			def nodeInstance = nodeService.get(updatedNode.id)
			
			if(updatedNode?.subPathwayId){
				
				def subPathwayId = updatedNode?.subPathwayId
				def subPathway = this.get(subPathwayId)
				def node = nodeService.update(nodeInstance, updatedNode, subPathway)
				
			}else{
				
			
				def node = nodeService.update(nodeInstance, updatedNode)
				

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
	   //FIXME we need to create a custom audit log class that all the classes implement
	   pathwaysModelInstance.auditLog =  springSecurityService.getCurrentUser().username + " edited this pathway on: " + new Date().toString()
	   pathwaysModelInstance.save(flush:true);
	   
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
	void deletePermission(PathwaysModel pathwaysModelInstance, String roleOrUsername, Permission permission) {
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
