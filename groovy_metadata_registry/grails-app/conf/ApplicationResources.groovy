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
		resource url: "js/datatable/jquery.dataTables.min.js"
		resource url: "js/datatable/jquery.dataTables.fnSetFilteringDelay.js"
	}
	
	dualListBox {
		resource url: "js/lib/jquery.bootstrap-duallistbox.js"
	}
	
	formsBuilder{
		dependsOn "application"
		resource url: "js/jquery.layout-1.3.0.min.js"
		resource url: "js/bootstrap-editable.js"
		resource url: "js/knockout-2.2.1.js"
		resource url: "js/knockout-sortable.js"
		resource url: "js/knockout-x-editable.js"
		resource url: "js/viewModel.js"
		resource url: "js/custom.js"
		
	}
	
	formsRenderer{
		
		dependsOn "application"
		resource url: "js/bootstrap-editable.js"
		resource url: "js/form_model.js"
		resource url: "js/constraint.js"
		resource url: "js/frenderer-theme-bootstrap.js"
		resource url: "js/frenderer.js"
	}
	
	pathways{
		dependsOn "application"
		resource url: "js/bootstrap-editable.js"
		resource url: "js/jquery.jsPlumb-1.5.2-min.js"
		resource url: "js/pathway_model.js"
		resource url: "js/pathway.js"
	}

	dForms{
		dependsOn "application"
		resource url: "js/jquery.dform-1.1.0.min.js"
		resource url: "js/formDesign.js"
		}

}

