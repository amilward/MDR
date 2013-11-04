import org.json.simple.JSONObject
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.DataElementConcept
import uk.co.mdc.model.ValueDomain
import uk.co.mdc.model.ConceptualDomain
import uk.co.mdc.model.DataType

class NHICImportConfig {

	public static functions = [ 
		'/WEB-INF/bootstrap-data/NHIC/Initial/CAN.csv' : 
		{ tokens -> 
			def categories = [tokens[2], tokens[1], "Initial Proposal - CUH","Ovarian Cancer", "NHIC Datasets"];
			def dataTypes = [tokens[5]]
			def dec = importDataElementConcepts(categories, null);
			def dataType = importDataTypes(dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Local Identifier", tokens[8]);
			ext.put("Link to Existing Definition",tokens[6]);
			ext.put("Notes from GD/JCIS",tokens[7]);
			
			def cd = ConceptualDomain.findByRefId("CANCUH")
			
			if (!cd) {
			
				cd = new ConceptualDomain(name:"CAN CUH", 
										  refId:"CANCUH", 
										  description:"Initial Proposal - CUH").save(failOnError: true);
			}
			
			def vd = new ValueDomain(name : tokens[3], 
									refId : tokens[0],
									conceptualDomain: cd,
									dataType: dataType,
									description : tokens[5]).save(failOnError: true);
								
			def de = new DataElement(	name: tokens[3], 
										description : tokens[4], 
										dataElementConcept: dec, 
										extension: ext,
										refId: tokens[0]).save(failOnError: true)
			de.addToDataElementValueDomains(vd);
			de.save();
			println "importing: " + tokens[0]
		}/*,
	'/WEB-INF/bootstrap-data/NHIC/Initial/ACS.csv' :
	{ tokens ->
		def categories = [tokens[2], tokens[1], "Initial Proposal - IMP","Acute Coronary Syndromes", "NHIC Datasets"];
		def dec = importDataElementConcepts(categories, null);
		def ext = new JSONObject();
		ext.put("NHIC Identifier", tokens[0]);
		ext.put("Local Identifier", tokens[7]);
		ext.put("Data Dictionary Element",tokens[6]);
		def vd = new ValueDomain('name' : "Value domain for " + tokens[0], 'refId' : tokens[0],'description' : tokens[5]).save(failOnError: true);
		def de = new DataElement(	name: tokens[3],
									description : tokens[4],
									dataElementConcept: dec,
									extension: ext,
									refId: tokens[0]).save(failOnError: true)
		de.dataElementValueDomains().add(vd);
		de.save();
		println "importing: " + tokens[0]
	}*/
		
	]
		
	
	static private importDataElementConcepts(nodenames, parent)
	{
		nodenames.reverse().inject(parent) {dec, name -> 
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
	
	static private importDataTypes(dataType){
		return DataType.findByName('String')
	}
	
	
}
