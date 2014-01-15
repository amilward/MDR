package uk.co.mdc.model

import java.util.List;

import org.json.simple.JSONObject;

abstract class CatalogueElement {
		
		def storage = [:]
		
		JSONObject extension
		
		static hasMany = { relations: Relationship }
		
		static constraints = {
			extension nullable:true
		}
		
		static mapping = {
			extension sqlType: 'blob'
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
				
				def modelElement = CatalogueElement.get(objectId)
				modelElement.relationshipType = relation.relationshipType
				relationsR.add(modelElement)
			}
		

			return relationsR
		
		}
		
		//return the relations with the given relationship type name
		List relations(String relationTypeName) {
			
				//array of relations to return to the caller
				def relationsR = []
				
				def relationshipType = RelationshipType.findByName(relationTypeName)
				
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
							
							if(relationshipType.xYRelationship){
								relationTypeName = relationshipType.yXRelationship
							}
							
						
							
						}
						
						def modelElement = CatalogueElement.get(objectId)
						modelElement.relationshipType = relationTypeName
						relationsR.add(modelElement)
					}
						
				}
			
	
				return relationsR
			
			}
		
		
	
		public void addToRelations(Relationship relationship){
			this.relations.add(relationship)
		}
		
		
		
		/******************************************************************************************************************/
		/****functions for adding json extensino to any model element************/
		/******************************************************************************************************************/
		
		public boolean addExtraAttribute(String name, Object obj)
		{
			try{
				if(extension==null)
				{
					extension = new JSONObject();
				}
				extension.put(name, obj);
				this.save();
				return true;
			}catch(Exception e){
				e.println
				return false;
			}
		}
		
		public Object getExtraAttributeValue(String name)
		{
			return this.extension.get(name);
			
		}
}