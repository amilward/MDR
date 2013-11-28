package uk.co.mdc.modules;

import geb.Browser
import geb.Page
import geb.Module

class TopNavElements extends Module{
	
	static content = {
		homeLink  { 	$("li#projectHomeLink a:first", text: "MDC") }
		modelLink  { 	$("li#nav-model-link a:first", text: "Data model") }
		pathwayLink { 	$("li#nav-pathway-link a:first", text: "Pathways") }
		
		// Pathways menu
		expandPathwayMenuLink { $("a#nav-pathway-expand") }
		createPathwayLink { $("a#openModalLink") }
		
		formLink  { 	$("li#nav-form-link a:first", text: "Form design") }
		
		// Pathway creation modal
		pathwayCreationModal {  $("div#createPathwayModal") } 
		newPathwayName 	{ pathwayCreationModal.find('#txt-name') }
		newPathwayDescription 	{ pathwayCreationModal.find('#txt-desc') }
		newPathwayVersion 	{ pathwayCreationModal.find('#txt-version') }
		newPathwayDraft 	{ pathwayCreationModal.find('#bool-isDraft') }
		newPathwaySubmit	{ pathwayCreationModal.find('#submitModalLink') }
	}
}