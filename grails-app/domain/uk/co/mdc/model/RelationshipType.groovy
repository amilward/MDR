package uk.co.mdc.model

class RelationshipType {
	
	//name of the relationship type i.e. parentChild  or synonym
	String name
	
	//the both sides of the relationship ie. for parentChild this would be parent (for synonym this is synonym, so the same on both sides)
	String xYRelationship
	
	//the both sides of the relationship i.e. for parentChild this would be child (for synonym this is synonym, so the same on both sides)
	String yXRelationship

    static constraints = {
		xYRelationship nullable:true
		yXRelationship nullable:true
    }
}
