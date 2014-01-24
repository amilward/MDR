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
	
	@PreAuthorize("hasPermission(#sectionElement, admin)")
	@Transactional
	void addPermission(SectionElement sectionElement, String username, Permission permission) {
	   aclUtilService.addPermission sectionElement, username, permission
	}
	
	
	/* ************************* CREATE Form from collection***********************************
	 * requires that the authenticated user to have ROLE_USER to create a data element
	 ********************************************************************************* */
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_USER')")
	//!!!!!!!!!!!!!!!!need to change this from object
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	FormDesign create(Map parameters) {
		
		//create the form design
		
		FormDesign formDesignInstance = new FormDesign(
			name : parameters?.name,
			description : parameters?.description,
			versionNo : parameters?.versionNo,
			isDraft : parameters?.isDraft
			)
		
		if(!formDesignInstance.save(flush:true)){
			return formDesignInstance
		}
		
		
		// Grant the current user principal administrative permission
		
		addPermission formDesignInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission formDesignInstance, 'admin', BasePermission.ADMINISTRATION
		
		
		//FIXME Grant user user administrative permissions
		
		addPermission formDesignInstance, 'user', BasePermission.ADMINISTRATION
		
		//return the data element to the consumer (the controller)
		
		formDesignInstance
		
		}
	
	
	/* ************************* CREATE Form from collection***********************************
	 * requires that the authenticated user to have ROLE_USER to create a data element
	 ********************************************************************************* */
	
	@Transactional 
	@PreAuthorize("hasRole('ROLE_USER')") 
	//!!!!!!!!!!!!!!!!need to change this from object
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	FormDesign createFromCollection(Object form) { 

		def components = form.components
		def section
		def inputField
		def questions	
		def collection = (form?.formCollectionId) ? Collection.get(form?.formCollectionId.toInteger()) : null
		
		//create the form design
		
		FormDesign formDesignInstance = new FormDesign(
			collection : collection,
			name : form?.formDesignName,
			description : form?.formDescription,
			versionNo : form?.userVersion,
			isDraft : form?.isDraft
			).save(flush:true)
		
		//add questions to the form design
		
		//create questions.
			
		def sectionNumber = 1

		components.each{ component->
			
			if(component.section){
				
				section = component.section
					
				def sectionInstance = new SectionElement(title: section?.title, designOrder: sectionNumber).save(failOnError: true,flush:true)
				
				// Grant the current user principal administrative permission
				 
				 addPermission sectionInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
				 
				 //Grant admin user administrative permissions
				 
				 addPermission sectionInstance, 'admin', BasePermission.ADMINISTRATION

				questions = section.questions
				
				questions.each{ question->
						 
						 //create input field for new question
						 inputField = new InputField(
							 
							  defaultValue: question?.defaultValue,
							  placeholder: question?.placeholder,
							  maxCharacters: question?.maxCharacters,
							  unitOfMeasure: question?.unitOfMeasure,
							  dataType: getDataType(question?.dataTypeInstance?.name, question?.valueDomainId),
							  format: question?.format,
							 
							 ).save(failOnError: true, flush:true)
							 
							 // Grant the current user principal administrative permission
							 
							 addPermission inputField, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
							 
							 //Grant admin user administrative permissions
							 
							 addPermission inputField, 'admin', BasePermission.ADMINISTRATION
							 
							 //FIXME Grant user user administrative permissions
							 
							 addPermission inputField, 'user', BasePermission.ADMINISTRATION
							 
							  
						//create question
							 
					   def questionNumber = 1
							 
						question  = new QuestionElement(
								 designOrder: questionNumber,
								 prompt: question?.prompt,
								 style: question?.style,
								 label: question?.label,
								 additionalInstructions: question?.additionalInstructions,
								 inputField: inputField,
								 dataElement: (question?.dataElementId) ? DataElement.get(question?.dataElementId.toInteger()) : null,
								 valueDomain: (question?.valueDomainId) ? ValueDomain.get(question?.valueDomainId.toInteger()) : null
								 ).save(failOnError: true, flush:true)
								
								 // Grant the current user principal administrative permission
								 
								 addPermission question, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
								 
								 //Grant admin user administrative permissions
								 
								 addPermission question, 'admin', BasePermission.ADMINISTRATION
								 
								 //FIXME Grant user user administrative permissions
								 
								 addPermission question, 'user', BasePermission.ADMINISTRATION
	
								 questionNumber++
								 
								 sectionInstance.addToQuestionElements(question)
								  
						 }
				
				sectionNumber++
				formDesignInstance.addToFormDesignElements(sectionInstance)

			}
		}

		if(!formDesignInstance.save(flush:true)){
			return formDesignInstance
		}
		
		
		// Grant the current user principal administrative permission
		
		addPermission formDesignInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
		
		//Grant admin user administrative permissions
		
		addPermission formDesignInstance, 'admin', BasePermission.ADMINISTRATION
		
		
		//FIXME Grant user user administrative permissions
		
		addPermission formDesignInstance, 'user', BasePermission.ADMINISTRATION
		
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
		println(dataType)
		if(valueDomainId){
			ValueDomain valueDomain = ValueDomain.get(valueDomainId);
			println(valueDomain.dataType)
			return valueDomain.dataType
		}else if(DataType.findByName(dataType.capitalize())){
			println(DataType.findByName(dataType.capitalize()))
			return DataType.findByName(dataType.capitalize())
		}else{
			return DataType.findByName('String')
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
		
		formDesignInstance.name = form.formDesignName
		formDesignInstance.description = form.formDescription
		formDesignInstance.versionNo = form.userVersion
		formDesignInstance.isDraft = form.isDraft.toBoolean()
		
		//update questions.

		def sectionNumber = 1
		
		removeRedundantComponents(formDesignInstance, components)
		
		
		components.each{ component->
			
			def section = component?.section
			
				
			if(section?.sectionId){
				
			//update the section
				
				def sectionInstance = SectionElement.get(section?.sectionId)
				
				if(sectionInstance){
					
					// remove any questions that have specified for removal
					removeRedundantSectionQuestions(sectionInstance, section)
					
					
					sectionInstance.title = section?.title
					sectionInstance.designOrder = sectionNumber
					
					def questions = section.questions
					def questionNumber = 1
					
					questions.each{ question ->
						
						if(question?.questionId){
							
								def questionInstance = QuestionElement.get(question?.questionId)
				   
								if(questionInstance){
									
									questionInstance.prompt = question?.prompt
									questionInstance.label = question?.label
									questionInstance.additionalInstructions = question?.additionalInstructions
									questionInstance.designOrder =  questionNumber
									inputFieldInstance = InputField.get(question?.inputId)
									inputFieldInstance.defaultValue = question?.defaultValue
									inputFieldInstance.placeholder = question?.placeholder
									inputFieldInstance.maxCharacters = (question?.maxCharacters) ? question?.maxCharacters : 20
									inputFieldInstance.unitOfMeasure = question?.unitOfMeasure
									// inputField.dataType =  will fill this in later
									inputFieldInstance.format = question?.format
									
									
									questionInstance.save(flush:true, failOnError: true)
									inputFieldInstance.save(flush:true, failOnError: true)
								
								}
								
							}else{
							//create new question instance
							//get data type for new question
							def dataType = getDataType(question?.dataTypeInstance?.name, question?.valueDomainId)
							
							//create input field for new question
							def inputField = new InputField(
								
								 defaultValue: question?.defaultValue,
								 placeholder: question?.placeholder,
								 maxCharacters: question?.maxCharacters,
								 unitOfMeasure: question?.unitOfMeasure,
								 dataType: dataType,
								 format: question?.format,
								
								).save(flush:true, failOnError: true)
								
						   //create question
						   
						   def newQuestion  = new QuestionElement(
									designOrder: questionNumber,
									prompt: question?.prompt,
									style: question?.style,
									label: question?.label,
									additionalInstructions: question?.additionalInstructions,
									inputField: inputField
									).save(flush:true, failOnError: true)
								
							
									sectionInstance.addToQuestionElements(newQuestion)
									
							}
							
							questionNumber++
						
					}
					
					
					sectionNumber++
					
					sectionInstance.questionElements.sort()
					
					sectionInstance.save(flush:true, failOnError: true)
					
				}
					
			}else{
			
				section = component.section
					
				def sectionInstance = new SectionElement(title: section?.title, designOrder: sectionNumber).save(failOnError: true,flush:true)
				
				// Grant the current user principal administrative permission
				 
				 addPermission sectionInstance, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
				 
				 //Grant admin user administrative permissions
				 
				 addPermission sectionInstance, 'admin', BasePermission.ADMINISTRATION
				 
				 //FIXME Grant user user administrative permissions
				 
				 addPermission sectionInstance, 'user', BasePermission.ADMINISTRATION

				 def questions = section.questions
				 
				 def questionNumber = 1
				
				questions.each{ question->
						 
						 //create input field for new question
						 def inputField = new InputField(
							 
							  defaultValue: question?.defaultValue,
							  placeholder: question?.placeholder,
							  maxCharacters: question?.maxCharacters,
							  unitOfMeasure: question?.unitOfMeasure,
							  dataType: getDataType(question?.dataTypeInstance?.name, question?.valueDomainId),
							  format: question?.format,
							 
							 ).save(failOnError: true, flush:true)
							 
							 // Grant the current user principal administrative permission
							 
							 addPermission inputField, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
							 
							 //Grant admin user administrative permissions
							 
							 addPermission inputField, 'admin', BasePermission.ADMINISTRATION
							 
							 //FIXME Grant user user administrative permissions
							 
							 addPermission inputField, 'user', BasePermission.ADMINISTRATION
							 
							  
						//create question
							 
						def newQuestion  = new QuestionElement(
								 designOrder: questionNumber,
								 prompt: question?.prompt,
								 style: question?.style,
								 label: question?.label,
								 additionalInstructions: question?.additionalInstructions,
								 inputField: inputField,
								 dataElement: (question?.dataElementId) ? DataElement.get(question?.dataElementId.toInteger()) : null,
								 valueDomain: (question?.valueDomainId) ? ValueDomain.get(question?.valueDomainId.toInteger()) : null
								 ).save(failOnError: true, flush:true)
								
								 // Grant the current user principal administrative permission
								 
								 addPermission newQuestion, springSecurityService.authentication.name, BasePermission.ADMINISTRATION
								 
								 //Grant admin user administrative permissions
								 
								 addPermission newQuestion, 'admin', BasePermission.ADMINISTRATION
								 
								 //FIXME Grant user user administrative permissions
								 
								 addPermission newQuestion, 'user', BasePermission.ADMINISTRATION
	
								 questionNumber++
								 
								 sectionInstance.addToQuestionElements(newQuestion)
								  
						 }
				
				
				sectionNumber++
				formDesignInstance.addToFormDesignElements(sectionInstance)
				
				
			}
			
			
			
		}
		
		formDesignInstance.formDesignElements.sort()
		formDesignInstance.save(flush:true, failOnError: true)
		
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
	
	
	def removeRedundantSectionQuestions(SectionElement sectionInstance, updatedSection){
		
		//if there are no components i.e. ALL components need to be removed
		//from the form design after the edit
		
				if(updatedSection){
					
					//collect all the question ids
					def questionIds = []
					
					updatedSection.questions.each{ question ->
						
						questionIds.push(question.questionId.toString());
						
					}
					
					//see if all questions have been removed
					
					if(questionIds.size()==0 && sectionInstance?.questionElements.size()>0){
							
							//pass all the objects  elements into a new array (otherwise we get all sorts or problems)
							def questions = []
							questions += sectionInstance?.questionElements
							
							//remove ALL of the subelements
							
							questions.each{ question->
								
								sectionInstance.removeFromQuestionElements(question)
							}
							
						//else if there are some  elements
							
						}else if(questionIds){
						
							//but there are also  elements to remove
			
							//pass all the objects  elements into a new array (otherwise we get all sorts or problems)
							def questions = []
							questions += sectionInstance?.questionElements
							
							//remove the sub elements that need removing
							questions.each{ question->
										if(!questionIds.contains(question.id.toString())){
											sectionInstance.removeFromQuestionElements(question)
										}
							}
					}
						
				
				}
			
	}
	
	
	/* ************************* DATA ELEMENT LINKAGE FUNCTIONS************************
	 * unlinks the sub elements that have been removed during an update of the data element
	 ********************************************************************************* */
	
	
	def removeRedundantComponents(FormDesign formDesignInstance, updatedComponents){
		
		//if there are no components i.e. ALL components need to be removed
		//from the form design after the edit
					
					//collect all the question ids
					def sectionIds = []
					
					
					if(updatedComponents){
						updatedComponents.each{ component ->

							sectionIds.push(component.section.sectionId.toString());
							
						}
					}
					
					//see if all questions have been removed
					
					if(sectionIds.size()==0 && formDesignInstance?.formDesignElements.size()>0){
							
							//pass all the objects  elements into a new array (otherwise we get all sorts or problems)
							def sections = []
							sections += formDesignInstance?.formDesignElements
							
							//remove ALL of the subelements
							
							sections.each{ section->
	
								formDesignInstance.removeFromFormDesignElements(section)
								
								// Delete the ACL information as well
								//aclUtilService.deleteAcl section
								
								section.delete(flush: true, failOnError:true)
								
								
								
								formDesignInstance.save(failOnError:true, flush:true)

							}
							
						//else if there are some  elements
							
						}else if(sectionIds.size()>0){
					
						
							//but there are also  elements to remove
			
							//pass all the objects  elements into a new array (otherwise we get all sorts or problems)
							def sections = []
							sections += formDesignInstance?.formDesignElements
							
							//remove the sub elements that need removing
							sections.each{ section->

								
										if(!sectionIds.contains(section.id.toString())){
											formDesignInstance.removeFromFormDesignElements(section)
											
											// Delete the ACL information as well
											//aclUtilService.deleteAcl section
											
											section.delete(flush: true, failOnError:true)
											
											
											
											formDesignInstance.save(failOnError:true, flush:true)
											
											
										}
							}
					}
						
				
				}
		
}
