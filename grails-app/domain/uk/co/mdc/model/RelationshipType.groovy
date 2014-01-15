package uk.co.mdc.model

class RelationshipType {
	
	//name of the relationship type i.e. parentChild  or synonym
	String name
	
	//the both sides of the relationship ie. for parentChild this would be parent (for synonym this is synonym, so the same on both sides)
	String xYRelationship
	
	//the both sides of the relationship i.e. for parentChild this would be child (for synonym this is synonym, so the same on both sides)
	String yXRelationship
	
	//this is the rule that describes the relationship in terms of X and Y
	//for instance this could be custom validation, display etc.....i.e. if the relationship 
	//type is a mandatory data element then the model x must contain a value for the data element y
	
	Rule relationshipTypeRule

    static constraints = {
		name unique:true
		xYRelationship nullable:true
		yXRelationship nullable:true
		relationshipTypeRule nullable:true
    }
}
