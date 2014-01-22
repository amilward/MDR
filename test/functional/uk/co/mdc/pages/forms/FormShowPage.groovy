package uk.co.mdc.pages.forms

import geb.Page

class FormShowPage extends Page{
	
	static url = "/formDesign/show"
	
	static at = {
		url == "/formDesign/show" &&
		title == "Show FormDesign"
	}
	
	static content = {
		
	}
}