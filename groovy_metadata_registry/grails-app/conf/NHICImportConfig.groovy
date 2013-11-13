import org.json.simple.JSONObject
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.DataElementConcept
import uk.co.mdc.model.ValueDomain
import uk.co.mdc.model.ConceptualDomain
import uk.co.mdc.model.DataType
import uk.co.mdc.model.DataTypeService

class NHICImportConfig {
	
	def dataTypeService

	public static functions = [ 
		'/WEB-INF/bootstrap-data/NHIC/Initial/CAN.csv' : 
		{ tokens -> 
			def categories = [tokens[2], tokens[1], "Initial Proposal - CUH","Ovarian Cancer", "NHIC Datasets"];
			def dataTypes = [tokens[5]]
			def dec = importDataElementConcepts(categories, null);
			def dataType = importDataTypes(tokens[3],dataTypes);
			def ext = new JSONObject();
			ext.put("NHIC Identifier", tokens[0]);
			ext.put("Local Identifier", tokens[8]);
			ext.put("Link to Existing Definition",tokens[6]);
			ext.put("Notes from GD/JCIS",tokens[7]);
			
			def cd = ConceptualDomain.findByRefId("CUH")
			
			if (!cd) {
			
				cd = new ConceptualDomain(name:"CUH", 
										  refId:"CUH", 
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
		},
	'/WEB-INF/bootstrap-data/NHIC/Initial/ACS.csv' :
	{ tokens ->
		def categories = [tokens[2], tokens[1], "Initial Proposal - IMP","Acute Coronary Syndromes", "NHIC Datasets"];
		def dataTypes = [tokens[5]]
		def dec = importDataElementConcepts(categories, null);
		def dataType = importDataTypes(tokens[3],dataTypes);
		def ext = new JSONObject();
		ext.put("NHIC Identifier", tokens[0]);
		ext.put("Local Identifier", tokens[7]);
		ext.put("Data Dictionary Element",tokens[6]);
		
		def cd = ConceptualDomain.findByRefId("ASC")
		
		if (!cd) {
		
			cd = new ConceptualDomain(name:"ASC",
									  refId:"ASC",
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
	
	static private importDataTypes(name, dataType){
		
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
	
	
}
