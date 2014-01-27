package uk.co.mdc.pathways

import grails.rest.Linkable


@Linkable
class Node extends Pathway{

    String name
    String description

    // Coordinates for rendering node
    Integer x
    Integer y

    // The owning pathway
    Pathway parent
    Pathway subPathway

    // TODO Forms
    // TODO Data elements
//    static hasMany = [
//            dataElements: DataElement
//            form: Form
//    ]


    static constraints = {
        description nullable:true
        subPathway nullable: true
        x nullable:true
        y nullable:true
    }
}
