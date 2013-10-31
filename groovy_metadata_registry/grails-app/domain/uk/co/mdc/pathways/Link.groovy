package uk.co.mdc.pathways

import uk.co.mdc.model.Collection;

class Link extends PathwayElement{
	
	Node source
	Node target

    static constraints = {
		mandatoryInputs nullable:true
		mandatoryOutputs nullable:true
		optionalInputs nullable:true
		optionalOutputs nullable:true
    }
}
