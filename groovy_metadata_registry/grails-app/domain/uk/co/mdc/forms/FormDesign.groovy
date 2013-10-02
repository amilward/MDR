package uk.co.mdc.forms

import uk.co.mdc.model.Collection

class FormDesign {
	
	String refId
	String name 
	String version
	Boolean isDraft
	//n.b. description is just for storage purposes not displayed when the form is rendered
	String description
	FormDesignElement header
	FormDesignElement footer
	//collection - link to the metadata registry model
	Collection collection
	
	static hasMany = [formDesignElements: FormDesignElement]

    static constraints = {
		refId unique:true, nullable: false
		name nullable:true
		header nullable:true
		footer nullable:true
		description nullable:true
		collection nullable: true
		formDesignElements nullable:true
    }
	
	def getQuestions(){
		Set elements = this.formDesignElements
		Set questions =  [];
		elements.each{ formDesignElement ->
			if(formDesignElement instanceof uk.co.mdc.forms.QuestionElement){
				questions.add(formDesignElement)
			}
		}
		
		return questions
		
	}
	
}
