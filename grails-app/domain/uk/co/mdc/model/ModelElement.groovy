package uk.co.mdc.model

import java.util.List;

import org.json.simple.JSONObject;

abstract class ModelElement {
		
		JSONObject extension
		def storage = [:]
		
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
				
				def modelElement = ModelElement.get(objectId)
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
					if(relation.relationshipType==relationshipType){
						def objectId
						
						if(relation.objectYId == this.id){
							objectId = relation.objectXId
						}else{
							objectId = relation.objectYId
						}
						
						def modelElement = ModelElement.get(objectId)
						modelElement.relationshipType = relation.relationshipType
						relationsR.add(modelElement)
					}
						
				}
			
	
				return relationsR
			
			}
		
		
	
		public void addToRelations(Relationship relationship){
			relations.add(relationship)
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