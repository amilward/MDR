package uk.co.mdc.model

class ConceptualDomain {
	
	String name

	Integer refId
	
	String description
	
	static hasMany = [valueDomains: ValueDomain]
	
    static constraints = {
		valueDomains nullable:true
		refId unique:true
    }
	
	def prepareForDelete(){
		if(this.valueDomains.size()!=0){
			this.valueDomains.each{ p->
				p.prepareForDelete()
				}
		}
	}
	
}
