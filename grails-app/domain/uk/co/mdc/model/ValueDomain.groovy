package uk.co.mdc.model

import java.util.List;

class ValueDomain extends ModelElement  {

	String name
	String unitOfMeasure
	String regexDef	
	String format	
	String description	
	DataType dataType	
	Set dataElementValueDomains = []	
	ConceptualDomain conceptualDomain
	
	static auditable = true
	
	static searchable = {
        content: spellCheck 'include'
    } 
	
	static hasMany = [relations: Relationship]
	
	static belongsTo = [conceptualDomain: ConceptualDomain]
	
    static constraints = {
		conceptualDomain nullable:true
		dataType nullable:true
		description nullable:true
		unitOfMeasure nullable:true
		regexDef nullable:true
		name blank: false
		format nullable:true
    }
	
	static mapping = {
		description type: 'text'
	}
	

	/******************************************************************************************************************/
	/*********************remove all the associated relations*****************************/
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
