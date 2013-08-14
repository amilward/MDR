package uk.co.mdc

import uk.co.mdc.model.DataElement

class CollectionBasket {
	
	SecUser user
	
	static hasMany = [dataElements: DataElement]
	static belongsTo = [user: SecUser]

    static constraints = {
		dataElements nullable:true
    }
	
}
