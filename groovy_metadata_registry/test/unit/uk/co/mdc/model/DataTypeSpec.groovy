package uk.co.mdc.model

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(DataType)
class DataTypeSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void "test something"() {
		
		Map nhsStatusEnumeration = ["01": "Number present and verified",
			"02": "Number present but not traced",
			"03": "Trace required", "04": "Trace attempted - No match or multiple match found",
			"05": "Trace needs to be resolved - (NHS Number or patient detail conflict)",
			"06": "Trace in progress" ,
			"07": "Number not present and trace not required",
			"08": "Trace postponed (baby under six weeks old)"];
		
		dataType = new DataType(name:"NHS NUMBER STATUS INDICATOR", enumerated: true, enumerations: nhsStatusEnumeration);
		
		
		dataType2 = new DataType(name:"NHS NUMBER STATUS INDICATOR", enumerated: true, enumerations: nhsStatusEnumeration);
		
		
		
		assert dataType2.validate();
		
	}
}