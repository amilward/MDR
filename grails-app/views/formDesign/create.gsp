<!-- --------------------------------------------------------------- -->
<!-- --------------------------------------------------------------- -->
<!-- --------------------------------------------------------------- -->
<!-- Built using James Welch's Forms Builder ------------------------- -->
<!-- --------------------------------------------------------------- -->
<!-- --------------------------------------------------------------- -->
<!-- --------------------------------------------------------------- -->
<!-- --------------------------------------------------------------- -->

<%@ page import="uk.co.mdc.forms.FormDesign" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_no-sidebar">
		<g:set var="entityName" value="${message(code: 'formDesign.label', default: 'FormDesign')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" FORM Builder - Create Form" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-editable.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'layout.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'custom.css')}" type="text/css">
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'formDesign']">
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						   		<li class="active"><a class="brand" href="#"><i class="icon-edit"></i> FormDesigner</a></li>
						   		<li><a href="#" onclick="saveForm();">Save</a></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
		<div id="container">
			<g:render template="form"/>
		</div>		
	</div>
	
	<!-- Update Form Info -->
    <div id="EditFormModal" class="modal fade hide" tabindex="-1"	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop = "true" >
	<div class="modal-dialog">
		<div class="modal-content" data-bind="">
			<div class="modal-header">
				<!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
				<h4 class="modal-title" id="myModalLabel">Update Form</h4>
			</div>
			<div class="modal-body">
			
				<form class="form" role="form" data-bind="with: activeForm" id="createFormForm" action="#" method="post">
					<div class="form-group">
						<label for="txt-name" class="control-label">Name: </label> 
						<input name="name"
							id="txt-name" type="text" class="form-control" data-bind="value: formDesignName, valueUpdate: 'input'" 
							 />
					</div>
					<div class="form-group">
						<label for="txt-desc" class="control-label">Description: </label>
						<textarea name="description" id="txt-desc" rows="3" class="form-control" data-bind="value: formDescription, valueUpdate: 'input'" 
							></textarea>
					</div>
					<div class="form-group">
			            <label for="txt-version" class="control-label">Collection Id: </label> <a data-bind="text: formCollectionId"></a>
			         </div>
					
					<div class="form-group">
			            <label for="txt-version" class="control-label">Version: </label> <input
			              id="txt-version" type="text" name="version" class="form-control" data-bind="value: versionNo, valueUpdate: 'input'" 
			              />
			         </div>
			          <div class="form-group"> 
			            <label for="bool-isDraft" class="control-label">Draft: </label> 
			            <select data-bind="optionsText: 'isDraft', value: isDraft, optionsCaption: 'Choose...'">
									<option value="true">true</option>
   									 <option value="false">false</option>
								</select>
			          </div>
				</form>
			</div>
			<div class="modal-footer">
        <button class="closeModalLink" type="button" class="btn"
          >Close</button>
      </div>
      
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
	
			<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<g:javascript disposition="defer" library="formsBuilder"/>	
	<r:script disposition="defer">
		
		var questions
		var collectionId
		
		<g:if test="${questions}">
			questions = JSON.stringify(${questions});
		</g:if>
		<g:else>
			questions = '';
		</g:else>
		
		<g:if test="${collectionId}">
			collectionId = '${collectionId}';
		</g:if>
		<g:else>
			collectionId = '';
		</g:else>
		
		if(collectionId!=''){
			createFormFromCollection(collectionId, questions);
		}else{
			createEmptyForm();
		}
	</r:script>
		
	</body>
</html>
