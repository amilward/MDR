package uk.co.mdc.model

import java.util.List;

import org.json.simple.JSONObject;

abstract class ModelElement {
		
		JSONObject extension
	
		static hasMany = { relations: Relationship }
		
		static constraints = {
			extension nullable:true
		}
		
		static mapping = {
			extension sqlType: 'blob'
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