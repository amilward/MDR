package uk.co.mdc.utils.importers
import org.json.simple.JSONObject
import org.springframework.security.acls.domain.BasePermission
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
import uk.co.mdc.pathways.Pathway

class ImportNHICService {

	static transactional = true
	
	def grailsApplication
	def aclUtilService
	def dataTypeService
	
	
    def importData() {
		def applicationContext = grailsApplication.mainContext
		String basePath = applicationContext.getResource("/").getFile().toString()
		
		functions.keySet().each { filename ->
			new File("${basePath}" + "/WEB-INF/bootstrap-data/NHIC" + filename).toCsvReader([charset:'UTF-8', skipLines : 1] ).eachLine { tokens ->
				functions[filename](tokens);
			}
		}
		
		grantUserPermissions(DataElement.list())
		grantUserPermissions(ValueDomain.list())
		grantUserPermissions(ConceptualDomain.list())
		grantUserPermissions(DataElementConcept.list())
		grantUserPermissions(DataType.list())
		grantUserPermissions(Document.list())
		grantUserPermissions(ExternalReference.list())
		grantUserPermissions(Collection.list())
		grantUserPermissions(Pathway.list())
    }
	private grantUserPermissions(objectList){
		for (object in objectList) {
			aclUtilService.addPermission object, 'ROLE_ADMIN', BasePermission.ADMINISTRATION
            aclUtilService.addPermission object, 'ROLE_USER', BasePermission.READ
		}
	}

	
	private static functions = [
		/** '/Initial/CAN.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "Initial Proposal - CUH","Ovarian Cancer", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Local Identifier", tokens[8]);
			ext.put("Link to Existing Definition",tokens[6]);
			ext.put("Notes from GD/JCIS",tokens[7]);

			def cd = findOrCreateConceptualDomain("CAN", "NHIC : Ovarian Cancer")


			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[5]).save(failOnError: true);

			def de = new DataElement(	name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0]
		},


		'/Initial/ACS.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "Initial Proposal - IMP","Acute Coronary Syndromes", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Local Identifier", tokens[7]);
			ext.put("Data Dictionary Element",tokens[6]);

			def cd = findOrCreateConceptualDomain("ACS","NHIC : Acute Coronary Syndromes")


			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[5]).save(failOnError: true);

			def de = new DataElement(	name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0]
		},
/**
		'/Initial/HEP.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "Initial Proposal - OUH","Viral Hepatitis C/B", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Local Identifier", tokens[8]);

			def cd = findOrCreateConceptualDomain("HEP", "NHIC : Viral Hepatitis C/B")


			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[5]).save(failOnError: true);

			def de = new DataElement(	name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0]
		},**/

		'/Initial/TRA.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "Initial Proposal - GSTT","Renal Transplantation", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);

			def cd = findOrCreateConceptualDomain("TRA", "NHIC : Renal Transplantation")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[5]).save(failOnError: true);

			def de = new DataElement(	name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0]
		},

		'/Initial/ICU.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "Initial Proposal - UCL","Intensive Care", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);

			def cd = findOrCreateConceptualDomain("ICU", "NHIC : Intensive Care")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[5]).save(failOnError: true);

			def de = new DataElement(	name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0]
		},
/**
		'/Round1/ACS/ACS_GSTT.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "GSTT","Round 1", "Acute Coronary Syndromes", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Data Dictionary Element", tokens[6]);
			ext.put("[Optional] Local Identifier", tokens[7]);
			ext.put("A: How is the data item collected", tokens[8]);
			ext.put("B. How is the data item stored, within the centre?", tokens[9]);
			ext.put("C. How would you describe the existing coverage?", tokens[10]);
			ext.put("D. How would you describe the existing quality?", tokens[11]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[12]);

			def cd = findOrCreateConceptualDomain("ACS", "NHIC : Acute Coronary Syndromes")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[5]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_GSTT"
		},

		'/Round1/ACS/ACS_OUH.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "OUH","Round 1", "Acute Coronary Syndromes", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Data Dictionary Element", tokens[6]);
			ext.put("[Optional] Local Identifier", tokens[7]);
			ext.put("A: How is the data item collected", tokens[8]);
			ext.put("B. How is the data item stored, within the centre?", tokens[9]);
			ext.put("C. How would you describe the existing coverage?", tokens[10]);
			ext.put("D. How would you describe the existing quality?", tokens[11]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[12]);

			def cd = findOrCreateConceptualDomain("ACS", "NHIC : Acute Coronary Syndromes")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[5]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_OUH"
		},

		'/Round1/ACS/ACS_UCL.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "UCL","Round 1", "Acute Coronary Syndromes", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Data Dictionary Element", tokens[6]);
			ext.put("[Optional] Local Identifier", tokens[7]);
			ext.put("A: How is the data item collected", tokens[8]);
			ext.put("B. How is the data item stored, within the centre?", tokens[9]);
			ext.put("C. How would you describe the existing coverage?", tokens[10]);
			ext.put("D. How would you describe the existing quality?", tokens[11]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[12]);

			def cd = findOrCreateConceptualDomain("ACS", "NHIC : Acute Coronary Syndromes")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[5]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_UCL"
		},
**/
		'/Round1/CAN/CAN_CUH.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "CUH","Round 1", "Ovarian Cancer", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Link to existing definition", tokens[6]);
			ext.put("Notes from GD/JCIS", tokens[7]);
			ext.put("[Optional] Local Identifier", tokens[8]);
			ext.put("A: How is the data item collected", tokens[9]);
			ext.put("B. How is the data item stored, within the centre?", tokens[10]);
			ext.put("C. How would you describe the existing coverage?", tokens[11]);
			ext.put("D. How would you describe the existing quality?", tokens[12]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[13]);
			ext.put("F. what  are the circumstances of data collection?  where and when is the data recorded?  who is responsible for data entry?", tokens[14]);
			ext.put("G. is there a particular form (or data standard, or proforma) used for the collection of the data?   If so, please supply a copy or reference. ", tokens[15]);
			ext.put("H. if the data is stored in a local database or data warehouse, what is the name and version of the database application?", tokens[16]);
			ext.put("E2. Source for column E - how data established?", tokens[17]);

			def cd = findOrCreateConceptualDomain("CAN", "NHIC : Ovarian Cancer")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[5]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_CAN"
		},
/**
		'/Round1/CAN/CAN_GSTT.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "GSTT","Round 1", "Ovarian Cancer", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Link to existing definition", tokens[6]);
			ext.put("Notes from GD/JCIS", tokens[7]);
			ext.put("[Optional] Local Identifier", tokens[8]);
			ext.put("A: How is the data item collected", tokens[9]);
			ext.put("B. How is the data item stored, within the centre?", tokens[10]);
			ext.put("C. How would you describe the existing coverage?", tokens[11]);
			ext.put("D. How would you describe the existing quality?", tokens[12]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[13]);

			def cd = findOrCreateConceptualDomain("CAN", "NHIC : Ovarian Cancer")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[5]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_GSTT"
		},

		'/Round1/CAN/CAN_IMP.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "IMP","Round 1", "Ovarian Cancer", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Link to existing definition", tokens[6]);
			ext.put("Notes from GD/JCIS", tokens[7]);
			ext.put("[Optional] Local Identifier", tokens[8]);
			ext.put("Location", tokens[9]);
			ext.put("Comments", tokens[10]);
			ext.put("A: How is the data item collected", tokens[11]);
			ext.put("B. How is the data item stored, within the centre?", tokens[12]);
			ext.put("C. How would you describe the existing coverage?", tokens[13]);
			ext.put("D. How would you describe the existing quality?", tokens[14]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[15]);

			def cd = findOrCreateConceptualDomain("CAN", "NHIC : Ovarian Cancer")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[5]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_IMP"
		},

		'/Round1/CAN/CAN_UCL.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "UCL","Round 1", "Ovarian Cancer", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[6]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Link to existing definition", tokens[7]);
			ext.put("Notes from GD/JCIS", tokens[8]);
			ext.put("[Optional] Local Identifier", tokens[9]);
			ext.put("Location", tokens[4]);
			ext.put("A: How is the data item collected", tokens[10]);
			ext.put("B. How is the data item stored, within the centre?", tokens[11]);
			ext.put("C. How would you describe the existing coverage?", tokens[12]);
			ext.put("D. How would you describe the existing quality?", tokens[13]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[14]);
			ext.put("Comments", tokens[15]);

			def cd = findOrCreateConceptualDomain("CAN", "NHIC : Ovarian Cancer")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[5]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[5],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_UCL"
		},

		'/Round1/HEP/HEP_OUH.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "OUH","Round 1", "Viral Hepatitis C/B", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("National Code", tokens[6]);
			ext.put("Data Dictionary Element", tokens[7]);
			ext.put("[Optional] Local Identifier", tokens[8]);
			ext.put("A: How is the data item collected", tokens[9]);
			ext.put("B. How is the data item stored, within the centre?", tokens[10]);
			ext.put("C. How would you describe the existing coverage?", tokens[11]);
			ext.put("D. How would you describe the existing quality?", tokens[12]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[13]);

			def cd = findOrCreateConceptualDomain("HEP", "NHIC : Viral Hepatitis C/B")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[4]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_OUH"
		},

		'/Round1/HEP/HEP_UCL.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "UCL","Round 1", "Viral Hepatitis C/B", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("National Code", tokens[6]);
			ext.put("Data Dictionary Element", tokens[7]);
			ext.put("[Optional] Local Identifier", tokens[8]);
			ext.put("A: How is the data item collected", tokens[9]);
			ext.put("B. How is the data item stored, within the centre?", tokens[10]);
			ext.put("C. How would you describe the existing coverage?", tokens[11]);
			ext.put("D. How would you describe the existing quality?", tokens[12]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[13]);

			def cd = findOrCreateConceptualDomain("HEP", "NHIC : Viral Hepatitis C/B")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[4]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_UCL"
		},

		'/Round1/ICU/ICU_GSTT.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "GSTT","Round 1", "Intensive Care", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("A: How is the data item collected", tokens[8]);
			ext.put("B. How is the data item stored, within the centre?", tokens[9]);
			ext.put("C. How would you describe the existing coverage?", tokens[10]);
			ext.put("D. How would you describe the existing quality?", tokens[11]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[12]);

			def cd = findOrCreateConceptualDomain("HEP", "NHIC : Intensive Care")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[4]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_GSTT"
		},

		'/Round1/ICU/ICU_UCL.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "UCL","Round 1", "Intensive Care", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("A: How is the data item collected", tokens[8]);
			ext.put("B. How is the data item stored, within the centre?", tokens[9]);
			ext.put("C. How would you describe the existing coverage?", tokens[10]);
			ext.put("D. How would you describe the existing quality?", tokens[11]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[12]);

			def cd = findOrCreateConceptualDomain("HEP", "NHIC : Intensive Care")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[4]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_UCL"
		},

		'/Round1/TRA/TRA_CUH.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "CUH","Round 1", "Renal Transplantation", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("A: How is the data item collected", tokens[8]);
			ext.put("B. How is the data item stored, within the centre?", tokens[9]);
			ext.put("C. How would you describe the existing coverage?", tokens[10]);
			ext.put("D. How would you describe the existing quality?", tokens[11]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[12]);
			ext.put("F. what  are the circumstances of data collection?  where and when is the data recorded?  who is responsible for data entry?", tokens[13]);
			ext.put("G. is there a particular form (or data standard, or proforma) used for the collection of the data?   If so, please supply a copy or reference.", tokens[14]);
			ext.put("H. if the data is stored in a local database or data warehouse, what is the name and version of the database application?", tokens[15]);

			def cd = findOrCreateConceptualDomain("TRA", "NHIC : Renal Transplantation")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[4]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_CUH"
		},

		'/Round1/TRA/TRA_GSTT.csv' :
		{ tokens ->
			def categories = [tokens[2], tokens[1], "GSTT","Round 1", "Renal Transplantation", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [tokens[5]]
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("A: How is the data item collected", tokens[8]);
			ext.put("B. How is the data item stored, within the centre?", tokens[9]);
			ext.put("C. How would you describe the existing coverage?", tokens[10]);
			ext.put("D. How would you describe the existing quality?", tokens[11]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[12]);

			def cd = findOrCreateConceptualDomain("TRA", "NHIC : Renal Transplantation")

			def vd = new ValueDomain(name : tokens[3],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[4]).save(failOnError: true);

			def de = new DataElement(name: tokens[3],
			description : tokens[4],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_GSTT"
		},

		'/Round1/TRA/TRA_OUH.csv' :
		{ tokens ->
			def categories = [tokens[1], "OUH","Round 1", "Renal Transplantation", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def dataTypes = [""]
			def dataType = importDataTypes(tokens[2],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("A: How is the data item collected", tokens[4]);
			ext.put("B. How is the data item stored, within the centre?", tokens[5]);
			ext.put("C. How would you describe the existing coverage?", tokens[6]);
			ext.put("D. How would you describe the existing quality?", tokens[7]);
			ext.put("E. How hard would it be to achieve a score of 1 for Parts A to D?", tokens[8]);

			def cd = findOrCreateConceptualDomain("TRA", "NHIC : Renal Transplantation")

			def vd = new ValueDomain(name : tokens[2],
			
			conceptualDomain: cd,
			dataType: dataType,
			description : tokens[3]).save(failOnError: true);

			def de = new DataElement(name: tokens[2],
			description : tokens[3],
			dataElementConcept: dec,
			extension: ext).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0] + "_Round1_OUH"
		}
 **/
	]



	private static importDataElementConcepts(nodenames, parent)
	{
		nodenames.reverse().inject(parent) {dec, name ->
			if(name.equals(""))
			{
				return dec;
			}
			def matches = DataElementConcept.findAllWhere("name" : name, "parent" : dec)
			if(matches.empty)
			{
				new DataElementConcept('name': name, 'parent': dec).save()
			}
			else
			{
				matches.first();
			}
		}
	}

	private static importDataTypes(name, dataType){

		def dataTypeReturn = DataType.findByName('String')

		dataType.each{ line ->

			String[] lines = line.split("\\r?\\n");

			//println(lines)
			def enumerated = false

			if(lines.size()>0 && lines[]!=null){

				Map enumerations = new HashMap()

				lines.each{ enumeratedValues->

					def EV = enumeratedValues.split(":")

					if(EV!=null && EV.size()>1 && EV[0]!=null &&EV[1]!=null){
						def key = EV[0]
						def value = EV[1]

						if(value.size()>244){
							value = value[0..244]
						}
						enumerated = true
						enumerations.put(key,value)
					}
				}

				if(enumerated){
					dataTypeReturn = new DataType(name:name,
					enumerated: enumerated,
					enumerations: enumerations).save(failOnError: true)
				}
			}
		}
		return dataTypeReturn
	}
	
	private static findOrCreateConceptualDomain(String name, String description){

		def cd = ConceptualDomain.findByName(name)

		if (!cd) {

			cd = new ConceptualDomain(name:name,
			description:description).save(failOnError: true);
		}
		return cd
	}
}