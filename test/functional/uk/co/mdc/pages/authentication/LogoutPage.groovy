package uk.co.mdc.pages.authentication;

import geb.Browser
import geb.Page
import geb.Module

class LogoutPage extends Page{
	
	static url = "logout/index"
	
	static at = {
		url == "logout/index" &&
		title == "Model Catalogue - Home"
	}
	
}