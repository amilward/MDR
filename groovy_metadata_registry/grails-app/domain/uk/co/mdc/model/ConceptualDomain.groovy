package uk.co.mdc.model

class ConceptualDomain {
	
	String refId
	
	String name
	
	String description
	
	static auditable = true
	
	static searchable = {
		content: spellCheck 'include'
	}
	
	static hasMany = [valueDomains: ValueDomain]
	
    static constraints = {
		valueDomains nullable:true
		refId unique:true
		name blank: false
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
	}
}
