modules = {
	
	jquery_lib {
		resource url: "js/vendor/jquery-1.10.1.min.js"
		resource url: "js/vendor/jquery-ui.1.10.2.js"
	}
	
    application {
		dependsOn "jquery_lib"
		resource url: "js/vendor/bootstrap.min.js"
		resource url: "js/main.js"
    }
	
	dataTables {
		dependsOn "application"
		resource url: "js/datatable/jquery.dataTables.min.js"
		resource url: "js/datatable/jquery.dataTables.fnSetFilteringDelay.js"
	}
	
	dualListBox {
		resource url: "js/lib/jquery.bootstrap-duallistbox.js"
	}
	
	dataElement{
		dependsOn "dataTables"
		resource url: "js/model/dataElement/dataElement.js"
	}
	
	valueDomain{
		dependsOn "dataTables"
		resource url: "js/model/valueDomain/valueDomain.js"
	}
	
	dataType{
		dependsOn "dataTables"
		resource url: "js/model/dataType/dataType.js"
	}
	
	dataElementConcept{
		dependsOn "dataTables"
		resource url: "js/model/dataElementConcept/dataElementConcept.js"
	}
	
	formsBuilder{
		dependsOn "application"
		resource url: "js/vendor/jquery.layout-1.3.0.min.js"
		resource url: "js/vendor/bootstrap-editable.js"
		resource url: "js/vendor/knockout-2.2.1.js"
		resource url: "js/vendor/knockout-sortable.js"
		resource url: "js/vendor/knockout-x-editable.js"
		resource url: "js/forms/viewModel.js"
		resource url: "js/forms/fDesignerLayout.js"
		
	}
	
	formsRenderer{
		
		dependsOn "application"
		resource url: "js/vendor/jquery.layout-1.3.0.min.js"
		resource url: "js/vendor/bootstrap-editable.js"
		resource url: "js/lib/bootstrap-datepicker.js"
		resource url: "js/lib/bootstrap-timepicker.js"
		resource url: "js/pathways/jquery.jsPlumb-1.5.2-min.js"
		resource url: "js/respond.min.js"
		resource url: "js/forms/form_model.js"
		resource url: "js/forms/constraint.js"
		resource url: "js/forms/frenderer-theme-bootstrap.js"
		resource url: "js/forms/frenderer.js"
		resource url: "js/forms/frendererLayout.js"
		
	}
	
	pathways{
		dependsOn "application"
		resource url: "js/vendor/jquery.layout-1.3.0.min.js"
		resource url: "js/vendor/bootstrap-editable.js"
		resource url: "js/pathways/jquery.jsPlumb-1.5.2-min.js"
		resource url: "js/pathways/pathway_model.js"
		resource url: "js/pathways/pathway.js"
		resource url: "js/pathways/pathwaysLayout.js"
		resource url: "js/pathways/pathwayAjaxFunctions.js"
		
	}
	
	pathwaysList{
		dependsOn "dataTables"
		resource url: "js/pathways/pathwaysList.js"
	}

	dForms{
		dependsOn "application"
		resource url: "js/vendor/jquery.dform-1.1.0.min.js"
		resource url: "js/forms/formDesign.js"
		}

}

