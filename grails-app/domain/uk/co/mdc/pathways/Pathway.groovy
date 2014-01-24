package uk.co.mdc.pathways


class Pathway {
	
	String  name
	String  userVersion = "1"
	Boolean isDraft = true
	String  description
	
	static hasMany = [
            nodes: uk.co.mdc.pathways.Node,
            links: Link
    ]
    static mappedBy = [
            nodes: 'parent',
            links: 'pathway'
    ]

    static constraints = {
        description nullable: true
		nodes       nullable: true
        links       nullable: true
    }
}
