package uk.co.mdc.model

class ModelElementService {
	
	
	/* ************************* MODEL ELEMENT LINKAGE FUNCTIONS************************
	 * links the model element with the relations specified via a link table
	 ********************************************************************************* */
	
	def linkRelations(modelElementInstance, relations, relationshipType){
		
		//get relationship type
		def relationType = RelationshipType.findByName(relationshipType)
		
		//get the relations already associated with the data element before the update
		def associatedRelations = modelElementInstance.relations()
		
		//if there are no new relations i.e. all have been removed in the edit data element form, then
		//remove all relations from the data element
		
		if(relations!=null){
			
			//if there is only one relations (rather than a list of them)
			
			if (relations instanceof String) {
				
				def newRelation =  ModelElement.get(relations)
				
				//remove any relations of the same type that aren't this one
				associatedRelations.each{ relation ->
					if(newRelation!=relation.id.toString() && relationshipType==relation?.relationshipType?.name){
							Relationship.unlink(modelElementInstance, relation)
					}
				}
				
				//add this one to the data element
				
				if(newRelation){
					Relationship.link(modelElementInstance, newRelation, relationType)
				}
				
			}
			
			//if there is a list of relations
			
			if (relations instanceof String[]) {
				
				//remove all the relations that aren't in the list
				associatedRelations.each{ relation ->
					if(!relations.contains(relation.id.toString()) && relationshipType==relation?.relationshipType?.name){
							Relationship.unlink(modelElementInstance, relation)
							
					}
				}
				
				//add all the relations in the list
				
				  for (relationsID in relations){
					  def relation =  ModelElement.get(relationsID)
					  if(relation){
							  Relationship.link(modelElementInstance, relation, relationType)
						}
				  }
  
				  
			}

		}else{
		
		//remove all the relations that aren't this one
		associatedRelations.each{ relation ->
			
			if(relationshipType==relation?.relationshipType?.name){
				Relationship.unlink(modelElementInstance, relation)
			}

		}
		
		}
		
	}
	
	
	
	
	//Method to check if parameters contain one another (i.e. array or single)
	
	

    Boolean parameterContains(params1, params2){
			
			if(params1 instanceof String[] && params2 instanceof String[]){
				
				params2.each{ params->
					
					if(params1.contains(params)){
						return true
					}
					
				}
				
			}else if(params1 instanceof String[] && params2 instanceof String){
			
				if(params1.contains(params2)){
					return true
				}
			
			}else if(params1 instanceof String && params2 instanceof String[]){
			
				if(params2.contains(params1)){
					return true
				}
			
			}else{
				if(params1==params2){
					return true
				}
			}
			
			return false		
	}
}
