import uk.co.mdc.*
import uk.co.mdc.model.ExternalReference
import uk.co.mdc.model.ValueDomain
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.DataType
import uk.co.mdc.model.DataElementConcept
import uk.co.mdc.model.ConceptualDomain
import uk.co.mdc.model.DataElementValueDomain
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import grails.util.DomainBuilder
import org.springframework.web.context.support.WebApplicationContextUtils

import static org.springframework.security.acls.domain.BasePermission.ADMINISTRATION
import static org.springframework.security.acls.domain.BasePermission.DELETE
import static org.springframework.security.acls.domain.BasePermission.READ
import static org.springframework.security.acls.domain.BasePermission.WRITE

import org.springframework.security.authentication. UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder as SCH



class BootStrap {

	def aclService
	def aclUtilService
	def objectIdentityRetrievalStrategy
	def sessionFactory
	def springSecurityService
	
    def init = { servletContext ->
		
		def springContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )
		
		//register custom json Marshallers
		registerJSONMarshallers(springContext)
		
		//register spring filters (in this case the rest api security filter)
		registerSpringFilters()
		
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
		100.times {
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
		
		aclUtilService.addPermission dataElements[4], 'user2', WRITE
		
		// user 3 has no grants
		
		// grant admin admin on everything 
		
		grantAdminPermissions(DataElement.list())
		grantAdminPermissions(ValueDomain.list())
		grantAdminPermissions(DataElementConcept.list())
		grantAdminPermissions(DataType.list())
	
		// grant user 1 ownership on 1,2 to allow the user to grant
		aclUtilService.changeOwner dataElements[0], 'user1'
		aclUtilService.changeOwner dataElements[1], 'user1'
		}
	
	
	def grantAdminPermissions(objectList){
		
		for (object in objectList) {
			aclUtilService.addPermission object, 'admin', ADMINISTRATION
		}
		
	}
		
    def destroy = {
    }
	
	
	
	/*
	 *  ******************************************************
	 * 
	 * */

	private populateWithTestData(){
		
		
		//populate with test data
		
		if (!ExternalReference.count()) {
			new ExternalReference(name:"test external reference 1", url:"www.testSite1.com").save(failOnError: true)
			new ExternalReference(name:"test external reference 2", url:"www.testSite2.com").save(failOnError: true)
			new ExternalReference(name:"test external reference 3", url:"www.testSite3.com").save(failOnError: true)
			
		}
		
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
																	description:"The PATIENT's surname at birth.",
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
						}

			}

		}
		}
		
		}
	
	}
