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
        dataElementVersion(wait:true)  { $("#dataElementVersion") }
        username { $("#username") }
        password { $("#password") }
        signIn { $("#signIn") }
        loginBox { $("#signIn") }
        editableControls { $("#dataElementShow").find("span", class:"editable-controls") }
        editableInput { editableControls.find("input", class:"editable-input") }
        editableSubmit { editableControls.find("button", type:"submit")}
    }

}