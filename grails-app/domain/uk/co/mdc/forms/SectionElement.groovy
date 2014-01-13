package uk.co.mdc.forms

import java.util.SortedSet;

class SectionElement extends FormDesignElement implements Comparable{
	
	String sectionNumber
	String title
	//N.B. Ordered?
	String[] instructions
	List<QuestionElement> questionElements
	
	static hasMany = [presentationElements: PresentationElement, questionElements: QuestionElement]

	static belongsTo = [formDesign: FormDesign]
	
	
	
	static fetchMode = [questionElements: 'eager']
	
    static constraints = {
		sectionNumber nullable:true
		formDesign nullable:true
		title nullable:true
		instructions nullable:true
    }
	
	static mapping = {
		questionElements cascade: 'all-delete-orphan'
		questionElements sort: 'designOrder'
	}
	
	@Override
	public int compareTo(obj){
	  if(obj){
		this.designOrder?.compareTo(obj.designOrder)
	  }
	}
	
	//this implements the sorting for the sortable set (question elements)
	
	
}
