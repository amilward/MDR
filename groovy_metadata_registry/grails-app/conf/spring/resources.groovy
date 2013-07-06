import groovy_metadata_registry.APIAuthenticationFilters

// Place your Spring DSL code here
beans = {
	
	apiAuthFilter(APIAuthenticationFilters) {
		authenticationManager = ref("authenticationManager")
		rememberMeServices = ref("rememberMeServices")
		springSecurityService = ref("springSecurityService")
	}
	
}
