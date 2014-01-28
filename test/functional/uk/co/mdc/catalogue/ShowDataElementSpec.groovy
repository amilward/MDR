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
					to DataElementShowPage
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


//FIXME need to add tests for changing status

		}
	
}