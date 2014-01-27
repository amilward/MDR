package uk.co.mdc

import uk.co.mdc.catalogue.DataElement

class ModelBasket {
	
	/* The collection basket  is like a traditional shopping cart. When a user is created a corresponding collection cart is created which belongs to that user. 
	 * The collection basket  is persisted across sessions and allows users to add data elements via drag and drop on the dashboard using javascript and ajax
	 * (please see the collectionBasket controller and collection basket scripts section in main.js
	 *  Once they have added all the data elements needed they can view their data elements and then create a collection from them.
	 * */
	
	SecUser user
	
	static hasMany = [dataElements: DataElement]
	static belongsTo = [user: SecUser]

    static constraints = {
		dataElements nullable:true
    }
	
}
