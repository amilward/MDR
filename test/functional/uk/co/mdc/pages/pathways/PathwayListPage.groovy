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
		dataTableFirstRow  { $("#documentTable tbody tr", 0) }
		dataTableSecondRow  { $("#documentTable tbody tr", 1) }
		dataTableFirstRowLink { dataTableFirstRow.find("a") }
		dataTableSecondRowLink { dataTableSecondRow.find("a") }
		dataTableTMLink { dataTableRows.find("a", text: "Transplanting and Monitoring Pathway") }
		
		searchBox  { 	$("#documentTable_filter input") }
	}
}