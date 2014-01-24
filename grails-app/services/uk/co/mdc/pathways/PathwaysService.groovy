package uk.co.mdc.pathways

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional

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
		
		//grant users in the same groups as the current user read/write/delete permissions on the pathway
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


	//@PostAuthorize("hasPermission(returnObject, read) or hasPermission(returnObject, admin)")
	PathwaysModel get(long id) {
	   PathwaysModel.get id
	}
	

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<PathwaysModel> search(String sSearch) {
		def searchResult = PathwaysModel.search(sSearch)
	    searchResult.results
	}


	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<PathwaysModel> list(Map parameters) {
		PathwaysModel.list parameters
	}

	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<PathwaysModel> findAll(Closure closure) {
		PathwaysModel.findAll closure
	}


    /**
     * no restrictions on the count method
     */
	int count() {
        PathwaysModel.count()
    }
	

	@Transactional
	@PreAuthorize("hasPermission(#pathway, write) or hasPermission(#pathway, admin)")
	PathwaysModel update(PathwaysModel pathway, Map parameters) {

        pathway.properties = parameters
        pathway.save()

        parameters.nodes.each { updatedNode ->
            def node = Node.get(updatedNode.id)
            node.properties = updatedNode
            node.save()
        }

        parameters.links.each { updatedLink ->
            def link = Link.get(updatedLink.id)
            link.properties = updatedLink
            link.save()
        }


        pathway
	}


	@Transactional @PreAuthorize("hasPermission(#pathway, delete) or hasPermission(#pathway, admin)")
	void delete(PathwaysModel pathway) {

		pathway.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl pathway
   }

	
	@Transactional @PreAuthorize("hasPermission(#pathway, admin)")
	void deletePermission(PathwaysModel pathwaysModelInstance, String roleOrUsername, Permission permission) {
		def acl = aclUtilService.readAcl(pathwaysModelInstance)
		
		// Remove all permissions associated with this particular
		// recipient (string equality to KISS)
		acl.entries.eachWithIndex {
			entry, i -> if (entry.sid.equals(roleOrUsername) && entry.permission.equals(permission)) {
				acl.deleteAcl i
			}
		}
		aclService.updateAcl acl
	}
}
