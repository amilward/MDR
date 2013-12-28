package uk.co.mdc.model

import java.util.List;

import org.json.simple.JSONObject;

abstract class ModelElement {
		
		JSONObject extension
		Set<Relationship> relations
	
		static constraints = {
			extension nullable:true
			relations nullable:true
		}
		
		static hasMany = { relations: Relationship }
		
		static mapping = {
			extension sqlType: 'blob'
		}
		
		
		/******************************************************************************************************************/
		/****functions for specifying relationships between model elements (using the Relationship class) ************/
		/******************************************************************************************************************/
		
		/*List relations() {
			
			def relationshipList = []
			
			if(relations.collect{it.objectXId}[0] == this.id){
				
				def relationIds = relations.collect{it.objectYId}
				
				relationIds.each{ relationId->
					relationshipList.add(DataElement.get(relationId))
				}
				
			}else{
				
				def relationIds = relations.collect{it.objectXId}
				
				relationIds.each{ relationId->
					relationshipList.add(DataElement.get(relationId))
				}
		
			}
			
			return relationshipList
			
		}*/
		
	
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