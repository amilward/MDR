package uk.co.mdc.catalogue

import org.codehaus.groovy.grails.exceptions.InvalidPropertyException

class Relationship  {
	
	Integer objectXId
	Integer objectYId
	
	RelationshipType relationshipType

    static constraints = {
		
    }
	
	static link(objectX, objectY, RelationshipType relationshipType){
		
		def s
		def s1 = Relationship.findByObjectXIdAndObjectYId(objectX.id, objectY.id)
		def s2 = Relationship.findByObjectXIdAndObjectYId(objectY.id, objectX.id)

        //check if the relationship already exists
		if(s1){
			
			s = s1
			
		}else if(s2){
		
			s = s2

		//if the relationship doesn't exist then create a new one
		}else{

            //validate that objectX and objectY are the right class
            if(relationshipType.objectXClass){
                if(objectX.getClass().getSimpleName()!=relationshipType.objectXClass){
                    throw new InvalidPropertyException("object X (" + objectX +  ") must be of the " + relationshipType.objectXClass + " class for " + relationshipType.name)
                }
            }

            if(relationshipType.objectYClass){
                if(objectY.getClass().getSimpleName()!=relationshipType.objectYClass){
                    throw new InvalidPropertyException("object Y (" + objectY +  ") must be of the " + relationshipType.objectYClass + " class" + relationshipType.name)
                }
            }


		    //create the relationship
			s = new Relationship( 	objectXId: objectX.id,
							 		objectYId: objectY.id,
									relationshipType: relationshipType).save(flush:true, failOnError:true)
			
			
			//if relations not initialised for objects then initialise (this gets around a hibernate bug)
									
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
