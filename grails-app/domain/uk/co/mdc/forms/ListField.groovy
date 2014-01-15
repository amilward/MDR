package uk.co.mdc.forms

class ListField extends InputField{

	Boolean multiSelect
	Boolean ordered
	Boolean fillIn
	
	static hasMany = [listItems: ListItem]
	
    static constraints = {
    }
}
