package uk.co.mdc.model

class Synonym {
	
	Integer dataElement1Id
	Integer dataElement2Id

    static constraints = {
    }
	
	static link(DataElement dataElement1, DataElement dataElement2){
		
		def s
		def s1 = Synonym.findByDataElement1IdAndDataElement2Id(dataElement1.id, dataElement2.id)
		def s2 = Synonym.findByDataElement1IdAndDataElement2Id(dataElement2.id, dataElement1.id)
		
		if(s1){
			
			s = s1
			
		}else if(s2){
		
			s = s2
		
		}else{
		
			s = new Synonym( dataElement1Id: dataElement1.id,
							 dataElement2Id: dataElement2.id).save()
			
			 
			dataElement1.addToSynonyms(s)
			dataElement2.addToSynonyms(s)

			dataElement1.save(flush:true)
			dataElement2.save(flush:true)
			s.save()
			
		}
		
		return s
		
	}
	
	
	static unlink(DataElement dataElement1, DataElement dataElement2){
		
		def s1 = dataElement1.synonyms.find{ it.dataElement2Id == dataElement2.id }
		def s2 = dataElement1.synonyms.find{ it.dataElement1Id == dataElement2.id }
		
		if (s1)
		{
			dataElement1.removeFromSynonyms(s1)
			dataElement2.removeFromSynonyms(s1)
			dataElement1.save(flush:true)
			dataElement2.save(flush:true)
			
			s1.delete(flush:true)
			
		}
		
		if (s2)
		{
			dataElement1.removeFromSynonyms(s2)
			dataElement2.removeFromSynonyms(s2)
			dataElement1.save(flush:true)
			dataElement2.save(flush:true)
			
			s2.delete(flush:true)
			
		}
		
		
		
	}
}
