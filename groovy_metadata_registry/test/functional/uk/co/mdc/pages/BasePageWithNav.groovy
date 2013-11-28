package uk.co.mdc.pages;

import geb.Browser
import geb.Page
import geb.Module
import uk.co.mdc.modules.TopNavElements

class BasePageWithNav extends Page{
	
	static content = {
		nav { module TopNavElements }
	}
}