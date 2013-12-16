/**
 * Author: Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
 * 		   Adam Milward (adam.milward@outlook.com)
 */

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
		editInfoButton { $("#editPathwayInfo") }
		updatePathwayModal { $("#updatePathwayModal") }
		createPathwayForm { $("form#createPathwayForm") }
		pathwayInfoName { js.exec("return document.getElementById('txt-nameUpdate').value")}
		pathwayInfoDescription { js.exec("return document.getElementById('txt-descUpdate').value")}
		pathwayInfoVersionNo { js.exec("return document.getElementById('txt-versionNoUpdate').value")}
		pathwayInfoIsDraft  { js.exec("return document.getElementById('select-isDraftUpdate').value")}
	}
	
}