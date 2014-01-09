
<!-- --------------------------------------------------------------- -->
<!-- Built using James Welch's Forms Builder ------------------------- -->
<!-- --------------------------------------------------------------- -->


<%@ page import="uk.co.mdc.forms.FormDesign" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_no-sidebar">
		<g:set var="entityName" value="${message(code: 'formDesign.label', default: 'FormDesign')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" FORM Builder - ${formDesignInstance?.name}" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-editable.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-timepicker.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'layout.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'custom.css')}" type="text/css">
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'formDesign']">
				<g:hiddenField name="id" value="${formDesignInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						   		<li class="active"><a class="brand" href="#"><i class="icon-edit"></i> FormRenderer</a></li>
						   		<li><g:link action="show" id="${formDesignInstance?.id}"><g:message code="default.button.show.label" default="Edit" /></g:link></li>
						  		<li><g:link action="create" id="${formDesignInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${formDesignInstance?.id}')">Delete</a></li>
							  </ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
		<div id="container">
			<div class="ui-layout-center" id="center-panel">
			
			<div id="model-panel" class="ui-layout-center large-rounded">
					<div class="large-rounded form-item form">
						<div id="formdiv" class="col-lg-11 panel panel-primary ui-layout-center"></div>
					</div>
			</div>
			
			
			
			
			<div id="properties-panel" class="ui-layout-west large-rounded">
				<div class="panel-title">
					<i class="icon-list-ul"></i> &nbsp;&nbsp;Navigation
				</div>
					<div id="sections" class="col-lg-3 oneHundred">
							<div id="dataelementselection" class="panel panel-primary">
								<div id="sectionsdiv">
									<ul>
									</ul>
								</div>
								
							</div>
					</div>
			</div>
			
	</div>
		</div>
	</div>
			<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<g:javascript disposition="defer" library="formsRenderer"/>	
	<r:script disposition="defer">
		<g:if test="${formDesignInstance?.id}">
			formInstanceId = ${formDesignInstance.id}
		</g:if>
		<g:else>
			formInstanceId = ''
		</g:else>
		
		renderForm(formInstanceId)
	
	</r:script>
		
	</body>
</html>
