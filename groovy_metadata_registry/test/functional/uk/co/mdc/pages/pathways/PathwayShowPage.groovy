package uk.co.mdc.pages.pathways;

import uk.co.mdc.pages.BasePageWithNav;

class PathwayShowPage extends BasePageWithNav{
	
	static url = "/pathwaysModel/show/*" 
	
	static at = {
		url == "/pathwaysModel/show/*" &&
		title == "Create PathwaysModel"
	}
	
	
	
	static content = {
		pathwayName  { 	$("h1#pathwayName") }
	}
}