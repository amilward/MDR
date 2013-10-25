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
import uk.co.mdc.model.DataElementValueDomain
import uk.co.mdc.pathways.PathwaysModel
import uk.co.mdc.pathways.Link
import uk.co.mdc.pathways.Node

import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.commons.ApplicationHolder

import grails.util.DomainBuilder

import org.springframework.web.context.support.WebApplicationContextUtils

import static org.springframework.security.acls.domain.BasePermission.ADMINISTRATION
import static org.springframework.security.acls.domain.BasePermission.DELETE
import static org.springframework.security.acls.domain.BasePermission.READ
import static org.springframework.security.acls.domain.BasePermission.WRITE

import org.springframework.beans.factory.parsing.ImportDefinition;
import org.springframework.security.authentication. UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.grails.plugins.csv.CSVMapReader


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
		/*100.times {
		 long id = it + 1
		 def dataElement = new DataElement(refId: "reference$id", name: "dataElement$id", description: 'test').save(failOnError:true)
		 dataElements << dataElement
		 aclService.createAcl( objectIdentityRetrievalStrategy.getObjectIdentity(dataElement))
		 }
		 // grant user 1 admin on 11,12 and read on 1-67
		 aclUtilService.addPermission dataElements[10], 'user1', ADMINISTRATION
		 aclUtilService.addPermission dataElements[11], 'user1', ADMINISTRATION
		 67.times {
		 aclUtilService.addPermission dataElements[it], 'user1', READ
		 }
		 // grant user 2 read on 1-5, write on 5
		 5.times {
		 aclUtilService.addPermission dataElements[it], 'user2', READ
		 }
		 aclUtilService.addPermission dataElements[4], 'user2', WRITE*/

		// user 3 has no grants

		// grant admin admin on everything

		grantAdminPermissions(DataElement.list())
		grantAdminPermissions(ValueDomain.list())
		grantAdminPermissions(DataElementConcept.list())
		grantAdminPermissions(DataType.list())
		grantAdminPermissions(ExternalReference.list())
		grantAdminPermissions(Collection.list())
		grantAdminPermissions(FormDesign.list())
		grantAdminPermissions(QuestionElement.list())
		grantAdminPermissions(InputField.list())

		// grant user 1 ownership on 1,2 to allow the user to grant
		//aclUtilService.changeOwner dataElements[0], 'user1'
		//aclUtilService.changeOwner dataElements[1], 'user1'
	}


	def grantAdminPermissions(objectList){

		for (object in objectList) {
			println object
			aclUtilService.addPermission object, 'admin', ADMINISTRATION
		}

	}

	def destroy = {
	}


	/*
	 *  **********************POPULATE WITH FORMS TEST DATA********************************
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

		
		new File("${basePath}/WEB-INF/bootstrap-data/NHIC/CAN/CAN.csv").toCsvReader(['charset':'UTF-8', skipLines : 1] ).eachLine { tokens ->
			importCSVLine(tokens, null);
		}
		
		
		
		/*def dataElementConcepts = new XmlSlurper().parse( new File("${basePath}/WEB-INF/bootstrap-data/NHIC/CAN/CAM.xml"))
		dataElementConcepts.dataElementConcept.each() { xmldec ->
			importDataElementConcept(xmldec, null);
		} */
			



		/*if (!PathwaysModel.count()) {
		 def pathways = new XmlSlurper().parse( new File("${basePath}/WEB-INF/bootstrap-data/Pathway.xml"))
		 pathways.pathway_model.each() { p ->
		 new PathwaysModel(p.attributes()).save(failOnError: true) //assumes the keys match the Pathway properties
		 }
		 }*/



		if (!ConceptualDomain.count()) {
			ConceptualDomain COSD = new ConceptualDomain(name:"COSD", refId:1, description:"Cancer Outcomes and Services Dataset").save(failOnError: true)

			if (!DataElementConcept.count()) {
				DataElementConcept CORE  = new DataElementConcept(name:"CORE", refId:"CORE", description:"CORE data set").save(failOnError: true)
				DataElementConcept HAEMA = new DataElementConcept(name:"HAEMATOLOGY", refId:"HAEMATOLOGY", description:"HAEMATOLOGY data set").save(failOnError: true)
				new DataElementConcept(name:"CORE - DIAGNOSTIC DETAILS", refId:"DIAGNOSTIC DETAILS", parent: CORE).save(failOnError: true)
				new DataElementConcept(name:"CORE - PATIENT IDENTITY DETAILS", refId:"PATIENT IDENTITY DETAILS", parent: CORE).save(failOnError: true)
				DataElementConcept DEM = new DataElementConcept(name:"CORE - DEMOGRAPHICS", refId:"DEMOGRAPHICS", parent: CORE).save(failOnError: true)
				DataElementConcept REF = new DataElementConcept(name:"CORE - REFERRALS", refId:"REFERRALS", parent: CORE).save(failOnError: true)
				new DataElementConcept(name:"CORE - IMAGING", refId:"IMAGING", parent: CORE).save(failOnError: true)
				new DataElementConcept(name:"CORE - DIAGNOSIS", refId:"DIAGNOSIS", parent: CORE).save(failOnError: true)
				new DataElementConcept(name:"CORE - CANCER CARE PLAN", refId:"CANCER CARE PLAN", parent: CORE).save(failOnError: true)
				new DataElementConcept(name:"CORE - CLINICAL TRIALS", refId:"CLINICAL TRIALS", parent: CORE).save(failOnError: true)
				new DataElementConcept(name:"CORE - STAGING", refId:"STAGING", parent: CORE).save(failOnError: true)



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

					Map enthicCat = [	"A":"(White) British",
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

					DataType string = new DataType(name:"String", enumerated: false).save(failOnError: true)
					new DataType(name:"Text", enumerated: false).save(failOnError: true)
					new DataType(name:"Integer", enumerated: false).save(failOnError: true)
					new DataType(name:"Date", enumerated: false).save(failOnError: true)
					new DataType(name:"DateTime", enumerated: false).save(failOnError: true)
					new DataType(name:"Time", enumerated: false).save(failOnError: true)
					new DataType(name:"Float", enumerated: false).save(failOnError: true)
					new DataType(name:"Boolean", enumerated: false).save(failOnError: true)
					new DataType(name:"Blob", enumerated: false).save(failOnError: true)

					if (!DataElement.count()&&!ValueDomain.count()) {

						DataElementValueDomain.link(new DataElement(name:"SOURCE OF REFERRAL FOR OUT-PATIENTS",
						refId:"C1600",
						description:"This identifies the source of referral of each Consultant Out-Patient Episode.",
						dataElementConcept: REF).save(failOnError: true),
						new ValueDomain(name:"NHS SOURCE OF REFERRAL FOR OUT-PATIENTS",
						refId:"C1600",
						description:"",
						dataType: OP_REF,
						conceptualDomain: COSD,
						format:"an2").save(failOnError: true))


						DataElementValueDomain.link(new DataElement(name:"ETHNIC CATEGORY",
						refId:"CR0150",
						description:"The ethnicity of a PERSON, as specified by the PERSON.. The 16+1 ethnic data categories defined in the 2001 census is the national mandatory standard for the collection and analysis of ethnicity.(The Office for National Statistics has developed a further breakdown of the group from that given, which may be used locally.)",
						dataElementConcept: DEM).save(failOnError: true),
						new ValueDomain(name:"NHS ETHNIC CATEGORY",
						refId:"CR0150",
						description:"",
						dataType: ETH_CAT,
						conceptualDomain: COSD,
						format:"an2").save(failOnError: true))

						DataElementValueDomain.link(new DataElement(name:"PERSON FAMILY NAME (AT BIRTH)",
						refId:"CR0140",
						description:"The PATIENTs surname at birth.",
						dataElementConcept: DEM).save(failOnError: true),
						new ValueDomain(name:"NHS PERSON FAMILY NAME (AT BIRTH)",
						refId:"CR0140",
						description:"",
						dataType: string,
						conceptualDomain: COSD,
						format:"max 35 characters").save(failOnError: true))

						DataElementValueDomain.link(new DataElement(name:"GENERAL MEDICAL PRACTICE CODE (PATIENT REGISTRATION)",
						refId:"CR0120",
						description:"The GENERAL MEDICAL PRACTICE CODE (PATIENT REGISTRATION) is an ORGANISATION CODE. This is the code of the GP Practice that the PATIENT is registered with.",
						dataElementConcept: DEM).save(failOnError: true),
						new ValueDomain(name:"NHS GENERAL MEDICAL PRACTICE CODE (PATIENT REGISTRATION)",
						refId:"CR0120",
						description:"",
						dataType: string,
						conceptualDomain: COSD,
						format:"an6").save(failOnError: true))


						def dataElement = DataElement.findByRefId("CR0120")
						DataElementCollection.link(dataElement,
								new Collection(refId: 'Col1',
								name: 'TestCol',
								description: 'blah blah blah').save(failOnError: true), SchemaSpecification.MANDATORY)


						//populate with forms data

						if(!FormDesign.count()){

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
									dataType: OP_REF,
									format: 'test format2',

									).save(failOnError: true)

							def inputField3 = new InputField(

									defaultValue: 'te3st default',
									placeholder: 'test3 placeholder',
									maxCharacters: 13,
									unitOfMeasure: 'tes3t UOM',
									dataType: string,
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

							def question1  = new QuestionElement(
									questionNumber: '1',
									prompt: 'this is the first question',
									style: 'this style1',
									label: 'is this really a label?',
									additionalInstructions: 'more instructions',
									inputField: inputField1
									).save(failOnError: true)

							def question2  = new QuestionElement(
									questionNumber: '2',
									prompt: 'operation reference',
									style: 'this style3',
									label: 'origin of referral',
									additionalInstructions: 'more instructions2 ',
									inputField: inputField2
									).save(failOnError: true)

							def question3  = new QuestionElement(
									questionNumber: '3',
									prompt: 'this is the thirs question',
									style: 'this style5',
									label: 'what is your favorite colour ?',
									additionalInstructions: 'more instructions',
									inputField: inputField3
									).save(failOnError: true)

							def question4  = new QuestionElement(
									questionNumber: '4',
									prompt: 'this is the 4th question',
									style: 'this style5',
									label: 'what is your favorite animal ?',
									additionalInstructions: 'more instructions',
									inputField: inputField4
									).save(failOnError: true)

							def question5  = new QuestionElement(
									questionNumber: '5',
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
									title: 'section1'
									).save(failOnError:true)

							def section2 = new SectionElement(
									title: 'section2'
									).save(failOnError:true)

							section1.addToQuestionElements(question1)
							section1.addToQuestionElements(question2)
							section1.addToQuestionElements(question3)

							section2.addToQuestionElements(question4)
							section2.addToQuestionElements(question5)

							formDesignInstance.addToFormDesignElements(section1)
							formDesignInstance.addToFormDesignElements(section2)
						}


						//add a pathway
						//Node(String ref, String nm,String x, String y, String desc, Collection col)

						if(!PathwaysModel.count()){


							def collect1 = new Collection(refId: 'Colt11', name: 'TestCol11', description: 'blah blah blah').save(failOnError: true)
							def collect2 = new Collection(refId: 'Colt12', name: 'TestCol12', description: 'blah blah blah').save(failOnError: true)
							def collect3 = new Collection(refId: 'Colt14', name: 'TestCol13',description: 'blah blah blah').save(failOnError: true)
							def collect4 = new Collection(refId: 'Colt15', name: 'TestCol14', description: 'blah blah blah').save(failOnError: true)
							def collect5 = new Collection(refId: 'Colt16', name: 'TestCol15', description: 'blah blah blah').save(failOnError: true)

							def de21 = new DataElement(name:"SOURCE OF REFERRAL FOR OUT-PATIENTS", refId:"D1600",description:"This identifies the source of referral of each Consultant Out-Patient Episode.", dataElementConcept: REF).save(failOnError: true)
							def de22 = new DataElement(name:"ANOTHER SOURCE  FOR OUT-PATIENTS", refId:"E1600",description:"This identifies the  referral of each Consultant Out-Patient Episode.",dataElementConcept: REF).save(failOnError: true)

							collect1.addToDataElementCollections(de21)
							collect1.addToDataElementCollections(de22)





							println(" Collection" + collect1.refId)
							println(" Collection" + collect2.refId)
							println(" Collection" + collect3.refId)
							println(" Collection" + collect4.refId)
							println(" Collection" + collect5.refId)

							def node1 = new Node(
									refId: 'TM_N1',
									name: 'transfer to O.R.',
									x: '5',
									y: '0',
									description: 'transfer patient to the Operating Room',
									peCollection:  collect1
									).save(flush:true)

							def de1 = new DataElement(name:"PERSON FAMILY NAME (AT BIRTH)",
							refId:"CR0111",
							description:"The PATIENT's surname at birth.",
							dataElementConcept: DEM).save(failOnError: true)

							println(" Collection" + de1.refId)


							def node2 = new Node(
									refId: 'TM_N2',
									name: 'Anaesthesia and Operating Patient.',
									x: '15',
									y: '10',
									description: 'perform the operation',
									peCollection:  collect2
									).save(flush:true)


							def node3 = new Node(
									refId: 'TM_N3',
									name: 'Guarding Patient on recovery and transfer to nursing ward',
									x: '25',
									y: '30',
									description: 'transfer patient to the Operating Room',
									peCollection:  collect3
									).save(flush:true)



							def link1 = new Link(
									refId: 'TM_L1',
									name: 'TM1',
									source: node1,
									target: node2,
									peCollection:  collect4
									).save(flush:true)

							def link2 = new Link(
									refId: 'TM_L2',
									name: 'TM2',
									source: node2,
									target: node3,
									peCollection: collect5
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

					}

				}

			}
		}
	}

	private importCSVLine(tokens, parent)
	{
		// if the DEC for tokens[1] doesn't exist, create it.
		def section = DataElementConcept.findAllWhere("name" : tokens[1], "parent" : parent) 
		
		if(section.empty){
			section = new DataElementConcept(name : tokens[1], parent : parent, dataElements: []).save(failOnError: true)
			println tokens[1]
		}
		else{
			section = section.first()
		}
		def subsection = DataElementConcept.findAllWhere("name" : tokens[2], "parent" : section.first())
		if(subsection.empty){
			subsection = new DataElementConcept(name : tokens[2], parent : section.first(), dataElements: []).save(failOnError: true)
			println tokens[2]
		}
		else{
			subsection = subsection.first()
		}

		def de = new DataElement(refId : tokens[0], name: tokens[3], description : tokens[4], dataElementConcept: subsection ).save(failOnError: true)
/*		subsection.dataElements.add(de)
		subsection.save(failOnError: true)
		de.save(failOnError: true) */

	}
	
	

	private importDataElementConcept(xmldec, parent)
	{
		if(parent != null)
		{
			xmldec.attributes().putAt("parent", parent);
		}
		def dec = new DataElementConcept(xmldec.attributes()).save(failOnError: true) //assumes the keys match the DataElementConcept properties
		//dec.dataElements = [];
		xmldec.subConcepts.dataElementConcept.each { xmldec2 ->
			importDataElementConcept(xmldec2, dec);
		}
	}
	
	
	
	
}


