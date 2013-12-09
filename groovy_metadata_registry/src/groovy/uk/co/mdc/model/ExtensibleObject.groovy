package uk.co.mdc.model

import org.json.simple.JSONObject;

abstract class ExtensibleObject {

	JSONObject extension

    static constraints = {
    }
	
	
	static mapping = {
		extension sqlType: 'blob'
	}

	
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
