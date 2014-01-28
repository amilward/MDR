package uk.co.mdc.catalogue

import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import spock.lang.*

/**
 *
 */
class DataElementServiceSpec extends Specification {

    def dataElementService
    Map dataElementMapCreate
    Map dataElementMapUpdate

    def setup() {
        //set up variables


        dataElementMapCreate = [
                name: "test dataElement1",
                description: "test data element description",
                status: "DRAFT",
                relations:[[
                                "id": 27,
                                "name": "ETHNIC CATEGORY",
                                "type": "ParentChild",
                                "direction": "ParentOf",
                                "className": "DataElement"
                            ]
                ]
        ]

        dataElementMapUpdate = [
                    "id": 27,
                    "version": 2,
                    "dataElement": [
                    "id": 27,
                    "version": 2,
                    "name": "ETHNIC CATEGORY UPDATED",
                    "description": "The ethnicity of a PERSON, as specified by the PERSON.)",
                    "definition": null,
                    "versionNumber": "0.1",
                    "status": "DRAFT",
                    "relations": [
                            [
                                "id": 28,
                                "name": "NHS ETHNIC CATEGORY",
                                "type": "DataValue",
                                "relationshipDirection": "ValueDomainFor",
                                "class": "uk.co.mdc.catalogue.ValueDomain"
                            ],
                            [
                                "id": 19,
                                "name": "CORE - REFERRALS",
                                "type": "ModelElement",
                                "relationshipDirection": "ModelContains",
                                "class": "uk.co.mdc.catalogue.Model"
                            ]
                    ],
                    "class": "DataElement"
                ]
        ]


    }

    def cleanup() {
        SCH.clearContext()
    }

    def "create new data element as admin"() {
        when: "I pass the data element map(JSON) above to the create method on the data element service"
        DataElement newDataElement = SpringSecurityUtils.doWithAuth('admin') { dataElementService.create(dataElementMapCreate)}
        then: "a data element is created with the same name"
        assert newDataElement.name == "test dataElement1"
        assert newDataElement.relations().size()==1
    }

    def "get a data element and update it as admin"() {
        when: "I get the ethnic category data element using the data element service"
        DataElement dataElementInstance = SpringSecurityUtils.doWithAuth('admin') { dataElementService.get(27)}
        then: "the service returen the correct data element"
        assert dataElementInstance.name == "ETHNIC CATEGORY"
        when: "I update the data element using the update method in the service"
        dataElementInstance = SpringSecurityUtils.doWithAuth('admin') {dataElementService.update(dataElementInstance, dataElementMapUpdate)}
        then: "The data element is updated with the relevant info"
        assert dataElementInstance.name == "ETHNIC CATEGORY UPDATED"
    }



}
