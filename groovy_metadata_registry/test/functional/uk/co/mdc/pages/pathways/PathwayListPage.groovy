package uk.co.mdc.pages.pathways;

import uk.co.mdc.pages.BasePageWithNav;

class PathwayListPage extends BasePageWithNav{
	
	static url = "/pathwaysModel/list"
	
	static at = {
		url == "/pathwaysModel/list" &&
		title == "List Pathways Models"
	}
	
	
	
	static content = {
		dataTableRows  { 	$("#documentTable tbody tr") }
		searchBox  { 	$("#documentTable tbody tr") }
	}
}