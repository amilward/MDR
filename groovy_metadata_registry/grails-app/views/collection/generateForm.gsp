<%@ page import="uk.co.mdc.model.Collection" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'collection.label', default: 'Collection')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="show-collection-form" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<g:form method="post" action="saveForm">
				<g:hiddenField name="collectionId" value="${collectionInstance?.id}" />
			
			<g:if test="${collectionInstance.mandatoryDataElementCollections()!=null}">
			
			
						<g:each var="dataElement" in="${collectionInstance.mandatoryDataElementCollections()}">
						
						
						<div class="fieldcontain ${hasErrors(bean: collectionInstance, field: 'name', 'error')} ">
							<label for="name">
								<g:message code="collection.name.label" default="${dataElement?.name}" />
								
							</label>
							<g:textField name="${dataElement?.name}" value=""/>
						</div>
						
						</g:each>
				
			</g:if>	
			
		
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
