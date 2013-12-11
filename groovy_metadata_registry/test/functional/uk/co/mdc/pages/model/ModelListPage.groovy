package uk.co.mdc.pages.model;

import geb.Browser
import geb.Page
import geb.Module

class ModelListPage extends Page{
	
	static url = "/valueDomain/list"
	
	static at = {
		url == "/valueDomain/list" &&
		title == "ValueDomain List"
	}
	
	static content = {
		
	}
}