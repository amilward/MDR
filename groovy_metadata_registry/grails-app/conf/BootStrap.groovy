import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.web.context.support.WebApplicationContextUtils
import uk.co.mdc.SecAuth
import uk.co.mdc.SecUser
import uk.co.mdc.SecUserSecAuth
import uk.co.mdc.forms.*
import uk.co.mdc.model.*
import uk.co.mdc.pathways.Link
import uk.co.mdc.pathways.Node
import uk.co.mdc.pathways.PathwaysModel
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import static org.springframework.security.acls.domain.BasePermission.ADMINISTRATION


class BootStrap {
	def aclService
	def aclUtilService
	def objectIdentityRetrievalStrategy
	def sessionFactory
	def springSecurityService
	def grailsApplication

	def init = { servletContext ->

		def springContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )
		
		//register custom json Marshallers
		registerJSONMarshallers(springContext)

		//register spring filters (in this case the rest api security filter)
		registerSpringFilters()
		
		environments {
			production {
				
				createAdminAccount()
			}
			staging{
				importDevData()
			}
			test{
				importDevData()
			}
			development {
				importDevData()
			}
		}
	}

	private importDevData(){
		if(!SecUser.findByUsername('user1')){
			//this if needs to be removed....only for development purposes

			//create user if none exists
			createUsers()

			//login as admin so you can create the prepopulated data
			loginAsAdmin()

			//populate with some test data....there will be more
			populateWithTestData()

			//grant relevant permissions (i.e. admin user has admin on everything)
			grantPermissions()

			sessionFactory.currentSession.flush()

			// logout
			SCH.clearContext()
		}
	}

	private createAdminAccount(){
		def roleUser = SecAuth.findByAuthority('ROLE_USER') ?: new SecAuth(authority: 'ROLE_USER').save(failOnError: true)
		def roleAdmin = SecAuth.findByAuthority('ROLE_ADMIN') ?: new SecAuth(authority: 'ROLE_ADMIN').save(failOnError: true)
		
		def admin = SecUser.findByUsername('localadmin') ?: new SecUser(username: 'localadmin', emailAddress: "brcmodelcatalogue@gmail.com", enabled: true, password: 'QpAsN#6HVP.6da').save(failOnError: true)
		
				if (!admin.authorities.contains(roleAdmin)) {
					SecUserSecAuth.create admin, roleUser
					SecUserSecAuth.create admin, roleAdmin, true
				}
	}
	
	private registerSpringFilters(){

		SpringSecurityUtils.clientRegisterFilter('apiAuthFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 10)

	}

	private registerJSONMarshallers(springContext) {

		//register custom marshallers
		springContext.getBean('customObjectMarshallers').register()
	}

	private void createUsers() {
		def rolePending = SecAuth.findByAuthority('ROLE_PENDING') ?: new SecAuth(authority: 'ROLE_PENDING').save(failOnError: true)
		def roleUser = SecAuth.findByAuthority('ROLE_USER') ?: new SecAuth(authority: 'ROLE_USER').save(failOnError: true)
		def roleAdmin = SecAuth.findByAuthority('ROLE_ADMIN') ?: new SecAuth(authority: 'ROLE_ADMIN').save(failOnError: true)


		if(!SecUser.findByUsername('user1') ){	
			3.times {
				long id = it + 1
				def user = new SecUser(username: "user$id", enabled: true, emailAddress: "testuser$id@example.org", password: "password$id").save(failOnError: true)
				SecUserSecAuth.create user, roleUser
			}
		}

		def admin = SecUser.findByUsername('admin') ?: new SecUser(username: 'admin', emailAddress: "testadmin1@example.org", enabled: true, password: 'admin123').save(failOnError: true)

		if (!admin.authorities.contains(roleAdmin)) {
			SecUserSecAuth.create admin, roleUser
			SecUserSecAuth.create admin, roleAdmin, true
		}


	}


	private void loginAsAdmin() {
		// have to be authenticated as an admin to create ACLs
		SCH.context.authentication = new UsernamePasswordAuthenticationToken( 'admin', 'admin123', AuthorityUtils.createAuthorityList('ROLE_ADMIN'))

	}

	private void grantPermissions() {
		def dataElements = []

		// grant ROLE_ADMIN on everything

		grantAdminPermissions(DataElement.list())
		grantAdminPermissions(ValueDomain.list())
		grantAdminPermissions(ConceptualDomain.list())
		grantAdminPermissions(DataElementConcept.list())
		grantAdminPermissions(DataType.list())
		grantAdminPermissions(Document.list())
		grantAdminPermissions(ExternalReference.list())
		grantAdminPermissions(Collection.list())
		grantAdminPermissions(FormDesign.list())
		grantAdminPermissions(QuestionElement.list())
		grantAdminPermissions(InputField.list())
		grantAdminPermissions(Node.list())
		grantAdminPermissions(Link.list())
		grantAdminPermissions(PathwaysModel.list())
		
		// Grant ROLE_USER on everything
		grantUserPermissions(DataElement.list())
		grantUserPermissions(ValueDomain.list())
		grantUserPermissions(ConceptualDomain.list())
		grantUserPermissions(DataElementConcept.list())
		grantUserPermissions(DataType.list())
		grantUserPermissions(Document.list())
		grantUserPermissions(ExternalReference.list())
		grantUserPermissions(Collection.list())
		grantUserPermissions(FormDesign.list())
		grantUserPermissions(QuestionElement.list())
		grantUserPermissions(InputField.list())
		grantUserPermissions(PathwaysModel.list())

	}


	def grantAdminPermissions(objectList){
		for (object in objectList) {
			aclUtilService.addPermission object, 'ROLE_ADMIN', ADMINISTRATION
			
			//FIX ME at present users can see everything but we don't want this to be the case
			aclUtilService.addPermission object, 'ROLE_USER', ADMINISTRATION
		}
	}
	def grantUserPermissions(objectList){
		for (object in objectList) {
			aclUtilService.addPermission object, 'admin', ADMINISTRATION
		}
	}
	

	def destroy = {
	}


	/*
	 * **********************POPULATE WITH FORMS TEST DATA********************************
	 *
	 * */

	private populateWithTestData(){


		//populate with test data
		def applicationContext = grailsApplication.mainContext
		String basePath = applicationContext.getResource("/").getFile().toString()


		//assumes the first line of the file has the field names


		if (!ExternalReference.count()) {
			def externalReferences = new XmlSlurper().parse( new File("${basePath}/WEB-INF/bootstrap-data/ExternalReference.xml"))
			externalReferences.externalReference.each() { e ->
				new ExternalReference(e.attributes()).save(failOnError: true) //assumes the keys match the ExternalReference properties
			}

		}

		def string
		
		def date
		
		if (!DataType.count()) {
			
			string = new DataType(name:"String", enumerated: false).save(failOnError: true)
			new DataType(name:"Text", enumerated: false).save(failOnError: true)
			new DataType(name:"Integer", enumerated: false).save(failOnError: true)
			date = new DataType(name:"Date", enumerated: false).save(failOnError: true)
			new DataType(name:"Datetime", enumerated: false).save(failOnError: true)
			new DataType(name:"Time", enumerated: false).save(failOnError: true)
			new DataType(name:"Float", enumerated: false).save(failOnError: true)
			new DataType(name:"Boolean", enumerated: false).save(failOnError: true)
			new DataType(name:"Blob", enumerated: false).save(failOnError: true)
			
			
		}
		
		

		if(!FormDesign.count()){
			
			def rule1 = new Rule(
				name: 'display section rule',
				predicate: 'question1 > 5',
				consequence: 'display'
				).save(failOnError:true)

			def inputField1 = new InputField(

					defaultValue: 'test default',
					placeholder: 'test placeholder',
					maxCharacters: 11,
					unitOfMeasure: 'test UOM',
					dataType: string,
					format: 'test format',

					).save(failOnError: true)

			def inputField2 = new InputField(

					defaultValue: 'test default',
					placeholder: 'test placeholder',
					maxCharacters: 20,
					unitOfMeasure: 'test2 UOM',
					dataType: string,
					format: 'test format2',

					).save(failOnError: true)

			def inputField3 = new InputField(

					defaultValue: 'te3st default',
					placeholder: 'test3 placeholder',
					maxCharacters: 13,
					unitOfMeasure: 'tes3t UOM',
					dataType: date,
					format: 'test forma3t',

					).save(failOnError: true)

			def inputField4 = new InputField(

					defaultValue: 'test default',
					placeholder: 'test placeholder',
					maxCharacters: 9,
					unitOfMeasure: 'test UOM',
					dataType: string,
					format: 'test format',

					).save(failOnError: true)

			def inputField5 = new InputField(

					defaultValue: 'test default',
					placeholder: 'test pladasceholder',
					maxCharacters: 11,
					unitOfMeasure: 'test UOM',
					dataType: string,
					format: 'test format',

					).save(failOnError: true)

			def question1 = new QuestionElement(
					designOrder: 1,
					prompt: 'how old are you',
					style: 'this style1',
					label: 'how old are you?',
					additionalInstructions: 'more instructions',
					inputField: inputField1
					).save(failOnError: true)

			def question2 = new QuestionElement(
					designOrder: 2,
					prompt: 'operation reference',
					style: 'this style3',
					label: 'origin of referral',
					additionalInstructions: 'more instructions2 ',
					inputField: inputField2
					).save(failOnError: true)

			def question3 = new QuestionElement(
					designOrder: '3',
					prompt: 'this is the thirs question',
					style: 'this style5',
					label: 'what is your favorite colour ?',
					additionalInstructions: 'more instructions',
					inputField: inputField3
					).save(failOnError: true)

			def question4 = new QuestionElement(
					designOrder: 4,
					prompt: 'this is the 4th question',
					style: 'this style5',
					label: 'what is your favorite animal ?',
					additionalInstructions: 'more instructions',
					inputField: inputField4
					).save(failOnError: true)

			def question5 = new QuestionElement(
					designOrder: 5,
					prompt: 'this is the 5th question',
					style: 'this style5',
					label: 'what is your favorite car ?',
					additionalInstructions: 'more instructions',
					inputField: inputField5
					).save(failOnError: true)


			def formDesignInstance = new FormDesign(
			name:'formDesignName1',
			versionNo:'V0.1',
			isDraft:true,
			description:'test description 1'
			).save(failOnError: true)


			def section1 = new SectionElement(
					title: 'section1',
					designOrder: 1
					).save(failOnError:true)

			def section2 = new SectionElement(
					title: 'section2',
					designOrder: 2
					).save(failOnError:true)
					
			section1.addToQuestionElements(question1)
			section1.addToQuestionElements(question2)
			section1.addToQuestionElements(question3)

			
			section2.addToRules(rule1)
			section2.addToQuestionElements(question4)
			section2.addToQuestionElements(question5)

			formDesignInstance.addToFormDesignElements(section1)
			formDesignInstance.addToFormDesignElements(section2)
		}

		if(!PathwaysModel.count()){

			
			
			def pathway1 = new PathwaysModel(
				name: 'Transplanting and Monitoring Pathway',
				versionNo: '0.1',
				isDraft: true
				).save(failOnError:true)
				
			def node1 = new Node(
					
					name: 'transfer to O.R.',
					x: '5px',
					y: '0px',
					description: 'transfer patient to the Operating Room'
					).save(flush:true)

			def node2 = new Node(
					
					name: 'Anaesthesia and Operating Patient.',
					x: '150px',
					y: '100px',
					description: 'perform the operation'
					).save(flush:true)

			def node3 = new Node(
					
					name: 'Guarding Patient on recovery and transfer to nursing ward',
					x: '250px',
					y: '300px',
					description: 'transfer patient to the Operating Room'
					).save(flush:true)

			def link1 = new Link(
					name: 'TM1',
					source: node1,
					target: node2,
					).save(flush:true)

			def link2 = new Link(
					name: 'TM2',
					source: node2,
					target: node3,
					).save(flush:true)



			pathway1.addToPathwayElements(node1)
			pathway1.addToPathwayElements(node2)
			pathway1.addToPathwayElements(node3)
			pathway1.addToPathwayElements(link1)
			pathway1.addToPathwayElements(link2)
			pathway1.save(flush:true)
			
			
			//Add a form to the pathways
			
						def rulepw1 = new Rule(
								name: 'display section rule',
								predicate: 'question1 > 5',
								consequence: 'display'
								).save(failOnError:true)
			
						def inputFieldpw1 = new InputField(
			
								defaultValue: 'test default',
								placeholder: 'test placeholder',
								maxCharacters: 11,
								unitOfMeasure: 'test UOM',
								dataType: string,
								format: 'test format',
			
								).save(failOnError: true)
			
						def inputFieldpw2 = new InputField(
			
								defaultValue: 'test default',
								placeholder: 'test placeholder',
								maxCharacters: 20,
								unitOfMeasure: 'test2 UOM',
								dataType: string,
								format: 'test format2',
			
								).save(failOnError: true)
			
						def inputFieldpw3 = new InputField(
			
								defaultValue: 'te3st default',
								placeholder: 'test3 placeholder',
								maxCharacters: 13,
								unitOfMeasure: 'tes3t UOM',
								dataType: date,
								format: 'test forma3t',
			
								).save(failOnError: true)
			
						def inputFieldpw4 = new InputField(
			
								defaultValue: 'test default',
								placeholder: 'test placeholder',
								maxCharacters: 9,
								unitOfMeasure: 'test UOM',
								dataType: string,
								format: 'test format',
			
								).save(failOnError: true)
			
						def inputFieldpw5 = new InputField(
			
								defaultValue: 'test default',
								placeholder: 'test pladasceholder',
								maxCharacters: 11,
								unitOfMeasure: 'test UOM',
								dataType: string,
								format: 'test format',
			
								).save(failOnError: true)
			
						def questionpw1 = new QuestionElement(
								designOrder: 1,
								prompt: 'how old are you',
								style: 'this style1',
								label: 'how old are you?',
								additionalInstructions: 'more instructions',
								inputField: inputFieldpw1
								).save(failOnError: true)
			
						def questionpw2 = new QuestionElement(
								designOrder: 2,
								prompt: 'operation reference',
								style: 'this style3',
								label: 'origin of referral',
								additionalInstructions: 'more instructions2 ',
								inputField: inputFieldpw2
								).save(failOnError: true)
						
						
			
						def questionpw3 = new QuestionElement(
								designOrder: '3',
								prompt: 'this is the thirs question',
								style: 'this style5',
								label: 'what is your favorite colour ?',
								additionalInstructions: 'more instructions',
								inputField: inputFieldpw3
								).save(failOnError: true)
			
						def questionpw4 = new QuestionElement(
								designOrder: 4,
								prompt: 'this is the 4th question',
								style: 'this style5',
								label: 'what is your favorite animal ?',
								additionalInstructions: 'more instructions',
								inputField: inputFieldpw4
								).save(failOnError: true)
			
						def questionpw5 = new QuestionElement(
								designOrder: 5,
								prompt: 'this is the 5th question',
								style: 'this style5',
								label: 'what is your favorite car ?',
								additionalInstructions: 'more instructions',
								inputField: inputFieldpw5
								).save(failOnError: true)
			
			
						def formDesignPW = new FormDesign(
						name:'formDesignNamepw1',
						versionNo:'V0.145',
						isDraft:true,
						description:'test description 1'
						).save(failOnError: true)
			
			
						def sectionpw1 = new SectionElement(
								title: 'sectionpw1',
								designOrder: 1
								).save(failOnError:true)
			
						def sectionpw2 = new SectionElement(
								title: 'sectionpw2',
								designOrder: 2
								).save(failOnError:true)
			
						sectionpw1.addToQuestionElements(questionpw1)
						sectionpw1.addToQuestionElements(questionpw2)
						sectionpw1.addToQuestionElements(questionpw3)
			
			
						sectionpw2.addToRules(rulepw1)
						sectionpw2.addToQuestionElements(questionpw4)
						sectionpw2.addToQuestionElements(questionpw5)
			
						formDesignPW.addToFormDesignElements(sectionpw1)
						formDesignPW.addToFormDesignElements(sectionpw2)
						//End add form
						def collect1 = new Collection(name: 'TestCol11', description: 'blah blah blah').save(failOnError: true)
						def collect2 = new Collection(name: 'TestCol12', description: 'blah blah blah').save(failOnError: true)
						def collect3 = new Collection(name: 'TestCol13',description: 'blah blah blah').save(failOnError: true)
						def collect4 = new Collection(name: 'TestCol14', description: 'blah blah blah').save(failOnError: true)
						def collect5 = new Collection(name: 'TestCol15', description: 'blah blah blah').save(failOnError: true)
			
						def dec1 = new DataElementConcept(name: "Lung Cancer", description: "Cancers affecting the Lung").save(failOnError: true)
			
						def de11 = new DataElement(name:"SOURCE OF REFERRAL FOR OUT-PATIENTS", dataElementConcept: dec1).save(failOnError: true)
						def de12 = new DataElement(name:"ANOTHER SOURCE FOR OUT-PATIENTS", description:"This identifies the referral of each Consultant Out-Patient Episode.",dataElementConcept: dec1).save(failOnError: true)
			
						collect1.addToDataElementCollections(de11)
						collect1.addToDataElementCollections(de12)
						collect1.addToForms(formDesignPW)
			
						def dec2 = new DataElementConcept(name: "Pancreatic Cancer", description: "Cancers affecting the Lung").save(failOnError: true)
			
						def de21 = new DataElement(name:"SOURCE OF REFERRAL FOR OUT-PATIENTS", dataElementConcept: dec2).save(failOnError: true)
						def de22 = new DataElement(name:"ANOTHER SOURCE FOR OUT-PATIENTS", description:"This identifies the referral of each Consultant Out-Patient Episode.",dataElementConcept: dec2).save(failOnError: true)
			
						collect2.addToDataElementCollections(de21)
						collect2.addToDataElementCollections(de22)
			
						def dec3 = new DataElementConcept(name: "Diabetes", description: "Cancers affecting the Lung").save(failOnError: true)
			
						def de31 = new DataElement(name:"SOURCE OF REFERRAL FOR OUT-PATIENTS", dataElementConcept: dec3).save(failOnError: true)
						def de32 = new DataElement(name:"ANOTHER SOURCE FOR OUT-PATIENTS", description:"This identifies the referral of each Consultant Out-Patient Episode.",dataElementConcept: dec3).save(failOnError: true)
			
						collect3.addToDataElementCollections(de31)
						collect3.addToDataElementCollections(de32)
			
						def dec4 = new DataElementConcept(name: "Ovarian Cancer", description: "Cancers affecting the Lung").save(failOnError: true)
			
						def de41 = new DataElement(name:"SOURCE OF REFERRAL FOR OUT-PATIENTS", dataElementConcept: dec4).save(failOnError: true)
						def de42 = new DataElement(name:"ANOTHER SOURCE FOR OUT-PATIENTS", description:"This identifies the referral of each Consultant Out-Patient Episode.",dataElementConcept: dec4).save(failOnError: true)
			
						collect4.addToDataElementCollections(de41)
						collect4.addToDataElementCollections(de42)
			
						def dec5 = new DataElementConcept(name: "Advanced Breast Cancer", description: "Cancers affecting the Lung").save(failOnError: true)
			
						def de51 = new DataElement(name:"SOURCE OF REFERRAL FOR OUT-PATIENTS", dataElementConcept: dec5).save(failOnError: true)
						def de52 = new DataElement(name:"ANOTHER SOURCE FOR OUT-PATIENTS", description:"This identifies the referral of each Consultant Out-Patient Episode.",dataElementConcept: dec5).save(failOnError: true)
			
						collect5.addToDataElementCollections(de51)
						collect5.addToDataElementCollections(de52)
			
			
						def node11 = new Node(
								
								name: 'transfer to O.R.',
								x: '5px',
								y: '0px',
								description: 'transfer patient to the Operating Room',
								peCollection: collect1
								).save(flush:true)
			
						def de1 = new DataElement(name:"PERSON FAMILY NAME (AT BIRTH)",
						
						description:"The PATIENT's surname at birth.",
						dataElementConcept: dec1).save(failOnError: true)
			
						def node12 = new Node(
								
								name: 'Anaesthesia and Operating Patient.',
								x: '115px',
								y: '110px',
								description: 'perform the operation',
								peCollection: collect2
								).save(flush:true)
			
			
						def node13 = new Node(
								
								name: 'Guarding Patient on recovery and transfer to nursing ward',
								x: '325px',
								y: '330px',
								description: 'transfer patient to the Operating Room',
								peCollection: collect3
								).save(flush:true)
			
			
			
						def link21 = new Link(
								
								name: 'TM21',
								source: node11,
								target: node12,
								peCollection: collect4
								).save(flush:true)
			
						def link22 = new Link(
								
								name: 'TM22',
								source: node12,
								target: node13,
								peCollection: collect5
								).save(flush:true)
			
			
						def pathway2 = new PathwaysModel(
								name: 'Transplanting and Monitoring Pathway',
								versionNo: '0.2',
								isDraft: true
						)
						pathway2.addToPathwayElements(node11)
						pathway2.addToPathwayElements(node12)
						pathway2.addToPathwayElements(node13)
						pathway2.addToPathwayElements(link21)
						pathway2.addToPathwayElements(link22)
						pathway2.save(flush:true)

		}		
	}
	


	
}
	
