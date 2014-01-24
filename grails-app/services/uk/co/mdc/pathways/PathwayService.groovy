package uk.co.mdc.pathways

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional

class PathwayService {

   	static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService


    // TODO get
    // TODO create
    // TODO update
    // TODO delete

	/* **************************** ADD PERMISSIONS *****************************************
	 * calls add permission with the relevant permission when called with an integer
	 * permission input
	 ********************************************************************************* */
		
	void addPermission(Pathway pathwaysModel, String roleOrUsername, int permission){
		addPermission pathwaysModel, roleOrUsername, aclPermissionFactory.buildFromMask(permission)
	}

    @PreAuthorize("hasPermission(#pathway, admin)")
    @Transactional
    void addPermission(Pathway pathway, String roleOrUsername, Permission permission) {
        aclUtilService.addPermission pathway, roleOrUsername, permission
    }
	
	/* **************************** ADD GROUP PERMISSIONS *****************************************
	 * adds permission to the relevant groups
	 ********************************************************************************* */
	
//	void addGroupPermissions(PathwaysModel pathwaysModelInstance, Collection roles){
//
//		//Grant admin users administrative permissions
//		addPermission pathwaysModelInstance, 'ROLE_ADMIN', BasePermission.ADMINISTRATION
//
//		//grant users in the same groups as the current user read/write/delete permissions on the pathway
//		roles.each{ role ->
//			role = role.toString()
//			if(role!="ROLE_USER"){
//				addPermission pathwaysModelInstance, role , BasePermission.READ
//				addPermission pathwaysModelInstance, role , BasePermission.WRITE
//				addPermission pathwaysModelInstance, role , BasePermission.DELETE
//			}
//		}
//
//	}

    @Transactional
    @PreAuthorize("hasPermission(#pathway, write) or hasPermission(#pathway, admin)")
    Pathway update(Pathway pathway) {
        pathway.save()
        return pathway
    }

    /**
     * Deletes a pathway, and its associated ACL
     * @param pathway The pathway to delete
     */
    @Transactional @PreAuthorize("hasPermission(#pathway, delete) or hasPermission(#pathway, admin)")
    void delete(Pathway pathway) {
        pathway.delete()
        aclUtilService.deleteAcl pathway
    }
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	Pathway create(Pathway pathway) {

        pathway.save()

        // Update permissions for owner (read for all, write + delete for owner
        addPermission pathway, springSecurityService.authentication.name, BasePermission.READ
        addPermission pathway, springSecurityService.authentication.name, BasePermission.WRITE
        addPermission pathway, springSecurityService.authentication.name, BasePermission.DELETE
        return pathway
	}

    /**
     * Return a list of top-level pathways, with a given set of search criteria.
     * The structure of the searchCriteria should be (e.g.):
     *
     * {
     *  name: "pathway 1",
     *  isDraft: false
     * }
     *
     * @param searchCriteria The search criteria
     * @return a list of pathways (top level only)
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    List<Pathway> topLevelPathways(def searchCriteria) {

        List<Pathway> pathways
        if(searchCriteria == null){
            pathways = Pathway.list()
        }else{

            def nodeProps = Pathway.metaClass.properties*.name
            pathways = Pathway.withCriteria {
                and {
                    searchCriteria.each { field, value ->
                        if (nodeProps.grep(field)) {
                            eq(field, value)
                        }
                    }
                }
            }
        }
        // FIXME this should be in the criteria, but I had problems getting that to work :(
        println "---"
        pathways.each { pathway ->
            println pathway.class
        }
        return pathways.findAll { it.class == Pathway }

    }


//	@PostAuthorize("hasPermission(returnObject, read) or hasPermission(returnObject, admin)")
//	Pathway get(long id) {
//	   Pathway.get id
//	}
	

//	@PreAuthorize("hasRole('ROLE_USER')")
//	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
//	List<Pathway> search(String sSearch) {
//		def searchResult = PathwaysModel.search(sSearch)
//	    searchResult.results
//	}


//	@PreAuthorize("hasRole('ROLE_USER')")
//	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
//	List<Pathway> list(Map parameters) {
//		Pathway.list parameters
//	}


//    /**
//     * no restrictions on the count method
//     */
//	int count() {
//        PathwaysModel.count()
//    }
	



	
//	@Transactional @PreAuthorize("hasPermission(#pathway, admin)")
//	void deletePermission(PathwaysModel pathwaysModelInstance, String roleOrUsername, Permission permission) {
//		def acl = aclUtilService.readAcl(pathwaysModelInstance)
//
//		// Remove all permissions associated with this particular
//		// recipient (string equality to KISS)
//		acl.entries.eachWithIndex {
//			entry, i -> if (entry.sid.equals(roleOrUsername) && entry.permission.equals(permission)) {
//				acl.deleteAcl i
//			}
//		}
//		aclService.updateAcl acl
//	}
}
