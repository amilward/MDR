package uk.co.mdc.model

import org.json.simple.JSONObject;

abstract class ExtensibleObject {

	JSONObject extension
	
	
    static constraints = {
    }
	static mapping = {
		extension sqlType: 'blob'
	}

}
