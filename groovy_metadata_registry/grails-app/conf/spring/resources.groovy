import groovy_metadata_registry.APIAuthenticationFilters
import util.marshalling.CustomObjectMarshallers
import uk.co.mdc.model.DataElementMarshaller
import uk.co.mdc.forms.FieldMarshaller
import uk.co.mdc.forms.QuestionElementMarshaller
import uk.co.mdc.model.ValueDomainMarshaller
import uk.co.mdc.model.DataElementConceptMarshaller
import uk.co.mdc.model.ConceptualDomainMarshaller
import uk.co.mdc.model.CollectionMarshaller

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
		new FieldMarshaller(),
		new ValueDomainMarshaller(),
		new DataElementConceptMarshaller(),
		new CollectionMarshaller(),
		new ConceptualDomainMarshaller(),
		new QuestionElementMarshaller()
		]
	}
}



