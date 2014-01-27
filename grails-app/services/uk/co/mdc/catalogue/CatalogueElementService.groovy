package uk.co.mdc.catalogue

import grails.validation.ValidationException
import uk.co.mdc.catalogue.CatalogueElement
import uk.co.mdc.catalogue.Relationship

class CatalogueElementService {



/* ************************* MODEL ELEMENT LINKAGE FUNCTIONS************************
	 * links the catalogue element with the relations when relation type not known
	 ********************************************************************************* */
    def linkRelations(catalogueElementInstance, relations){

        //create a map of the relations grouped by relationType
        def relationsByType = [:]

        relations.each{ relation ->

            if(!relationsByType.containsKey(relation.type)){
                relationsByType.put(relation.type, [relation])
            }else{
                def innerMap = relationsByType.get(relation.type)
                innerMap.push(relation)
            }
        }

        //iterate through the map and link the relations
        relationsByType.each{ relationType, rRelations ->
            this.linkRelations(catalogueElementInstance, rRelations, relationType)
        }

    }

	/* ************************* MODEL ELEMENT LINKAGE FUNCTIONS************************
	 * links the catalogue element with the relations specified via a link table
	 ********************************************************************************* */
	
	def linkRelations(catalogueElementInstance, relations, String relType){
		
		
		if(relType){
			
			
			//get relationship type
			def relationType = RelationshipType.findByName(relType)
			
			if(!relationType){
				relationType = new RelationshipType(name: relType)
			}
			
			//get the relations of this type already associated with the data element before the update
			def associatedRelations = catalogueElementInstance.relations(relType)
			
			//if there are no new relations i.e. all have been removed in the edit data element form, then
			//remove all relations from the data element
			
			if(relations!=null){

				//if there is only one relations (rather than a list of them)
				
				if (relations.size()==1) {
					
					def newRelation =  CatalogueElement.get(relations.id)
					
					//remove any relations of the same type that aren't this one
					associatedRelations.each{ relation ->
						if(newRelation!=relation.id.toString()){
								Relationship.unlink(catalogueElementInstance, relation)
						}
					}
					
					//add this one to the data element
					
					if(newRelation){

                        if(relations?.direction){
                            if(relations.direction==relationType.yXRelationship){

                                Relationship.link(newRelation, catalogueElementInstance, relationType)

                            }else{

                                Relationship.link(catalogueElementInstance, newRelation, relationType)
                                
                            }

                        }else{
						    Relationship.link(catalogueElementInstance, newRelation, relationType)
                        }
					}
					
				}
				
				//if there is a list of relations
				
				if (relations.size()>1) {
					
					//remove all the relations that aren't in the list
					associatedRelations.each{ relation ->
						if(!relations.contains(relation.id.toString())){
								Relationship.unlink(catalogueElementInstance, relation)
						}
					}
					
					//add all the relations in the list
					
					  for (relation in relations){
						  def newRelation =  CatalogueElement.get(relation.id)

                          if(relation.direction==relationType.yXRelationship){

                              Relationship.link(newRelation, catalogueElementInstance, relationType)

                          }else{

                              Relationship.link(catalogueElementInstance, newRelation, relationType)

                          }

					  }
	  
					  
				}
	
			}else{
			
			//remove all the relations that aren't this one
			associatedRelations.each{ relation ->
					Relationship.unlink(catalogueElementInstance, relation)
			}
			
			}
			
		}else{
		
		//throw error
		
		throw new MissingPropertyException("relationshipType")
		
		}
	}


    /* ************************* MODEL ELEMENT LINKAGE FUNCTIONS************************
 * links the catalogue element with the relations specified via a link table
 ********************************************************************************* */

    def linkRelations(catalogueElementInstance, relations, String relType, String direction){


        //FIXME we need to have a more robust check for direction but good enough for now
        if(direction=="XY" || direction == "YX"){

            if(relType){

                //get relationship type
                def relationType = RelationshipType.findByName(relType)

                if(!relationType){å
                    relationType = new RelationshipType(name: relType)
                }

                //get the relations of this type already associated with the data element before the update
                def associatedRelations = catalogueElementInstance.relations(relType)

                //if there are no new relations i.e. all have been removed in the edit data element form, then
                //remove all relations from the data element

                if(relations!=null){

                    //if there is only one relations (rather than a list of them)

                    if (relations instanceof String) {

                        def newRelation =  CatalogueElement.get(relations)

                        //remove any relations of the same type that aren't this one
                        associatedRelations.each{ relation ->
                            if(newRelation!=relation.id.toString()){
                                Relationship.unlink(catalogueElementInstance, relation)
                            }
                        }

                        //add this one to the data element

                        if(newRelation){
                            if(direction == "YX"){
                                Relationship.link(newRelation, catalogueElementInstance, relationType)
                            }else{
                                Relationship.link(catalogueElementInstance, newRelation, relationType)
                            }
                        }

                    }

                    //if there is a list of relations

                    if (relations instanceof String[]) {

                        //remove all the relations that aren't in the list
                        associatedRelations.each{ relation ->
                            if(!relations.contains(relation.id.toString())){
                                Relationship.unlink(catalogueElementInstance, relation)
                            }
                        }

                        //add all the relations in the list

                        for (relationsID in relations){
                            def newRelation =  CatalogueElement.get(relationsID)
                            if(newRelation){
                                if(direction == "YX"){
                                    Relationship.link(newRelation, catalogueElementInstance, relationType)
                                }else{
                                    Relationship.link(catalogueElementInstance, newRelation, relationType)
                                }
                            }
                        }


                    }

                }else{

                    //remove all the relations that aren't this one
                    associatedRelations.each{ relation ->
                        Relationship.unlink(catalogueElementInstance, relation)
                    }å

                }

            }else{

            //FIXME
            //throw error

                throw new MissingPropertyException("relationshipType")

            }
    }else{
            //FIXME
            //throw error
            throw new MissingPropertyException("direction")
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
