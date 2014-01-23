package uk.co.mdc.pages.forms;

import geb.Browser
import geb.Page
import geb.Module

class FormListPage extends Page{
	
	static url = "/formDesign/list"
	
	static at = {
		url == "/formDesign/list" &&
		title == "FormDesign List"
	}
	
	static content = {
		
	}
}