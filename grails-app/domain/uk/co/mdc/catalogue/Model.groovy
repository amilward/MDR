package uk.co.mdc.catalogue

import java.util.List;
import java.util.Set;
import uk.co.mdc.forms.FormDesign

class Model extends CatalogueElement  {
	 
	String name
	String description
	 
	static auditable = true

	static hasMany = [relations: Relationship]
	 
    static constraints = {
		name blank: false
    }
	
	static mapping = {
		description type: 'text'
	}
	

}
