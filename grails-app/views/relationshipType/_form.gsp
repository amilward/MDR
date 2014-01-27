<%@ page import="uk.co.mdc.catalogue.RelationshipType" %>

<table class="table table-hovered">
    <tbody>
    <tr class="${hasErrors(bean: relationshipTypeInstance, field: 'name', 'error')} ">
        <td class="left_col_show"><span id="name-label" ><g:message code="relationshipType.name.label" default="Name" /></span></td>
        <td class="right_col_show"><g:textField title="tooltip.relationshipType.name" class="input-large input-block-level"  name="name" value="${relationshipTypeInstance?.name}"/></td>
    </tr>
    <tr class="${hasErrors(bean: relationshipTypeInstance, field: 'description', 'error')} ">
        <td class="left_col_show"><span id="name-label" ><g:message code="relationshipType.description.label" default="Description" /></span></td>
        <td class="right_col_show"><g:textArea title="tooltip.relationshipType.description" class="input-block-level" name="description" value="${relationshipTypeInstance?.description}"/></td>
    </tr>

    </tbody>
</table>



