/**
 * Author: Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
 * 		   Adam Milward (adam.milward@outlook.com)
 */

package uk.co.mdc.pages.pathways;

import uk.co.mdc.pages.BasePageWithNav;

class PathwayShowPage extends BasePageWithNav{
	
	static url = "/pathway/show/*"
	
	static at = {
		url == "/pathway/show/*" &&
		title == "Pathway Editor"
	}
	
	
	
	static content = {
		pathwayName  { 	$("#pathwayName") }
		addNodeButton { $("#addNode") }
		node2(required:false) { $("#node7") }
		addFormModal { $("#AddFormModal") }
		addFormButton { $("h5", text: "Forms").find("i") }
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

		deleteSelectedElementButton {$("#deleteSelectedElement")}
		propertiesName {js.exec("return document.getElementById('txt-properties-name').value")}
		modalLabel { $("#createNodeModalLabel") }
		createNodeName { $("#createNodeName") }
		createNodeDescription { $("#createNodeDescription") }
		createNodeButton { $("#createNodeButton") }
		newNodeTitleDiv { pathwayCanvas.find("div", text: "testNode")}

        editModal { module PathwayEditModal }
	}
	
}