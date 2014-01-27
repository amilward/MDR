package uk.co.mdc.modules;

import geb.Browser
import geb.Page
import geb.Module

class TopNavElements extends Module{
	
	static content = {
		homeLink  { 	$("li#projectHomeLink").find("a:first", text: "MDC") }
		modelLink  { 	$("li#nav-model-link").find("a:first", text: "Data model") }
		pathwayLink { 	$("li#nav-pathway-link").find("a:first", text: "Pathways") }
		
		// Pathways menu
		expandPathwayMenuLink { $("a#nav-pathway-expand") }
		listPathwaysLink(wait: true) { $("a#listPathwaysLink") }
		createPathwayLink { $("a#createPathwayLink") }
		
		formLink  { 	$("li#nav-form-link a:first", text: "Form design") }
		
		// Pathway creation modal
		pathwayCreationModal {  $("div#createPathwayModal") } 
		newPathwayName 	{ pathwayCreationModal.find('#txt-name') }
		newPathwayDescription 	{ pathwayCreationModal.find('#txt-desc') }
		newPathwayVersionNo 	{ pathwayCreationModal.find('#txt-version') }
		newPathwayIsDraft 	{ pathwayCreationModal.find('#bool-isDraft') }
		newPathwaySubmit	{ pathwayCreationModal.find('#createPathwaySubmit') }
		
		
		navPresentAndVisible(required:false) {
			$("nav", class: "navbar")
		}
	}

    /**
     * Go to the list page for pathways
     */
    void goToPathwayListPage(){
        expandPathwayMenuLink.click()
        listPathwaysLink.click()
    }
}