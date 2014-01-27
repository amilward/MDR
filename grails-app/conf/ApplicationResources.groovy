modules = {


	// Standard libraries
	jquery_lib {
		resource url: "bower_components/jquery/jquery.min.js", disposition: "head"
		resource url: "js/vendor/jquery/jquery-ui.1.10.2.js"
	}
	
	jquery_layout_lib{
		dependsOn "jquery_lib"
		resource url: "js/vendor/jquery/jquery.layout-1.3.0.min.js"
	}
	
	jquery_dform_lib{
		dependsOn "jquery_lib"
		resource url: "js/vendor/jquery/jquery.dform-1.1.0.min.js"
	}
	
	knockout_lib{
		resource url: "js/vendor/knockout/knockout-3.0.0.js"
		resource url: "js/vendor/knockout/knockout-es5.js"
		resource url: "js/vendor/knockout/knockout.punches.js"
	}
	
	require_lib{
		resource url: "js/vendor/require/require.js"
	}
	
	d3_lib{
		resource url: "js/vendor/d3/d3.js"
	}
	
	modernizr_lib{
		resource url: "js/vendor/modernizr/modernizr-2.6.2-respond-1.1.0.min.js"
	}
	
	jsplumb_lib{
		resource url: "js/vendor/jsplumb/jquery.jsPlumb-1.5.4-min.js"
	}
	
	bootstrap_lib{
        resource url: "bower_components/bootstrap/dist/js/bootstrap.min.js"
	}
	
	bootstrap_editable_lib{
		dependsOn "bootstrap_lib"
		resource url: "js/vendor/bootstrap-editable.js"
	}

	dataTables {
		dependsOn "application"
        resource url: "bower_components/datatables/media/js/jquery.dataTables.js"
        resource url: "bower_components/datatables/media/css/jquery.dataTables.css"
        resource url: "js/datatable/jquery.dataTables.fnSetFilteringDelay.js"
	}
	
	dualListBox {
		resource url: "css/bootstrap-duallistbox.css"
		resource url: "js/lib/jquery.bootstrap-duallistbox.js"
	}
	
	dataElement{
		dependsOn "dataTables"
		resource url: "js/model/dataElement/dataElement.js"
	}

    relationshipType{
        dependsOn "dataTables"
        resource url: "js/model/relationshipType/relationshipType.js"
    }
	
	conceptualDomain{
		dependsOn "dataTables"
		dependsOn "dualListBox"
		resource url: "js/model/conceptualDomain/conceptualDomain.js"
	}
	
	model{
		dependsOn "dataTables"
		dependsOn "dualListBox"
		resource url: "js/model/model/model.js"
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
	
	formDesignList{
		dependsOn "dataTables"
		resource url: "js/forms/formDesign.js"
	}
	
	formsBuilder{
		dependsOn "application"
		dependsOn "jquery_layout_lib"
		dependsOn "bootstrap_editable_lib"
		resource url: "js/vendor/knockout-2.2.1.js"
		resource url: "js/vendor/knockout-sortable.js"
		resource url: "js/vendor/knockout-x-editable.js"
		resource url: "js/forms/formsDesigner/dataTypeTemplates.js"
		resource url: "js/forms/formsDesigner/palette.js"
		resource url: "js/forms/formsDesigner/model/Form.js"
		resource url: "js/forms/formsDesigner/AppViewModel.js"
		resource url: "js/forms/formsDesigner/service/FormDesignService.js"
		resource url: "js/forms/formsDesigner/model/Component.js"
		resource url: "js/forms/formsDesigner/model/Property.js"
		resource url: "js/forms/formsDesigner/model/Question.js"
		resource url: "js/forms/formsDesigner/model/Component.js"
		resource url: "js/forms/formsDesigner/model/Section.js"
		resource url: "js/forms/formsDesigner/model/SelectOption.js"
		resource url: "js/forms/formsDesigner/model/DataTypeInstance.js"
		resource url: "js/forms/formsDesigner/show.js"
		resource url: "js/forms/formsDesigner/fDesignerLayout.js"
		
	}
	
	formsRenderer{
		
		dependsOn "application"
		dependsOn "jquery_layout_lib"
		dependsOn "bootstrap_editable_lib"
		resource url: "js/lib/bootstrap-datepicker.js"
		resource url: "js/lib/bootstrap-timepicker.js"
		resource url: "js/respond.min.js"
		resource url: "js/forms/formsRenderer/constraint.js"
		resource url: "js/forms/formsRenderer/frenderer-theme-bootstrap.js"
		resource url: "js/forms/formsRenderer/frenderer.js"
		resource url: "js/forms/formsRenderer/frendererLayout.js"
		
	}
	
	pathways{
		dependsOn "application"
		dependsOn "jsplumb_lib"
		dependsOn "knockout_lib"
		dependsOn "jquery_layout_lib"
		dependsOn "bootstrap_editable_lib"
		dependsOn "dataTables"
		//resource url: "js/pathways/pathway_model.js"
		//resource url: "js/pathways/pathway.js"
		resource url: "js/pathways/pathwaysLayout.js"
		resource url: "js/pathways/AppViewModel.js"
		resource url: "js/pathways/show.js"
		resource url: "js/pathways/service/FormService.js"
		resource url: "js/pathways/service/PathwayService.js"
		resource url: "js/pathways/binding/knockout.jsplumb.js"
		//resource url: "js/pathways/catalogue/modelModel.js"
		resource url: "js/pathways/model/NodeModel.js"
		resource url: "js/pathways/model/PathwayModel.js"
		resource url: "js/pathways/model/LinkModel.js"
		resource url: "js/pathways/model/FormModel.js"
		resource url: "js/forms/formDesign.js"
		resource url: "js/model/model/model.js"

        resource url: "css/pathways/style.css"
		
	}
	
	pathwaysList{
		dependsOn "dataTables"
		dependsOn "knockout_lib"
		resource url: "js/pathways/pathwaysList.js"
	}

	dForms{
		dependsOn "application"
		dependsOn "jquery_dform_lib"
		resource url: "js/forms/formDesign.js"
		}

    dashboard{
        dependsOn "application"
        resource url: "js/dashboard.js"
    }


    /**
     * Angular support
     */
    angular {
        // we will use jQuery anyway so let's depend on it directly to assure that it's loaded
        // before angular and it won't fallback to jqLite
        resource id: 'js', url: [dir: 'bower_components/angular', file: "angular.js"], nominify: true
    }

    "angular-animate" {
        dependsOn "angular"
        resource id: 'js', url: [dir: 'bower_components/angular-animate', file: "angular-animate.js"], nominify: true
    }

    "angular-cookies" {
        dependsOn "angular"
        resource id: 'js', url: [dir: 'bower_components/angular-cookies', file: "angular-cookies.js"], nominify: true
    }

    "angular-loader" {
        dependsOn "angular"
        resource id: 'js', url: [dir: 'bower_components/angular-loader', file: "angular-loader.js"], nominify: true
    }

    "angular-resource" {
        dependsOn "angular"
        resource id: 'js', url: [dir: 'bower_components/angular-resource', file: "angular-resource.js"], nominify: true
    }

    "angular-route" {
        dependsOn "angular"
        resource id: 'js', url: [dir: 'bower_components/angular-route', file: "angular-route.js"], nominify: true
    }

    "angular-sanitize" {
        dependsOn "angular"
        resource id: 'js', url: [dir: 'bower_components/angular-sanitize', file: "angular-sanitize.js"], nominify: true
    }

    "angular-xeditable" {
        dependsOn "angular"
        resource id: 'js', url: [dir: 'bower_components/angular-xeditable/dist/js', file: "xeditable.js"], nominify: true
        resource url:'bower_components/angular-xeditable/dist/css/xeditable.css'
    }

    "angular-touch" {
        dependsOn "angular"
        resource id: 'js', url: [dir: 'bower_components/angular-touch', file: "angular-touch.js"], nominify: true
    }

    angularPathwaysApp {
        dependsOn 'angular'
        //resource id: 'js', url: [dir: 'js/angular/', file: "angular-app.js"], nominify: true
    }

    "ng-table" {
        dependsOn "angular"
        resource id: 'js', url: [dir: 'bower_components/ng-table', file: "ng-table.0.3.2.js"], nominify: true
    }

    /**
     *  Application libraries
     */
    application_libraries {
        dependsOn "jquery_lib"
        dependsOn "bootstrap_lib"
        dependsOn "modernizr_lib"
    }

    application {
        dependsOn "application_libraries"
        resource url: "js/main.js"

        // Keep this at the end, otherwise we'll override our classes with vendor defaults :(
        resource url:'css/style.css'
        resource url:[dir: 'less', file: 'application.less'], attrs:[rel: "stylesheet/less", type:'css']
        resource url: "js/vendor/less/less-1.6.0.min.js", disposition: 'head'
    }

    'angular-bootstrap' {
        dependsOn "angular"
        dependsOn "bootstrap-css"
        dependsOn "jquery"

        resource url: "bower_components/angular-bootstrap/ui-bootstrap.js"
        resource url: "bower_components/angular-bootstrap/ui-bootstrap-tpls.js"
    }

    'angular-security' {
        dependsOn "angular-bootstrap"

        // based on http://www.packtpub.com/angularjs-web-application-development/book

        resource url: "js/appng/security/authorization.js"
        resource url: "js/appng/security/index.js"
        resource url: "js/appng/security/interceptor.js"
        resource url: "js/appng/security/retryQueue.js"
        resource url: "js/appng/security/security.js"

        resource url: "js/appng/security/login/LoginFormController.js"
        resource url: "js/appng/security/login/login.js"

    }

    appng {
        dependsOn "angular-security"
        dependsOn "angular-route"
        dependsOn "angular-resource"
        dependsOn "angular-xeditable"
        dependsOn "ng-table"
        resource url: "js/appng/app.js"
        resource url: "js/appng/controllers.js"
        resource url: "js/appng/services.js"
        resource url: "js/appng/toolbar.js"
    }
}

