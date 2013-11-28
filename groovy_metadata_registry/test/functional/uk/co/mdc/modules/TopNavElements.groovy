package uk.co.mdc.modules;

import geb.Browser
import geb.Page
import geb.Module

class TopNavElements extends Module{
	
	static content = {
		homeLink  { 	$("li#projectHomeLink a:first", text: "MDC") }
		modelLink  { 	$("li#nav-model-link a:first", text: "Data model") }
		pathwayLink { 	$("li#nav-pathway-link a:first", text: "Pathways") }
		formLink  { 	$("li#nav-form-link a:first", text: "Form design") }
		
	}
}