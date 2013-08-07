
<%@ page import="uk.co.mdc.model.ExternalSynonym" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'externalSynonym.label', default: 'ExternalSynonym')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-externalSynonym" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-externalSynonym" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list externalSynonym">
			
				<g:if test="${externalSynonymInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="externalSynonym.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${externalSynonymInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${externalSynonymInstance?.url}">
				<li class="fieldcontain">
					<span id="url-label" class="property-label"><g:message code="externalSynonym.url.label" default="Url" /></span>
					
						<span class="property-value" aria-labelledby="url-label"><g:fieldValue bean="${externalSynonymInstance}" field="url"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${externalSynonymInstance?.attributes}">
					<h1>Attributes:</h1>
						<table>
							<thead>
								<tr>
									<th>Attribute</th>
									<th>Value</th>
								</tr>
							</thead>
							<g:each var="attribute" in="${externalSynonymInstance.attributes}">
								<tr>
									<td>${attribute?.key}</td>
									<td>${attribute?.value}</td>
								</tr>
							</g:each>
					</table>
				</g:if>
				
				
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${externalSynonymInstance?.id}" />
					<g:link class="edit" action="edit" id="${externalSynonymInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
