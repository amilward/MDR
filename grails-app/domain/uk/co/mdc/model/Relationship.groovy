package uk.co.mdc.model

class Relationship  {
	
	Integer objectXId
	Integer objectYId
	
	RelationshipType relationshipType
	
	Rule relationshipRule

    static constraints = {
		
		relationshipRule nullable:true
		
    }
	
	static link(objectX, objectY, RelationshipType relationshipType){
		
		def s
		def s1 = Relationship.findByObjectXIdAndObjectYId(objectX.id, objectY.id)
		def s2 = Relationship.findByObjectXIdAndObjectYId(objectY.id, objectX.id)
		
		if(s1){
			
			s = s1
			
		}else if(s2){
		
			s = s2
		
		}else{
		
			s = new Relationship( 	objectXId: objectX.id,
							 		objectYId: objectY.id,
									relationshipType: relationshipType).save(flush:true, failOnError:true)
			
			
			//if relations not intialised for objects then initialise						
									
			if(!objectX.relations){objectX.relations = []}	
			if(!objectY.relations){objectY.relations = []}
								 
			objectX.addToRelations(s)
			objectY.addToRelations(s)

			objectX.save(failOnError:true)
			objectY.save(failOnError:true)
			
		}
		
		return s
		
	}
	
	
	static unlink(objectX, objectY){
		
		def s1 = objectX.relations.find{ it.objectYId == objectY.id }
		def s2 = objectX.relations.find{ it.objectXId == objectY.id }
		
		if (s1)
		{
			objectX.removeFromRelations(s1)
			objectY.removeFromRelations(s1)
			objectX.save()
			objectY.save()
			
			s1.delete(flush:true)
			
		}
		
		if (s2)
		{
			objectX.removeFromRelations(s2)
			objectY.removeFromRelations(s2)
			objectX.save()
			objectY.save()
			
			s2.delete(flush:true)
			
		}
		
		
		
	}
}
