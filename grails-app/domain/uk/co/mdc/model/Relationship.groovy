package uk.co.mdc.model

class Relationship  {
	
	Integer dataElement1Id
	Integer dataElement2Id

    static constraints = {
    }
	
	static link(DataElement dataElement1, DataElement dataElement2){
		
		def s
		def s1 = Relationship.findByDataElement1IdAndDataElement2Id(dataElement1.id, dataElement2.id)
		def s2 = Relationship.findByDataElement1IdAndDataElement2Id(dataElement2.id, dataElement1.id)
		
		if(s1){
			
			s = s1
			
		}else if(s2){
		
			s = s2
		
		}else{
		
			s = new Relationship( 	dataElement1Id: dataElement1.id,
							 		dataElement2Id: dataElement2.id)
			
			 
			dataElement1.addToRelations(s)
			dataElement2.addToRelations(s)

			dataElement1.save()
			dataElement2.save()
			s.save(flush:true)
			
		}
		
		return s
		
	}
	
	
	static unlink(DataElement dataElement1, DataElement dataElement2){
		
		def s1 = dataElement1.relations.find{ it.dataElement2Id == dataElement2.id }
		def s2 = dataElement1.relations.find{ it.dataElement1Id == dataElement2.id }
		
		if (s1)
		{
			dataElement1.removeFromRelations(s1)
			dataElement2.removeFromRelations(s1)
			dataElement1.save()
			dataElement2.save()
			
			s1.delete(flush:true)
			
		}
		
		if (s2)
		{
			dataElement1.removeFromRelations(s2)
			dataElement2.removeFromRelations(s2)
			dataElement1.save()
			dataElement2.save()
			
			s2.delete(flush:true)
			
		}
		
		
		
	}
}
