package uk.co.mdc.authentication.pages;

import geb.Browser
import geb.Page
import geb.Module

class DashboardPage extends Page{
	
	static url = "/"
	
	static at = {
		url == "/" &&
		title == "Welcome to the MDC Metadata Registry"
	}
	
	static content = {
		
	}
}