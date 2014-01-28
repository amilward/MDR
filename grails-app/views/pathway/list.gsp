
<%@ page import="uk.co.mdc.pathways.Pathway" %>
<!DOCTYPE html>
<html>
	<head>
<meta name="layout" content="main">
<g:set var="entityName" value="${message(code: 'pathway.label', default: 'Pathway')}" />
		<title>All pathways</title>
		<parameter name="name" value="Pathways" />
	</head>
	<body>
        <g:set var="grailsParams" value="${params.collect{ it.key + '=\'' + it.value + '\''}.join('; ')}" />
		<div class="box" ng-app="pathway-editor" ng-init="${grailsParams}}">
			<div id="list-pathways" class="content scaffold-list" role="main">
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<div id="pathwaysList" >
                    <table id="pathwayList" width="100%">
                        <thead>
                            <td><g:message code="pathway.name" /></td>
                            <td><g:message code="pathway.description" /></td>
                            <td><g:message code="pathway.userVersion" /></td>
                            <td><g:message code="pathway.isDraft" /></td>
                            <td>actions</td>
                        </thead>
                        <tbody>
                        <g:each in="${pathways}" var="pathway">
                            <tr>
                                <td><g:link controller="pathway" action="show" id="${pathway.id}">${pathway.name}</g:link></td>
                                <td>${pathway.description}</td>
                                <td>${pathway.userVersion}</td>
                                <td>${pathway.isDraft}</td>
                                <td><div confirm-delete on-confirm="deletePathway(${pathway.id})"></div></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
				</div>
			</div>
            <script type="text/ng-template" id="templates/deleteConfirmation.html">
                <div style="width: 25em;">
                    <div ng-hide="isDeleting" >
                        <button type="button" class="btn btn-danger btn-xs" ng-click="startDelete()"><i class="fa fa-trash-o"></i> Delete</button>
                    </div>
                    <div ng-show="isDeleting">
                        <button type="button" class="btn btn-default btn-xs" ng-click="cancel()">Abort deletion</button>
                        <button type="button" class="btn btn-danger btn-xs"  ng-click="confirm()"><i class="fa fa-trash-o"></i>Confirm (WARNING: this cannot be undone)</button>
                    </div>
                </div>
            </script>
		</div>
	</body>
</html>
