package uk.co.mdc.forms

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission
import org.springframework.transaction.annotation.Transactional

import uk.co.mdc.model.DataElement;
import uk.co.mdc.model.ValueDomain;
import uk.co.mdc.model.DataType;
import uk.co.mdc.model.Collection;
import grails.converters.JSON

import org.codehaus.groovy.grails.web.json.*;

	/* *********************************************************************
	 * This service allows the user to access the form design model
	 * It will be called by the form design controller
	 * *************************************************************** */


class FormDesignService {

    static transactional = false
	
	def aclPermissionFactory
	def aclService
	def aclUtilService
	def springSecurityService
	
	
	/* **************************** ADD PERMISSIONS *****************************************
	 * calls add permission with the relevant permission when called with an integer 
	 * permission input
	 ********************************************************************************* */
		
	void addPermission(FormDesign formDesign, String username, int permission){
		
		addPermission formDesign, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	void addPermission(QuestionElement questionElement, String username, int permission){
		
		addPermission questionElement, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	void addPermission(InputField inputField, String username, int permission){
		
		addPermission inputField, username,
			aclPermissionFactory.buildFromMask(permission)
			
	}
	
	
	/*
	 * requires that the authenticated user have admin permission on the form design instance
	 * to grant a permission to someone else
	 * */
	
	@PreAuthorize("hasPermission(#formDesign, admin)")
	@Transactional
	void addPermission(FormDesign formDesign, String username, Permission permission) {
	   aclUtilService.addPermission formDesign, username, permission
	}
	
	@PreAuthorize("hasPermission(#inputField, admin)")
	@Transactional
	void addPermission(InputField inputField, String username, Permission permission) {
	   aclUtilService.addPermission inputField, username, permission
	}
	
	@PreAuthorize("hasPermission(#questionElement, admin)")
	@Transactional
	void addPermission(QuestionElement questionElement, String username, Permission permission) {
	   aclUtilService.addPermission questionElement, username, permission
	}
	
	
	/* ************************* CREATE DATA ELEMENTS***********************************
	 * requires that the authenticated user to have ROLE_USER to create a data element
	 ********************************************************************************* */
	
	@Transactional 
	@PreAuthorize("hasRole('ROLE_USER')") 
	//!!!!!!!!!!!!!!!!need to change this from object
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	FormDesign create(Object form) { 
		
		def components = form.components
		
		def inputField
		def question		
		def collection = (form?.formCollectionId) ? Collection.get(form?.formCollectionId.toInteger()) : null
		
		//create the form design
		
		FormDesign formDesignInstance = new FormDesign(
			collection : collection,
			refId : form?.formRefId,
			name : form?.formDesignName,
			description : form?.formDescription,
			versionNo : form?.versionNo,
			isDraft : form?.isDraft
			).save(flush:true)
		
		//add questions to the form design
		
		//create questions.

		components.each{ component->
					
						 question = component.question
						 
						 //create input field for new question
						 inputField = new InputField(
							 
							  defaultValue: component.question?.defaultValue,
							  placeholder: component.question?.placeholder,
							  maxCharacters: component.question?.maxCharacters,
							  unitOfMeasure: component.question?.unitOfMeasure,
							  dataType: getDataType(component.question?.dataTypeInstance?.name, component.question?.valueDomainId),
							  format: component.question?.format,
							 
							 ).save(failOnError: true, flush:true)
							 
							 // Grant the current user principal administrative permission
							 
							 addPermission inputField, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
							 
							 //Grant admin user administrative permissions
							 
							 addPermission inputField, 'admin', BasePermission.ADMINISTRATION
							 
							  
						//create question
							 
						question  = new QuestionElement(
								 questionNumber: '1',
								 prompt: component.question?.prompt,
								 style: component.question?.style,
								 label: component.question?.label,
								 additionalInstructions: component.question?.additionalInstructions,
								 inputField: inputField,
								 dataElement: (component.question?.dataElementId) ? DataElement.get(component.question?.dataElementId.toInteger()) : null,
								 valueDomain: (component.question?.valueDomainId) ? ValueDomain.get(component.question?.valueDomainId.toInteger()) : null
								 ).save(failOnError: true,flush:true)
								
								 // Grant the current user principal administrative permission
								 
								 addPermission question, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
								 
								 //Grant admin user administrative permissions
								 
								 addPermission question, 'admin', BasePermission.ADMINISTRATION
	
								 formDesignInstance.addToFormDesignElements(question)
								 
						 }

		if(!formDesignInstance.save(flush:true)){
			return formDesignInstance
		}
		
		
		// Grant the current user principal administrative permission
		
		addPermission formDesignInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission formDesignInstance, 'admin', BasePermission.ADMINISTRATION
		
		
		//return the data element to the consumer (the controller)
		
		formDesignInstance
		
		}
	
	
	
	/* ************************* GET DATA ELEMENTS***********************************************
	 * requires that the authenticated user have read or admin permission on the specified Data Element
	 ******************************************************************************************** */
	
	@PreAuthorize('hasPermission(#id, "uk.co.mdc.forms.FormDesign", read) or hasPermission(#id, "uk.co.mdc.forms.FormDesign", admin)')
	FormDesign get(long id) {
	   FormDesign.get id
	   }
	
	
	/* ************************* SEARCH DATA ELEMENTS***********************************************
	 * requires that the authenticated user have ROLE_USER and read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 * */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<FormDesign> search(String sSearch) {
	   def searchResult = FormDesign.search(sSearch)
	   searchResult.results
	   }
	
	
	/* ************************* LIST DATA ELEMENTS***********************************************
	 * requires that the authenticated user have ROLE_USER sand read or admin permission on each
	 * returned Data Element; instances that don't have granted permissions will be removed from the returned
	 * List
	 ******************************************************************************************** */
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
	List<FormDesign> list(Map parameters) {
		FormDesign.list parameters
	}
	
	//no restrictions on the count method
	
	int count() { FormDesign.count() }
	
	//!!!!!!!!!!!!!!
	//no restriction on the getDataType MEthod at the moment
	
	def getDataType(String dataType, Integer valueDomainId){
		if(valueDomainId){
			ValueDomain valueDomain = ValueDomain.get(valueDomainId);
			return valueDomain.dataType
		}else{
			return DataType.findByName(dataType.capitalize())
		}
	}
	
	/* ************************* UPDATE FORM DESIGN***********************************************
	 *  requires that the authenticated user have write or admin permission on the form design instance to edit it
	 ******************************************************************************************** */
	
	@Transactional
	@PreAuthorize("hasPermission(#formDesignInstance, write) or hasPermission(#formDesignInstance, admin)")
	
	def update(FormDesign formDesignInstance, Object form){

		def components = form.components
		def inputFieldInstance
		def question
		
		formDesignInstance.refId = form.formRefId
		formDesignInstance.name = form.formDesignName
		formDesignInstance.description = form.formDescription
		formDesignInstance.versionNo = form.versionNo
		formDesignInstance.isDraft= form.isDraft
	   
		
		//check to see if any components have been deleted
		
		// remove any questions that have specified for removal
		removeRedundantQuestions(formDesignInstance, components)
		
		
		//update questions.

		components.each{ component->
			
			question = component.question

			if(question?.questionId){
			
				def questionInstance = QuestionElement.get(question?.questionId)
   
				if(questionInstance){
					
					questionInstance.prompt = question?.prompt
					questionInstance.additionalInstructions = question?.additionalInstructions

					inputFieldInstance = InputField.get(question?.inputId)
					inputFieldInstance.defaultValue = question?.defaultValue
					inputFieldInstance.placeholder = question?.placeholder
					inputFieldInstance.maxCharacters = (question?.maxCharacters) ? question?.maxCharacters : 20
					inputFieldInstance.unitOfMeasure = question?.unitOfMeasure
					// inputField.dataType =  will fill this in later
					inputFieldInstance.format = question?.format
					inputFieldInstance.save()
				
				}
				
			}else{
			//create new question instance
			//get data type for new question
			def dataType = getDataType(component.question?.dataTypeInstance?.name, component.question?.valueDomainId)
			
			//create input field for new question
			def inputField = new InputField(
				
				 defaultValue: component.question?.defaultValue,
				 placeholder: component.question?.placeholder,
				 maxCharacters: component.question?.maxCharacters,
				 unitOfMeasure: component.question?.unitOfMeasure,
				 dataType: dataType,
				 format: component.question?.format,
				
				).save(failOnError: true)
				
		   //create question
		   
		   def newQuestion  = new QuestionElement(
					questionNumber: '1',
					prompt: component.question?.prompt,
					style: component.question?.style,
					label: component.question?.label,
					additionalInstructions: component.question?.additionalInstructions,
					inputField: inputField
					).save(failOnError: true)
				
			
					formDesignInstance.addToFormDesignElements(newQuestion)
					
			}
			
		}
		
		return formDesignInstance
		
	}
	
	
	/* ************************* DELETE DATA ELEMENTS***********************************************
	 * requires that the authenticated user have delete or admin permission on the report instance to
	 * edit it
	 ******************************************************************************************** */

	@Transactional @PreAuthorize("hasPermission(#formDesignInstance, delete) or hasPermission(#formDesignInstance, admin)")
	void delete(FormDesign formDesignInstance) {
		
		//formDesignInstance.prepareForDelete()
		formDesignInstance.delete(flush: true)
		
		// Delete the ACL information as well
		aclUtilService.deleteAcl formDesignInstance
   }
	
	
	/* ************************* DELETE PERMISSIONS***********************************************
	 * deletePermission requires that the authenticated user have admin permission on the report
	 *  instance to delete a grant
	 ******************************************************************************************** */
	
	@Transactional @PreAuthorize("hasPermission(#formDesignInstance, admin)")
	void deletePermission(FormDesign formDesignInstance, String username, Permission permission) {
		def acl = aclUtilService.readAcl(formDesignInstance)
		
		// Remove all permissions associated with this particular
		// recipient (string equality to KISS)
		
		acl.entries.eachWithIndex {
			entry, i -> if (entry.sid.equals(recipient) && entry.permission.equals(permission)) {
				acl.deleteAce i
				}
			}
		
		aclService.updateAcl acl
		
		}
	
	
	/* ************************* DATA ELEMENT LINKAGE FUNCTIONS************************
	 * unlinks the sub elements that have been removed during an update of the data element
	 ********************************************************************************* */
	
	
	def removeRedundantQuestions(FormDesign formDesignInstance, components){
		
		//if there are no components i.e. ALL components need to be removed
		//from the form design after the edit
		
		def questionIds = []
		
		components.each{ component->
			
			questionIds.push(component.question.questionId.toString());
			
			}
		
			if(questionIds.size()==0 && formDesignInstance?.formDesignElements.size()>0){
				
				//pass all the objects  elements into a new array (otherwise we get all sorts or problems)
				def formDesignElements = []
				formDesignElements += formDesignInstance?.formDesignElements
				
				//remove ALL of the subelements
				
				formDesignElements.each{ element->
					
					formDesignInstance.removeFromFormDesignElements(element)
				}
				
			//else if there are some  elements
				
			}else if(questionIds){
			
				//but there are also  elements to remove

				//pass all the objects into a new array (otherwise we get all sorts or problems)
				def formDesignElements = []
				formDesignElements += formDesignInstance?.formDesignElements
				
				//remove the sub elements that need removing
				formDesignElements.each{ element->
							if(!questionIds.contains(element.id.toString())){
								formDesignInstance.removeFromFormDesignElements(element)
							}
				}
		}
	}
	
	
	
	
}
