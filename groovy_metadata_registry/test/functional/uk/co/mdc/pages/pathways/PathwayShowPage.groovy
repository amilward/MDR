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
		node2 { $("#node2") }
		addFormModal { $("#AddFormModal") }
		addFormButton { $("#addFormToNode") }
		formDesignTableFirstRow { $("#formDesignTable tbody tr", 0) }
		formDesignTableRows { $("#formDesignTable tbody tr") }
		formDesignTableFRLink { formDesignTableFirstRow.find("a") } 
		formDesignCartListFirstItem { $("#formCartList li") }
	}
	
}