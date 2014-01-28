/**
 * Author: Adam Milward (adam.milward@outlook.com)
 */
package uk.co.mdc.catalogue

import geb.spock.GebReportingSpec
import org.openqa.selenium.Dimension
import uk.co.mdc.pages.catalogue.DataElementCreatePage
import uk.co.mdc.pages.catalogue.DataElementShowPage

class CreateDataElementSpec extends GebReportingSpec {
	def "Create a data element and add a relationship"() {
		
			given:"I am on the create data element view in a 1024x768 browser window"
					driver.manage().window().setSize(new Dimension(1028, 768))
					to DataElementCreatePage
                    waitFor{
                        loginBox.displayed
                    }
					username = "admin"
					password = "admin123"

                    when: "I click the pathways dropdown menu"
                    signIn.click()

                    then: "The data element information is visible"
                    waitFor{
                        dataElementName.text()=="empty"
                        dataElementDescription.text()=="empty"
                        dataElementStatus.text()=="DRAFT"
                    }

                    when: "I click on the data element name"
                        dataElementName.click()

                    then: "the editable input panel is displayed"
                    waitFor{
                        editableControls.displayed
                    }

                    when: "I change the name of the data element and save"
                    editableInput = "Test create name"
                    editableSubmit.click()

                    then: "the data element name is changed"
                    waitFor{
                        dataElementName.text()=="Test create name"
                    }

                    when: "I click on the data element description"
                    dataElementDescription.click()

                    then: "the editable input panel is displayed"
                    waitFor{
                        editableControls.displayed
                    }

                    when: "I change the description of the data element and save"
                    editableTextarea = "Test create description"
                    editableSubmit.click()

                    then: "the data element description is changed"
                    waitFor{
                        dataElementDescription.text()=="Test create description"
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

                    when: "I click on the add data element button"
                    createDataElementButton.click()

                    then: "the data element is created and I am forwarded to the show page"
                    waitFor{
                        at DataElementShowPage
                        dataElementName.text()=="Test create name"
                        dataElementDescription.text()=="Test create description"
                        dataElementStatus.text()=="DRAFT"
                        dataElementVersion.text()=="0.1"
                    }




		}
	
}