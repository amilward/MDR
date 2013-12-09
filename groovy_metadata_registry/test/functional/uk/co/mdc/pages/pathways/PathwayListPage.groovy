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
		dataTableFirstRow  { $("#documentTable tbody tr:first") }
		dataTableFirstRowName {dataTableFirstRow.find("a")}
		searchBox  { 	$("#documentTable_filter input") }
	}
}