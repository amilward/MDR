package uk.co.mdc.forms

class FormDesignElement {
	
	String label
	String style
	String title
	String preText
	
	//I'm assuming that cardinality is the order of the design elements within the design
	Integer designOrder
	
	//Rule rule .............I'm leaving this for the next iteration - I'm assuming this 
	//has something to do with conditionality etc. but will have to double check
	
	static belongsTo = [formDesign: FormDesign]
	
    static constraints = {
		label nullable: true
		style nullable:true
		designOrder nullable:true
		title nullable: true
		preText nullable:true
    }
}
