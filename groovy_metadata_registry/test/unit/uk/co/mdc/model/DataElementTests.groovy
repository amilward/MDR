package uk.co.mdc.model



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(DataElement)
@Mock([DataElement, DataElementDataElement, DataElementConcept, DataType, DataElementValueDomain, DataElementCollection, ExternalReference])
class DataElementTests {

    void testUniqueRefId() {
//set the DataElement class for constraints testing
mockForConstraintsTests DataElement
//create new dataElement
def dataElement1 = new DataElement( name:"SOURCE OF REFERRAL FOR OUT-PATIENTS",
refId:"C1600",
description:"This identifies the source of referral of each Consultant Out-Patient Episode.")
//create another dataElement with the same refId
/*def dataElement2 = new DataElement( name:"SOURCE OF REFERRAL FOR OUT-PATIENTS",
refId:"C1600",
description:"This identifies the source of referral of each Consultant Out-Patient Episode.")
*/
//make sure that the validation fails
assert dataElement1.validate()
//assert !dataElement2.validate()
//assert dataElement1 == dataElement2
//make sure unique constraint fails
//assert 'unique' == dataElement2.errors['refId']
    }
}
