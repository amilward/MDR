package uk.co.mdc.model

class ConceptualDomain extends ModelElement  {

	String name
	String description

	static auditable = true

	static searchable = { content: spellCheck 'include' }

	static hasMany = [valueDomains: ValueDomain]

	static constraints = {
		valueDomains nullable:true
		name blank: false
		extension nullable: true
		relations nullable: true
	}

	def prepareForDelete(){
		if(this.valueDomains.size()!=0){
			this.valueDomains.each{ p->
				p.prepareForDelete()
			}
		}
	}

	static mapping = {
		description type: 'text'
		valueDomains cascade: 'save-update'
	}
}
