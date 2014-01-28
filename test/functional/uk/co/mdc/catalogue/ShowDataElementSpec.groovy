/**
 * Author: Adam Milward (adam.milward@outlook.com)
 */
package uk.co.mdc.catalogue

import geb.spock.GebReportingSpec
import org.openqa.selenium.Dimension
import uk.co.mdc.pages.catalogue.DataElementShowPage

class ShowDataElementSpec extends GebReportingSpec {
	def "View a data element with a given Id and edit the name of the element"() {
		
			given:"I am on the show data element view with id 25 in a 1024x768 browser window"
					driver.manage().window().setSize(new Dimension(1028, 768))
					to DataElementShowPage, 25
                    waitFor{
                        loginBox.displayed
                    }
					username = "admin"
					password = "admin123"

                    when: "I click the pathways dropdown menu"
                    signIn.click()

                    then: "The data element information is visible"
                    waitFor{
                        dataElementName.text()=="SOURCE OF REFERRAL FOR OUT-PATIENTS"
                        dataElementDescription.text()=="This identifies the source of referral of each Consultant Out-Patient Episode."
                        dataElementStatus.text()=="DRAFT"
                        dataElementVersion.text()=="0.1"
                    }

                    when: "I click on the data element name"
                        dataElementName.click()

                    then: "the editable input panel is displayed"
                    waitFor{
                        editableControls.displayed
                    }

                    when: "I change the name of the data element and save"
                    editableInput = "Test name"
                    editableSubmit.click()

                    then: "the data element name is changed and the version is incremented and nothing else"
                    waitFor{
                        dataElementName.text()=="Test name"
                        dataElementDescription.text()=="This identifies the source of referral of each Consultant Out-Patient Episode."
                        dataElementStatus.text()=="DRAFT"
                        dataElementVersion.text()=="0.2"
                    }

                    when: "I click on Add Relationship"
                    toggleAddRelationshipButton.click()

                    then: "the add relationship panel is displayed"
                    waitFor{
                        addRelationshipPanel.displayed
                    }

                    when: "I click on the data element relationshipType"
                    relationshipType.click()

                    then: "the editable input panel is displayed"
                    waitFor{
                        editableControls.displayed
                    }

                    when: "I change the name of the data element and save"
                    editableSelect = "ParentChild"
                    editableSubmit.click()

                    then: "the data element relationship type"
                    waitFor{
                        relationshipType.text()=="ParentChild"
                    }

                    when: "I click on the data element relationship direction"
                    relationshipDirection.click()

                    then: "the editable input panel is displayed"
                    waitFor{
                        editableControls.displayed
                    }

                    when: "I change the name of the data element and save"
                    editableSelect = "ParentOf"
                    editableSubmit.click()

                    then: "the data element relationship direction is changed"
                    waitFor{
                        relationshipDirection.text()=="ParentOf"
                    }

                    when: "I click on the data element relationship object"
                    relation.click()

                    then: "the editable input panel is displayed"
                    waitFor{
                        editableControls.displayed
                    }

                    when: "I change the name of the data element and save"
                    editableSelect = "ETHNIC CATEGORY"
                    editableSubmit.click()

                    then: "the data element relationship direction is changed"
                    waitFor{
                        relation.text()=="ETHNIC CATEGORY"
                    }

                    when: "I click on the add relationship button"
                    addRelationshipButton.click()

                    then: "the relations table is updated with the relevant data"
                    waitFor{
                        relationsTableType.displayed
                        relationsTableDirection.displayed
                        relationsTableName.displayed
                        relationsTableClass.displayed
                    }


		}
	
}