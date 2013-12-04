package uk.co.mdc.authentication.pages.pathways;

import geb.Browser
import geb.Page
import geb.Module

class PathwayListPage extends Page{
	
	static url = "/pathwaysModel/list"
	
	static at = {
		url == "/pathwaysModel/list" &&
		title == "PathwaysModel List"
	}
	
	static content = {
		
	}
}