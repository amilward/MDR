package uk.co.mdc.pages.catalogue;

import uk.co.mdc.pages.BasePageWithNav;

class DataElementShowPage extends BasePageWithNav{
	
	static url = "ng#/dataElement/show"
	
	static at = {
		url == "ng#/dataElement/show" &&
		title == "Model Catalogue NG"
	}
	
	
	
	static content = {
        dataElementName(wait:true)  { $("#dataElementName") }
        dataElementDescription(wait:true)  { $("#dataElementDescription") }
        dataElementStatus(wait:true)  { $("#dataElementStatus") }
        dataElementVersion(wait:true)  { $("#dataElementVersion") }
        username { $("#username") }
        password { $("#password") }
        signIn { $("#signIn") }
        loginBox { $("#signIn") }
        editableControls { $("#dataElementShow").find("span", class:"editable-controls") }
        editableInput { editableControls.find("input", class:"editable-input") }
        editableSubmit { editableControls.find("button", type:"submit")}
        editableSelect { editableControls.find("select", class:"editable-input") }
        toggleAddRelationshipButton {$("#toggleAddRelationshipButton")}
        addRelationshipPanel {$("#addRelationshipPanel")}
        relationsTable {$("#relationsTable")}
        relationsTableType {relationsTable.find("td", text: "ParentChild")}
        relationsTableDirection {relationsTable.find("td", text: "ParentOf (this)")}
        relationsTableName {relationsTable.find("td", text: "ETHNIC CATEGORY")}
        relationsTableClass {relationsTable.find("td", text: "DataElement")}
        relationshipType(wait:true)  { $("#relationshipType") }
        relationshipDirection(wait:true)  { $("#relationshipDirection") }
        relation(wait:true)  { $("#relation") }
        addRelationshipButton { $("#addRelationship")}
    }

}