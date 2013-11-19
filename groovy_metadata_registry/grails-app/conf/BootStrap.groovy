import uk.co.mdc.*
import uk.co.mdc.forms.*
import uk.co.mdc.model.SchemaSpecification;
import uk.co.mdc.model.Collection;
import uk.co.mdc.model.ExternalReference
import uk.co.mdc.model.ValueDomain
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.DataType
import uk.co.mdc.model.DataElementConcept
import uk.co.mdc.model.DataElementCollection
import uk.co.mdc.model.ConceptualDomain
import uk.co.mdc.model.Document
import uk.co.mdc.model.DataElementValueDomain
import uk.co.mdc.pathways.PathwaysModel
import uk.co.mdc.pathways.Link
import uk.co.mdc.pathways.Node

import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.commons.ApplicationHolder

import grails.util.Environment
import grails.util.DomainBuilder
import groovy.json.JsonSlurper

import org.springframework.web.context.support.WebApplicationContextUtils

import static org.springframework.security.acls.domain.BasePermission.ADMINISTRATION
import static org.springframework.security.acls.domain.BasePermission.DELETE
import static org.springframework.security.acls.domain.BasePermission.READ
import static org.springframework.security.acls.domain.BasePermission.WRITE

import org.springframework.security.authentication. UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.grails.plugins.csv.CSVMapReader
import org.json.simple.JSONObject


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

		if(!SecUser.findByUsername('user1') ){
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


	private registerSpringFilters(){

		SpringSecurityUtils.clientRegisterFilter('apiAuthFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 10)

	}

	private registerJSONMarshallers(springContext) {

		//register custom marshallers

		springContext.getBean('customObjectMarshallers').register()
	}

	private void createUsers() {
		def roleUser = SecAuth.findByAuthority('ROLE_USER') ?: new SecAuth(authority: 'ROLE_USER').save(failOnError: true)
		def roleAdmin = SecAuth.findByAuthority('ROLE_ADMIN') ?: new SecAuth(authority: 'ROLE_ADMIN').save(failOnError: true)


		if(!SecUser.findByUsername('user1') ){
			3.times {
				long id = it + 1
				def user = new SecUser(username: "user$id", enabled: true, password: "password$id").save(failOnError: true)
				SecUserSecAuth.create user, roleUser
			}
		}

		def admin = SecUser.findByUsername('admin') ?: new SecUser(username: 'admin', enabled: true, password: 'admin123').save(failOnError: true)

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

		// grant admin admin on everything

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
		grantAdminPermissions(PathwaysModel.list())

	}


	def grantAdminPermissions(objectList){

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


			def formDesignInstance = new FormDesign(refId: 'testForm1',
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


			//def collect1 = new Collection(refId: 'Colt11', name: 'TestCol11', description: 'blah blah blah').save(failOnError: true)
			//def collect2 = new Collection(refId: 'Colt12', name: 'TestCol12', description: 'blah blah blah').save(failOnError: true)
			//def collect3 = new Collection(refId: 'Colt14', name: 'TestCol13',description: 'blah blah blah').save(failOnError: true)
			//def collect4 = new Collection(refId: 'Colt15', name: 'TestCol14', description: 'blah blah blah').save(failOnError: true)
			//def collect5 = new Collection(refId: 'Colt16', name: 'TestCol15', description: 'blah blah blah').save(failOnError: true)

			//def de21 = new DataElement(name:"SOURCE OF REFERRAL FOR OUT-PATIENTS", refId:"D1600",description:"This identifies the source of referral of each Consultant Out-Patient Episode.", dataElementConcept: REF).save(failOnError: true)
			//def de22 = new DataElement(name:"ANOTHER SOURCE FOR OUT-PATIENTS", refId:"E1600",description:"This identifies the referral of each Consultant Out-Patient Episode.",dataElementConcept: REF).save(failOnError: true)

			//collect1.addToDataElementCollections(de21)
			//collect1.addToDataElementCollections(de22)


			def node1 = new Node(
					refId: 'TM_N1',
					name: 'transfer to O.R.',
					x: '5',
					y: '0',
					description: 'transfer patient to the Operating Room',
					//peCollection: collect1
					).save(flush:true)

			/*def de1 = new DataElement(name:"PERSON FAMILY NAME (AT BIRTH)",
			 refId:"CR0111",
			 description:"The PATIENT's surname at birth.",
			 dataElementConcept: DEM).save(failOnError: true)
			 */
			//println(" Collection" + de1.refId)


			def node2 = new Node(
					refId: 'TM_N2',
					name: 'Anaesthesia and Operating Patient.',
					x: '15',
					y: '10',
					description: 'perform the operation',
					//	peCollection: collect2
					).save(flush:true)


			def node3 = new Node(
					refId: 'TM_N3',
					name: 'Guarding Patient on recovery and transfer to nursing ward',
					x: '25',
					y: '30',
					description: 'transfer patient to the Operating Room',
					//peCollection: collect3
					).save(flush:true)



			def link1 = new Link(
					refId: 'TM_L1',
					name: 'TM1',
					source: node1,
					target: node2,
					//peCollection: collect4
					).save(flush:true)

			def link2 = new Link(
					refId: 'TM_L2',
					name: 'TM2',
					source: node2,
					target: node3,
					//peCollection: collect5
					).save(flush:true)


			def pathway = new PathwaysModel(
					refId: 'TM_P1',
					name: 'Transplanting and Monitoring Pathway',
					versionNo: '0.1',
					isDraft: true
					)


			pathway.addToPathwayElements(node1)
			pathway.addToPathwayElements(node2)
			pathway.addToPathwayElements(node3)
			pathway.addToPathwayElements(link1)
			pathway.addToPathwayElements(link2)
			pathway.save(flush:true)

		}


		if(Environment.current != Environment.DEVELOPMENT){
			importNHICData(basePath)
		}
		
	}
	


	private importNHICData(basePath){
		
		
		NHICImportConfig.functions.keySet().each { filename -> 
			new File("${basePath}" + filename).toCsvReader([charset:'UTF-8', skipLines : 1] ).eachLine { tokens ->
				NHICImportConfig.functions[filename](tokens);
			}
		}

	}
	
}
	
