package uk.co.mdc.pages

import geb.Browser
import geb.Page
import geb.Module
import uk.co.mdc.modules.TopNavElements

class DashboardPage extends BasePageWithNav{
	
	static url = "/"
	
	static at = {
		url == "/" &&
		title == "Model Catalogue - Home"
	}
	
	static content = {
		nav { module TopNavElements }
		dashboardOptions { $("#dashboard-options")}
		pathwaysLink { $("#pathways") }
		dashboardPathways { $("#dashboard-pathways") }
		createPathwaysLink { $("#dashCreatePathway") }
        flibblewidget { $("#flibbleWidget5") }
	}
}