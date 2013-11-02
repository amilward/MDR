import org.json.simple.JSONObject
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.DataElementConcept
import uk.co.mdc.model.ValueDomain


class NHICImportConfig {

	public static functions = [ '/WEB-INF/bootstrap-data/NHIC/CAN/CAN.csv' : 
		{ tokens -> 
			def categories = [tokens[2], tokens[1], "Initial Proposal - CUH","Ovarian Cancer", "NHIC Datasets"];
			def dec = importDataElementConcepts(categories, null);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Local Identifier", tokens[8]);
			ext.put("Link to Existing Definition",tokens[6]);
			ext.put("Notes from GD/JCIS",tokens[7]);
			def vd = new ValueDomain('name' : "Value domain for " + tokens[0], 'refId' : tokens[0],'description' : tokens[5]).save(failOnError: true);
			def de = new DataElement(	name: tokens[3], 
										description : tokens[4], 
										dataElementConcept: dec, 
										extension: ext,
										refId: tokens[0]).save(failOnError: true)
			de.dataElementValueDomains().add(vd);
			de.save();
		}
		
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
	
	
}
