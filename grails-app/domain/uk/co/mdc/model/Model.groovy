package uk.co.mdc.model

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
		extension nullable: true
    }
	
	static mapping = {
		description type: 'text'
	}
	
	
	/******************************************************************************************************************/
	/*********************remove all the associated valueDomains and collections before deleting data element*****************************/
	/******************************************************************************************************************/
	
	def prepareForDelete(){
				
		if(this.relations.size()!=0){
			
			dataForDelete = this.relations()
			
			dataForDelete.each{ relationship->
				this.removeFromRelations(relationship)
			}
		}
	}
	
	
}
