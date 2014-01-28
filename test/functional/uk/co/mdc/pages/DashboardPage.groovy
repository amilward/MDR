package uk.co.mdc.pages

import geb.Browser
import geb.Page
import geb.Module
import uk.co.mdc.modules.TopNavElements

class DashboardPage extends BasePageWithNav{
	
	static url = "/dashboard/"
	
	static at = {
		url == "/dashboard/" &&
		title == "Model Catalogue - Dashboard"
	}
	
	static content = {
		nav { module TopNavElements }
		dashboardOptions { $("#dashboard-options")}

		pathwaysLink { $("#pathways") }
        formScreenLink{ $("#forms")}

        dashboardForms { $("#dashboard-forms") }
        formsTable { dashboardForms.find("table",0)}

        dashboardPathways { $("#dashboard-pathways") }
		createPathwaysLink { $("#dashCreatePathway") }
        pathwaysTable { dashboardPathways.find("table",0)}
	}

    def getPathwayLink(pathwayName){
        return pathwaysTable.find("a", text: pathwayName)
    }

    def goToPathwaysScreen(){
        pathwaysLink.click()
        waitFor{
            pathwaysTable.displayed
        }
    }

    def getPathwaysLinks(){
        return pathwaysTable.find("a")
    }

    def goToFormsScreen(){
        formScreenLink.click()
        waitFor{
            formsTable.displayed
        }
    }

    def getFormsLinks(){
        return formsTable.find("a")
    }
}