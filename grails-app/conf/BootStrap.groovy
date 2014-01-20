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
import org.springframework.security.acls.domain.BasePermission


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
                createBaseRoles()
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
    /**
     * Adds the requisite roles to the system, unless they are aleady present.
     */
    private createBaseRoles(){
        SecAuth.findByAuthority('ROLE_ADMIN') ?: new SecAuth(authority: 'ROLE_ADMIN').save(failOnError: true)
        SecAuth.findByAuthority('ROLE_PENDING') ?: new SecAuth(authority: 'ROLE_PENDING').save(failOnError: true)
        SecAuth.findByAuthority('ROLE_USER') ?: new SecAuth(authority: 'ROLE_USER').save(failOnError: true)
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

        def roleAdmin = SecAuth.findByAuthority('ROLE_ADMIN') ?: new SecAuth(authority: 'ROLE_ADMIN').save(failOnError: true)
        def rolePending = SecAuth.findByAuthority('ROLE_PENDING') ?: new SecAuth(authority: 'ROLE_PENDING').save(failOnError: true)
        def roleUser = SecAuth.findByAuthority('ROLE_USER') ?: new SecAuth(authority: 'ROLE_USER').save(failOnError: true)

		def roleUCL = SecAuth.findByAuthority('ROLE_UCL') ?: new SecAuth(authority: 'ROLE_UCL').save(failOnError: true)
		def roleOxford = SecAuth.findByAuthority('ROLE_OXFORD') ?: new SecAuth(authority: 'ROLE_OXFORD').save(failOnError: true)
		def roleCambridge = SecAuth.findByAuthority('ROLE_CAMBRIDGE') ?: new SecAuth(authority: 'ROLE_CAMBRIDGE').save(failOnError: true)
		def roleImperial = SecAuth.findByAuthority('ROLE_IMPERIAL') ?: new SecAuth(authority: 'ROLE_IMPERIAL').save(failOnError: true)
		def roleGST = SecAuth.findByAuthority('ROLE_GST') ?: new SecAuth(authority: 'ROLE_GST').save(failOnError: true)



		if(!SecUser.findByUsername('user1') ){
			def user = new SecUser(username: "user1", enabled: true, emailAddress: "user1@example.org", password: "password1").save(failOnError: true)
			SecUserSecAuth.create user, roleUser			
		}
		
		if(!SecUser.findByUsername('ucl1') ){	
			def user = new SecUser(username: "ucl1", enabled: true, emailAddress: "ucl1@example.org", password: "password1").save(failOnError: true)
			SecUserSecAuth.create user, roleUser
			SecUserSecAuth.create user, roleUCL
			
		}
		
		if(!SecUser.findByUsername('oxford1') ){
			def user = new SecUser(username: "oxford1", enabled: true, emailAddress: "oxford1@example.org", password: "password1").save(failOnError: true)
			SecUserSecAuth.create user, roleUser
			SecUserSecAuth.create user, roleOxford
		}
		
		if(!SecUser.findByUsername('oxford2') ){
			def user = new SecUser(username: "oxford2", enabled: true, emailAddress: "oxford2@example.org", password: "password2").save(failOnError: true)
			SecUserSecAuth.create user, roleUser
			SecUserSecAuth.create user, roleOxford
		}
		
		if(!SecUser.findByUsername('cambridge1') ){
			def user = new SecUser(username: "cambridge1", enabled: true, emailAddress: "cambridge1@example.org", password: "password1").save(failOnError: true)
			SecUserSecAuth.create user, roleUser
			SecUserSecAuth.create user, roleCambridge
		}
		
		if(!SecUser.findByUsername('cambridge2') ){
			def user = new SecUser(username: "cambridge2", enabled: true, emailAddress: "cambridge2@example.org", password: "password2").save(failOnError: true)
			SecUserSecAuth.create user, roleUser
			SecUserSecAuth.create user, roleCambridge
		}
		
		if(!SecUser.findByUsername('imperial1') ){
			def user = new SecUser(username: "imperial1", enabled: true, emailAddress: "imperial1@example.org", password: "password1").save(failOnError: true)
			SecUserSecAuth.create user, roleUser
			SecUserSecAuth.create user, roleImperial
		}
		
		if(!SecUser.findByUsername('gstUser1') ){
			def user = new SecUser(username: "gstUser1", enabled: true, emailAddress: "gstUser1@example.org", password: "password1").save(failOnError: true)
			SecUserSecAuth.create user, roleUser
			SecUserSecAuth.create user, roleGST
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
			aclUtilService.addPermission object, 'ROLE_ADMIN', BasePermission.ADMINISTRATION
			
		}
	}
	
	
	def grantUserPermissions(objectList){
		for (object in objectList) {
			//FIX me - by default user will have the 
			aclUtilService.addPermission object, 'ROLE_USER', BasePermission.READ

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
		def DEM
		def string
		
		def date
		
		
		if (!ConceptualDomain.count()) {
			ConceptualDomain COSD = new ConceptualDomain(name:"TESTDOMAIN", description:"Cancer Outcomes and Services Dataset").save(failOnError: true)

			if (!DataElementConcept.count()) {
					DataElementConcept CORE = new DataElementConcept(name:"CORE", description:"CORE data set").save(failOnError: true)
					DataElementConcept HAEMA = new DataElementConcept(name:"HAEMATOLOGY", description:"HAEMATOLOGY data set").save(failOnError: true)
					new DataElementConcept(name:"CORE - DIAGNOSTIC DETAILS", parent: CORE).save(failOnError: true)
					new DataElementConcept(name:"CORE - PATIENT IDENTITY DETAILS", parent: CORE).save(failOnError: true)
					DEM = new DataElementConcept(name:"CORE - DEMOGRAPHICS",  parent: CORE).save(failOnError: true)
					DataElementConcept REF = new DataElementConcept(name:"CORE - REFERRALS", parent: CORE).save(failOnError: true)
					new DataElementConcept(name:"CORE - IMAGING", parent: CORE).save(failOnError: true)
					new DataElementConcept(name:"CORE - DIAGNOSIS", parent: CORE).save(failOnError: true)
					new DataElementConcept(name:"CORE - CANCER CARE PLAN", parent: CORE).save(failOnError: true)
					new DataElementConcept(name:"CORE - CLINICAL TRIALS", parent: CORE).save(failOnError: true)
					new DataElementConcept(name:"CORE - STAGING", parent: CORE).save(failOnError: true)



					if (!DataType.count()) {


							//declare cancer enumerated data types....to get things going
							Map nhsStatusEnumeration = ["01": "Number present and verified",
									"02": "Number present but not traced",
									"03": "Trace required", "04": "Trace attempted - No match or multiple match found",
									"05": "Trace needs to be resolved - (NHS Number or patient detail conflict)",
									"06": "Trace in progress" ,
									"07": "Number not present and trace not required",
									"08": "Trace postponed (baby under six weeks old)"]

							new DataType(name:"NHS NUMBER STATUS INDICATOR", enumerated: true, enumerations: nhsStatusEnumeration).save(failOnError: true)

							Map genderCode = ["0": "Not Known", "1": "Male", "2": "Female", "9": "Not Specified"]

							new DataType(name:"NHS PERSON GENDER", enumerated: true, enumerations: genderCode).save(failOnError: true)

							Map enthicCat = [ "A":"(White) British",
									"B":"(White) Irish",
									"C":"Any other White background",
									"D":"White and Black Caribbean",
									"E":"White and Black African",
									"F":"White and Asian",
									"G":"Any other mixed background",
									"H":"Indian",
									"J":"Pakistani",
									"K":"Bangladeshi",
									"L":"Any other Asian background",
									"M":"Caribbean",
									"N":"African",
									"P":"Any other Black background",
									"R":"Chinese",
									"S":"Any other ethnic group",
									"Z":"Not stated",
									"99":"Not Known"]


							DataType ETH_CAT = new DataType(name:"NHS ETHIC CATEGORY", enumerated: true, enumerations: enthicCat).save(failOnError: true)

							Map referralSource = [
									"01":"following an emergency admission",
									"02":"following a Domiciliary Consultation",
									"10":"following an Accident And Emergency Attendance (including Minor Injuries Units and Walk In Centres)",
									"11":"other - initiated by the CONSULTANT responsible for the Consultant Out-Patient Episode",
									"03":"referral from a GENERAL MEDICAL PRACTITIONER",
									"92":"referral from a GENERAL DENTAL PRACTITIONER",
									"12":"referral from a GENERAL PRACTITIONER with a Special Interest (GPwSI) or dentist with a Special Interest (DwSI)",
									"04":"referral from an Accident And Emergency Department (including Minor Injuries Units and Walk In Centres)",
									"05":"referral from a CONSULTANT other than in an Accident And Emergency Department",
									"06":"self-referral",
									"07":"referral from a Prosthetist",
									"13":"referral from a Specialist NURSE (Secondary Care)",
									"14":"referral from an Allied Health Professional",
									"15":"referral from an OPTOMETRIST",
									"16":"referral from an Orthoptist",
									"17":"referral from a National Screening Programme",
									"93":"referral from a Community Dental Service",
									"97":"other - not initiated by the CONSULTANT responsible for the Consultant Out-Patient Episode"]

							DataType OP_REF = new DataType(name:"NHS SOURCE OUT-PATIENT REFERRAL ", enumerated: true, enumerations: referralSource).save(failOnError: true)


							//declare normal data types

							string = new DataType(name:"String", enumerated: false).save(failOnError: true)
							new DataType(name:"Text", enumerated: false).save(failOnError: true)
							new DataType(name:"Integer", enumerated: false).save(failOnError: true)
							date = new DataType(name:"Date", enumerated: false).save(failOnError: true)
							new DataType(name:"Datetime", enumerated: false).save(failOnError: true)
							new DataType(name:"Time", enumerated: false).save(failOnError: true)
							new DataType(name:"Float", enumerated: false).save(failOnError: true)
							new DataType(name:"Boolean", enumerated: false).save(failOnError: true)
							new DataType(name:"Blob", enumerated: false).save(failOnError: true)

							if (!DataElement.count()&&!ValueDomain.count()) {

									DataElementValueDomain.link(new DataElement(name:"SOURCE OF REFERRAL FOR OUT-PATIENTS",
									description:"This identifies the source of referral of each Consultant Out-Patient Episode.",
									dataElementConcept: REF).save(failOnError: true),
									new ValueDomain(name:"NHS SOURCE OF REFERRAL FOR OUT-PATIENTS",
									description:"",
									dataType: OP_REF,
									conceptualDomain: COSD,
									format:"an2").save(failOnError: true))


									DataElementValueDomain.link(new DataElement(name:"ETHNIC CATEGORY",
									description:"The ethnicity of a PERSON, as specified by the PERSON.. The 16+1 ethnic data categories defined in the 2001 census is the national mandatory standard for the collection and analysis of ethnicity.(The Office for National Statistics has developed a further breakdown of the group from that given, which may be used locally.)",
									dataElementConcept: DEM).save(failOnError: true),
									new ValueDomain(name:"NHS ETHNIC CATEGORY",
									description:"",
									dataType: ETH_CAT,
									conceptualDomain: COSD,
									format:"an2").save(failOnError: true))

									DataElementValueDomain.link(new DataElement(name:"PERSON FAMILY NAME (AT BIRTH)",
									description:"The PATIENTs surname at birth.",
									dataElementConcept: DEM).save(failOnError: true),
									new ValueDomain(name:"NHS PERSON FAMILY NAME (AT BIRTH)",
									description:"",
									dataType: string,
									conceptualDomain: COSD,
									format:"max 35 characters").save(failOnError: true))

									DataElementValueDomain.link(new DataElement(name:"GENERAL MEDICAL PRACTICE CODE (PATIENT REGISTRATION)",
									description:"The GENERAL MEDICAL PRACTICE CODE (PATIENT REGISTRATION) is an ORGANISATION CODE. This is the code of the GP Practice that the PATIENT is registered with.",
									dataElementConcept: DEM).save(failOnError: true),
									new ValueDomain(name:"NHS GENERAL MEDICAL PRACTICE CODE (PATIENT REGISTRATION)",
									description:"",
									dataType: string,
									conceptualDomain: COSD,
									format:"an6").save(failOnError: true))


									

							}
							
							def dataElement = DataElement.get(1);
							
							DataElementCollection.link(dataElement,
								new Collection(refId: 'Col1',
								name: 'TestCol',
								description: 'blah blah blah').save(failOnError: true), SchemaSpecification.MANDATORY)



							
							}
					}
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
				name: 'Guarding Patient on recovery and transfer to nursing ward',
				versionNo: '0.1',
				isDraft: true
				).save(failOnError:true)
				
			def node1 = new Node(
					
					name: 'Guard Patient',
					x: '5px',
					y: '0px',
					description: 'guard patient on recovery'
					).save(flush:true)

			def node2 = new Node(
					
					name: 'Recovery',
					x: '150px',
					y: '100px',
					description: 'recover'
					).save(flush:true)

			def node3 = new Node(
					
					name: 'Transfer to nursing ward',
					x: '250px',
					y: '300px',
					description: 'transfer patient to the nursing ward'
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
			
			
						def node11 = new Node(
								
								name: 'transfer to O.R.',
								x: '5px',
								y: '0px',
								description: 'transfer patient to the Operating Room',
								).save(flush:true)
			
			
						def node12 = new Node(
								
								name: 'Anaesthesia and Operating Patient.',
								x: '115px',
								y: '110px',
								description: 'perform the operation',
								).save(flush:true)
			
			
						def node13 = new Node(
								
								name: 'Guarding Patient on recovery and transfer to nursing ward',
								x: '325px',
								y: '330px',
								description: 'transfer patient to the Operating Room',
								subModel: pathway1
								).save(flush:true)
			
			
			
						def link21 = new Link(
								
								name: 'TM21',
								source: node11,
								target: node12
								).save(flush:true)
			
						def link22 = new Link(
								
								name: 'TM22',
								source: node12,
								target: node13,
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
	
