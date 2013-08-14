import groovy_metadata_registry.APIAuthenticationFilters
import util.marshalling.CustomObjectMarshallers
import uk.co.mdc.model.DataElementMarshaller
import uk.co.mdc.model.FieldMarshaller

// Place your Spring DSL code here
beans = {
	
	apiAuthFilter(APIAuthenticationFilters) {
		authenticationManager = ref("authenticationManager")
		rememberMeServices = ref("rememberMeServices")
		springSecurityService = ref("springSecurityService")
	}
	
	
	customObjectMarshallers( CustomObjectMarshallers ) {
		marshallers = [
		new DataElementMarshaller(),
		new FieldMarshaller()
		]
	}
}



