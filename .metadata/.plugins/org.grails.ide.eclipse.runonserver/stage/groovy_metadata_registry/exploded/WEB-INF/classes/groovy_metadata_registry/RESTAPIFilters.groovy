package groovy_metadata_registry

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

class RESTAPIFilters {

    def filters = {
        testREST(uri:'/api/**') {
            before = {
				
				Authentication auth
				UsernamePasswordAuthenticationToken upat

				def username = params.j_username
				def password = params.j_password
				
				if ( username && password ) {
					try {upat = new UsernamePasswordAuthenticationToken(username, password)
					
				   if (upat != null) {
					   auth = authenticationManager.authenticate(upat)
						
					   logger.debug("Authentication success: " + auth);
						
					   onSuccessfulAuthentication(request, response, auth)
				   }
			   } catch (AuthenticationException authenticationException) {
			   
			   
					}
				}	
				
				
				

            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
