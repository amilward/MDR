package uk.co.mdc.catalogue

import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import spock.lang.*

/**
 *
 */
class DataElementServiceSpec extends Specification {

    def dataElementService
    Map dataElementMapNoRelations

    def setup() {


        /*
        SAMPLE JSON
        {
            "id":27,
            "version":5,
            "name":"ETHNIC CATEGORY",
            "description":"The ethnicity of a PERSON, as specified by the PERSON.)",
            "definition":null,
            "versionNumber":"0.4",
            "status":"DRAFT",
            "relations":[
                {
                    "id":28,
                    "name":"NHS ETHNIC CATEGORY",
                    "relationshipType":"DataValue"
                },
                {
                    "id":19,
                    "name":"CORE - REFERRALS",
                    "relationshipType":"ModelElement"
                }
        ]
        }*/

        //set up variables


        dataElementMapNoRelations = [
            name: "test dataElement1",
            description: "test data element description",
            status: "DRAFT",
            relations:[[
                            "id":28,
                            "name":"NHS ETHNIC CATEGORY",
                            "relationshipType":"DataValue"
                        ],
                        [
                            "id":19,
                            "name":"CORE - REFERRALS",
                            "relationshipType":"ModelElement"
                        ]
            ]
        ]
    }

    def cleanup() {
        SCH.clearContext()
    }

    def "create new data element without relations"() {
        when:
        DataElement newDataElement = SpringSecurityUtils.doWithAuth('admin') { dataElementService.create(dataElementMapNoRelations)}
        then:
        assert newDataElement.name == "test dataElement1"
        assert newDataElement.relations().size()==2
    }


}
