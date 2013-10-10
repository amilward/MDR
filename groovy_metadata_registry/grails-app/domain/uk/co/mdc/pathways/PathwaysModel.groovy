package uk.co.mdc.pathways

class PathwaysModel {
	

	String refId
	String name
	String versionNo
	Boolean isDraft
	String description
	
	static hasMany = [pathwayElements : PathwayElement] 

    static constraints = {
		refId unique:true
		description nullable: true
    }
}
