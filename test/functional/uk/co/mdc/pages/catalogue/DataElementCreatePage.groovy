package uk.co.mdc.pages.catalogue;

import uk.co.mdc.pages.BasePageWithNav;

class DataElementCreatePage extends BasePageWithNav{
	
	static url = "ng#/dataElement/create"
	
	static at = {
		url == "ng#/dataElement/create" &&
		title == "Model Catalogue NG"
	}
	
	
	
	static content = {
        dataElementName(wait:true)  { $("#dataElementName") }
        dataElementDescription(wait:true)  { $("#dataElementDescription") }
        dataElementStatus(wait:true)  { $("#dataElementStatus") }
        relationshipType(wait:true)  { $("#relationshipType") }
        relationshipDirection(wait:true)  { $("#relationshipDirection") }
        relation(wait:true)  { $("#relation") }
        username { $("#username") }
        password { $("#password") }
        signIn { $("#signIn") }
        loginBox { $("#signIn") }
        editableControls { $("#dataElementCreate").find("span", class:"editable-controls") }
        editableInput { editableControls.find("input", class:"editable-input") }
        editableTextarea { editableControls.find("textarea", class:"editable-input") }
        editableSelect { editableControls.find("select", class:"editable-input") }
        editableSubmit { editableControls.find("button", type:"submit")}
        addRelationshipButton { $("#addRelationship")}
        createDataElementButton { $("#createDataElementButton")}
        relationsTable {$("#relationsTable")}
        relationsTableType {relationsTable.find("td", text: "ParentChild")}
        relationsTableDirection {relationsTable.find("td", text: "ParentOf (this)")}
        relationsTableName {relationsTable.find("td", text: "ETHNIC CATEGORY")}
        relationsTableClass {relationsTable.find("td", text: "DataElement")}
        toggleAddRelationshipButton {$("#toggleAddRelationshipButton")}
        addRelationshipPanel {$("#addRelationshipPanel")}

    }

}