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
		title == "Show Pathway"
	}
	
	
	
	static content = {
		pathwayName  { 	$("h1#pathwayName") }
		node2(required:false) { $("#node7") }
		addFormModal { $("#AddFormModal") }
		addFormButton { $("#addFormToNode") }
		formDesignTableFirstRow { $("#formDesignTable tbody tr", 0) }
		formDesignTableRows { $("#formDesignTable tbody tr") }
		formDesignTableFRLink { formDesignTableFirstRow.find("a") }
		formDesignCartListFirstItem { $("#formCartList li") }
        editInfoButton { $("#editPathwayInfo") }
		viewSubPathwayButton { $("#viewSubPathway") }
		addSubPathwayButton { $("#addSubPathway") }
		updatePathwayModal { $("#updatePathwayModal") }
		createPathwayForm { $("form#createPathwayForm") }
		pathwayInfoName { js.exec("return document.getElementById('txt-nameUpdate').value")}
		pathwayInfoDescription { js.exec("return document.getElementById('txt-descUpdate').value")}
		pathwayInfoVersionNo { js.exec("return document.getElementById('txt-versionNoUpdate').value")}
		pathwayInfoIsDraft  { js.exec("return document.getElementById('select-isDraftUpdate').value")}
		pathwayCanvas { $(".jsplumb-container") }
		checkBox { $("#cb8") } 
		treeLevel1 { $("ul.level1 li") }
		treeLevel2 { $("ul.level2 li") }
		guardPatientNode { treeLevel2.find("span", text: "Guard Patient") }
		deleteSelectedElementButton {$("#deleteSelectedElement")}
		propertiesName {js.exec("return document.getElementById('txt-properties-name').value")}
		

        editModal { module PathwayEditModal }
	}
	
}