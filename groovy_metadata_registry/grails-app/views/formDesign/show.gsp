
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
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'layout.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'custom.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'font-awesome.min.css')}" type="text/css">
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'formDesign']">
				<g:hiddenField name="id" value="${formDesignInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						   		<li class="active"><a class="brand" href="#"><i class="icon-edit"></i> FormDesigner</a></li>
						   		<li><a href="#" onclick="updateForm('${formDesignInstance?.id}')">Update</a></li>
						  		 <li><g:link action="create" id="${formDesignInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${formDesignInstance?.id}')">Delete</a></li>
							    <li><g:link action="preview" id="${formDesignInstance?.id}"><g:message code="default.button.preview.label" default="Preview" /></g:link></li>
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
    <div id="EditSectionModal" class="modal fade hide" tabindex="-1"	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop = "true" >
	<div class="modal-dialog">
		<div class="modal-content" data-bind="">
			<div class="modal-header">
				<!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
				<h4 class="modal-title" id="myModalLabel">Update Section</h4>
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
		
		<g:if test="${formDesignInstance?.collection}">
			formInstanceCollectionId = ${formDesignInstance?.collection?.id}
		</g:if>
		<g:else>
			formInstanceCollectionId = ''
		</g:else>
		
		<g:if test="${formDesignInstance?.version}">
			formVersionNo = ${formDesignInstance?.version}
		</g:if>
		<g:else>
			formVersionNo = ''
		</g:else>
		
		<g:if test="${formDesignInstance?.id}">
			formInstanceId = ${formDesignInstance.id}
		</g:if>
		<g:else>
			formInstanceId = ''
		</g:else>
		
		<g:if test="${formDesignInstance?.name}">
			formInstanceName = "${formDesignInstance.name}"
		</g:if>
		<g:else>
			formInstanceName = ''
		</g:else>
		
		<g:if test="${formDesignInstance?.description}">
			formInstanceDescription = "${formDesignInstance.description}"
		</g:if>
		<g:else>
			formInstanceDescription = ''
		</g:else>
		
		<g:if test="${formDesignInstance?.versionNo}">
			versionNo = "${formDesignInstance.versionNo}"
		</g:if>
		<g:else>
			versionNo = ''
		</g:else>

		<g:if test="${formDesignInstance?.isDraft}!=null">
		
			isDraft = "${formDesignInstance.isDraft}"
		</g:if>
		<g:else>
			isDraft = ''
		</g:else>

		openForms(formInstanceId, formInstanceName, formInstanceDescription, versionNo, isDraft, formInstanceCollectionId, formVersionNo);
		
	</r:script>
		
	</body>
</html>
