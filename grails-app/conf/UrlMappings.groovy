import java.rmi.Naming.ParsedNamingURL;

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.acls.model.NotFoundException

class UrlMappings {

	static mappings = {
		
		// Default for controllers
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		
		name pendingUsers: "/role/pendingUsers"( controller: "role", action: "listPendingUsers" )
		name importData: "/admin/importData"(view:"admin/importData")

		"/"(controller:"index", action: "index")
		"403"(controller: "errors", action: "error403") 
		"404"(controller: "errors", action: "error404") 
		"500"(controller: "errors", action: "error500") 
		"500"(controller: "errors", action: "error403", exception: AccessDeniedException) 
		"500"(controller: "errors", action: "error403", exception: NotFoundException) 
	}
}
