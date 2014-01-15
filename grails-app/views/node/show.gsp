
<%@ page import="uk.co.mdc.pathways.Node" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'node.label', default: 'Node')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-node" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-node" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list node">
			
				<g:if test="${nodeInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="node.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${nodeInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
			
				<g:if test="${nodeInstance?.pathwaysModel}">
				<li class="fieldcontain">
					<span id="pathwaysModel-label" class="property-label"><g:message code="node.pathwaysModel.label" default="Pathways Model" /></span>
					
						<span class="property-value" aria-labelledby="pathwaysModel-label"><g:link controller="pathwaysModel" action="show" id="${nodeInstance?.pathwaysModel?.id}">${nodeInstance?.pathwaysModel?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${nodeInstance?.x}">
				<li class="fieldcontain">
					<span id="x-label" class="property-label"><g:message code="node.x.label" default="X" /></span>
					
						<span class="property-value" aria-labelledby="x-label"><g:fieldValue bean="${nodeInstance}" field="x"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${nodeInstance?.y}">
				<li class="fieldcontain">
					<span id="y-label" class="property-label"><g:message code="node.y.label" default="Y" /></span>
					
						<span class="property-value" aria-labelledby="y-label"><g:fieldValue bean="${nodeInstance}" field="y"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${nodeInstance?.mandatoryInputs}">
				<li class="fieldcontain">
					<span id="mandatoryInputs-label" class="property-label"><g:message code="node.mandatoryInputs.label" default="Mandatory Inputs" /></span>
					
						<g:each in="${nodeInstance.mandatoryInputs}" var="m">
						<span class="property-value" aria-labelledby="mandatoryInputs-label"><g:link controller="collection" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${nodeInstance?.mandatoryOutputs}">
				<li class="fieldcontain">
					<span id="mandatoryOutputs-label" class="property-label"><g:message code="node.mandatoryOutputs.label" default="Mandatory Outputs" /></span>
					
						<g:each in="${nodeInstance.mandatoryOutputs}" var="m">
						<span class="property-value" aria-labelledby="mandatoryOutputs-label"><g:link controller="collection" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${nodeInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="node.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${nodeInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${nodeInstance?.optionalInputs}">
				<li class="fieldcontain">
					<span id="optionalInputs-label" class="property-label"><g:message code="node.optionalInputs.label" default="Optional Inputs" /></span>
					
						<g:each in="${nodeInstance.optionalInputs}" var="o">
						<span class="property-value" aria-labelledby="optionalInputs-label"><g:link controller="collection" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${nodeInstance?.optionalOutputs}">
				<li class="fieldcontain">
					<span id="optionalOutputs-label" class="property-label"><g:message code="node.optionalOutputs.label" default="Optional Outputs" /></span>
					
						<g:each in="${nodeInstance.optionalOutputs}" var="o">
						<span class="property-value" aria-labelledby="optionalOutputs-label"><g:link controller="collection" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${nodeInstance?.id}" />
					<a href="#" onclick="updateNode({'nodeInstance':{'id':${nodeInstance?.id},'nodeVersionNo': ${nodeInstance?.version},'name':'transfer to O.R. TEST2','description':'transfer patient to the Operating Room','x':'5','y':'0','mandatoryInputs':[],'mandatoryOutputs':[],'optionalInputs':[],'optionalOutputs':[]}})"">Update</a>
					<a href="#" onclick="createNode({'nodeInstance':{'refId': 'TMN123','pathwaysModelId': 1, 'name':'transfer to O.R. TEST CREATE','description':'test ccreate - transfer patient to the Operating Room','x':'15','y':'10','mandatoryInputs':[],'mandatoryOutputs':[],'optionalInputs':[],'optionalOutputs':[]}})">Create</a>
					<a href="#" onclick="deleteNode(${nodeInstance?.id})">Delete</a>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
		
		<g:javascript disposition="defer" library="ajaxfunctions" />
	<r:script disposition="defer">
		//getPathway(${pathwaysModelInstance?.id});
		
		//getNode(1);
		
		
		
	</r:script>
		
		
	</body>
</html>
