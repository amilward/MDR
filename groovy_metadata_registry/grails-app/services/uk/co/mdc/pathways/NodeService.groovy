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

class NodeService {

   	static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
	

	/* **************************** ADD PERMISSIONS *****************************************
	 * calls add permission with the relevant permission when called with an integer
	 * permission input
	 ********************************************************************************* */
		
	void addPermission(Node node, String username, int permission){
		
		addPermission node, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	/*
	 * requires that the authenticated user have admin permission on the report instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#node, admin)")
	@Transactional
	void addPermission(Node node, String username, Permission permission) {
	   aclUtilService.addPermission node, username, permission
	}
	
	
	
	/* ************************* CREATE DATA ELEMENTS***********************************
	 * requires that the authenticated user to have ROLE_USER to create a data element
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	Node create(Map parameters) {
		

		println('new node')
		println(parameters)

		def sourceNode
		def targetNode

		def nodeInstance = new Node(
			refId: parameters?.refId,
			name: parameters?.name,
			x: parameters?.x,
			y: parameters?.y,
			description: parameters?.description
			)
					
			if(!nodeInstance.save(flush:true)){
				// FIXME should throw an error here with the errors from the instance
				return nodeInstance
			}
			
			// Grant the current user principal administrative permission
			addPermission nodeInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
			
			//Grant admin user administrative permissions
			addPermission nodeInstance, 'admin', BasePermission.ADMINISTRATION
		
		
		if(nodeInstance && parameters?.pathwaysModelId){

			def pathway = PathwaysModel.get(parameters?.pathwaysModelId)
			
			if(pathway){
				pathway.addToPathwayElements(nodeInstance)
			}
		}
		
		println(nodeInstance)
		//return the data element to the consumer (the controller)
		return nodeInstance
	}
	
	
	/* ************************* GET VALUED DOMAINS ***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.pathways.Node", read) or hasPermission(#id, "uk.co.mdc.pathways.Node", admin)')
	Node get(long id) {
	   Node.get id
	   }
	
	
	/* ************************* SEARCH VALUED DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<Node> search(String sSearch) {
		def searchResult = Node.search(sSearch)
		searchResult.results
	}
	
	
	/* ************************* LIST VALUED DOMAINS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<Node> list(Map parameters) {
		Node.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { Node.count() }
	
	
	/* ************************* UPDATE VALUE DOMAINS***********************************************
	 *  requires that the authenticated user have write or admin permission on the value domain instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#nodeInstance, write) or hasPermission(#nodeInstance, admin)")
	Node update(Node nodeInstance, Map parameters) {
		 
		nodeInstance.properties = parameters
		nodeInstance.save()
		
		nodeInstance
	}
	
	
	/* ************************* DELETE DATA ELEMENTS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#nodeInstance, delete) or hasPermission(#nodeInstance, admin)")
	void delete(Node nodeInstance) {
		
		//make sure we have the latest copy of the node
		nodeInstance.refresh()
		
		def sources  = Link.findAllWhere(source: nodeInstance)
		
		println('removing link sources and targets')
		
		sources.each{ link ->
			
			link.delete(flush:true,failOnError:true)
		}
		
		//targets
		def targets = Link.findAllWhere(target: nodeInstance)
		
		targets.each{ link->
			
			link.delete(flush:true,failOnError:true)
			
		}
		
		//nodeInstance.prepareForDelete()
		nodeInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl nodeInstance
   }
	
	
	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#nodeInstance, admin)")
	void deletePermission(Node nodeInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(nodeInstance)
		
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
