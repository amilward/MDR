package uk.co.mdc.spec.pathways;


import geb.spock.GebReportingSpec
import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.authentication.LogoutPage
import uk.co.mdc.pages.DashboardPage
import uk.co.mdc.pages.pathways.PathwayListPage
import org.openqa.selenium.Dimension

class TimeoutNotificationSpec extends GebReportingSpec {
	
	def "Log in access the pathways menu and then logout"() {
		
			given:"I am on the dashboard and I have a notification that the session has expired"
					driver.manage().window().setSize(new Dimension(1028, 768))
					to LoginPage
					username = "admin"
					password = "admin123"
					submitButton.click(DashboardPage)
			
					waitFor{
						at DashboardPage
					}
			
					when: "I click the pathways dropdown menu"
					nav.expandPathwayMenuLink.click()
		
			
					then: "The list pathways link is visible"
					waitFor{
						nav.listPathwaysLink.displayed
					}
					
					when: "simulate timeout with log out from server and continue doing stuff"
						js.exec("jQuery.ajax('../logout/index')")
						nav.expandPathwayMenuLink.click()
					then: "Screen is returned to login screen after simulated timeout"
						waitFor{
							at DashboardPage
						}
					  
					
						
					
							
						
					
					
				
					
			
		}
	

}