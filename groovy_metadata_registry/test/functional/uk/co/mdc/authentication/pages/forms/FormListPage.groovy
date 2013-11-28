package uk.co.mdc.authentication.pages.forms;

import geb.Browser
import geb.Page
import geb.Module

class PathwayListPage extends Page{
	
	static url = "/formDesign/list"
	
	static at = {
		url == "/formDesign/list" &&
		title == "FormDesign List"
	}
	
	static content = {
		
	}
}