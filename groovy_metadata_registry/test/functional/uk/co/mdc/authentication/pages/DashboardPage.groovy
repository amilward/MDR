package uk.co.mdc.authentication.pages;

import geb.Browser
import geb.Page
import geb.Module
import uk.co.mdc.modules.TopNavElements

class DashboardPage extends Page{
	
	static url = "/"
	
	static at = {
		url == "/" &&
		title == "Model Catalogue - Home"
	}
	
	static content = {
		nav { module TopNavElements }
	}
}