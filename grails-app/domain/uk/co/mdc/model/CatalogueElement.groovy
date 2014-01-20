package uk.co.mdc.model

import java.util.List;

import org.json.simple.JSONObject;

abstract class CatalogueElement {
		

        //storage is used with propertyMissing to allow the user to extend any catalogue element properties
        def storage = [:]

        //finalized is used for versioning
        //once an object is finalized it cannot be changed
        //it's version number is updated and any subsequent update will
        //be applied to the next version (isDraft)
        Boolean finalized = false
		
		static hasMany = { relations: Relationship }
		
		static constraints = {
		}
		
		static mapping = {
		}
		
		
		/******************************************************************************************************************/
		/****allows you to add extra properties to all of the model elements ************************************************/
		/******************************************************************************************************************/
		def propertyMissing(String name, value) { 
			storage[name] = value 
		}
		
		
		def propertyMissing(String name) { 
			storage[name] 
		}
		
		
		
		/******************************************************************************************************************/
		/****functions for specifying relationships between model elements (using the Relationship class) ************/
		/******************************************************************************************************************/
		
		//return all the relations
		
		List relations() {
		
			//array of relations to return to the caller
			def relationsR = []
			
			relations.each{ relation ->
				def objectId
				
				if(relation.objectYId == this.id){
					objectId = relation.objectXId
				}else{
					objectId = relation.objectYId
				}
				
				def catalogueElement = CatalogueElement.get(objectId)
				catalogueElement.relationshipType = relation.relationshipType
				relationsR.add(catalogueElement)
			}
		

			return relationsR
		
		}
		
		//return the relations with the given relationship type name
		List relations(String relationTypeName) {
			
				//array of relations to return to the caller
				def relationsR = []
				
				if(relationTypeName){
				
					def relationshipType = RelationshipType.findByName(relationTypeName)

                    if(relationshipType){
						
                        relations.each{ relation ->
                            if(relation.relationshipType.id==relationshipType.id){
                                def objectId

                                //if the relation y side is this object then return the x side of the relationship otherwise return the y side
                                if(relation.objectYId == this.id){
                                    objectId = relation.objectXId

                                    //if the relationship type has an xYRelationship then return this instead of the relationship type name
                                    //i.e. if the relationship is parentChild and the object to return is the parent, then return Parent rather
                                    //then ParentChild as the relationshipT type

                                    if(relationshipType.xYRelationship){
                                        relationTypeName = relationshipType.xYRelationship
                                    }


                                }else{

                                    objectId = relation.objectYId

                                    //if the relationship type has an yXRelationship then return this instead of the relationship type name
                                    //i.e. if the relationship is parentChild and the object to return is the child, then return Child rather
                                    //then ParentChild as the relationship type

                                    if(relationshipType.yXRelationship){
                                        relationTypeName = relationshipType.yXRelationship
                                    }



                                }

                                def catalogueElement = CatalogueElement.get(objectId)
                                catalogueElement.relationshipType = relationTypeName
                                relationsR.add(catalogueElement)
                            }

                        }
                    }
				
				}
	
				return relationsR
			
			}
		
		
	
		public void addToRelations(Relationship relationship){
			this.relations.add(relationship)
		}
		
		/******************************************************************************************************************/
		/*********************remove all the associated relations before delete*****************************/
		/******************************************************************************************************************/
		
		def prepareForDelete(){
					
			if(this.relations.size()!=0){
				
				dataForDelete = this.relations
				
				dataForDelete.each{ relationship->
					this.removeFromRelations(relationship)
				}
			}
		}



    /******************************************************************************************************************/
    /******   method called before every update  used for versioning - all objects in the catalogue    ***********************/
    /******   are immutable and should be cloned be updated   ***********************/
    /******************************************************************************************************************/


    def beforeUpdate(){
        if(finalized){
            println('clone the object')
           // def newObjectInstance =
        }
    }





}