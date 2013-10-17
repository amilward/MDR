
<!-- --------------------------------------------------------------- -->
<!-- Built using James Welch's Forms Builder ------------------------- -->
<!-- --------------------------------------------------------------- -->


<%@ page import="uk.co.mdc.forms.FormDesign" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'formDesign.label', default: 'FormDesign')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" FORM Builder - ${formDesignInstance?.name}" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-editable.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'layout.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'custom.css')}" type="text/css">
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'formDesign']">
				<g:hiddenField name="id" value="${collectionInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						   		<li class="active"><a class="brand" href="#"><i class="icon-edit"></i> FormDesigner</a></li>
						   		<li><a href="#" onclick="updateForm('${formDesignInstance?.id}')">Update</a></li>
						  		 <li><g:link action="create" id="${formDesignInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${formDesignInstance?.name}')">Delete</a></li>
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
		
		<g:if test="${formDesignInstance?.id}">
			formInstanceRefId = "${formDesignInstance.refId}"
		</g:if>
		<g:else>
			formInstanceRefId = ''
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
		
		<g:if test="${formDesignInstance?.isDraft}">
			isDraft = "${formDesignInstance.isDraft}"
		</g:if>
		<g:else>
			isDraft = ''
		</g:else>
		
		openForms(formInstanceId, formInstanceRefId, formInstanceName, formInstanceDescription, versionNo, isDraft, formInstanceCollectionId, formVersionNo);
		
	</r:script>
		
	</body>
</html>
