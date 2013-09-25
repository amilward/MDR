package uk.co.mdc.model

class DataElementDataElement {

	DataElement dataElement
	
	DataElement synonym

	static constraints = {
		
	}
	
	static DataElementDataElement link(dataElement, synonym) {
		
		
		def m = DataElementDataElement.findByDataElementAndSynonym(dataElement, synonym)
		if (!m)
		{

			m = new DataElementDataElement(dataElement: dataElement, synonym: synonym)
			
			synonym?.addToSynonyms(m)
			dataElement?.addToSynonyms(m)
			
			m.save()
			
		}
		return m
	}

	static void unlink(dataElement, synonym) {
		def m = DataElementDataElement.findByDataElementAndSynonym(dataElement, synonym)
		if (m)
		{
			synonym?.removeFromSynonyms(m)
			dataElement?.removeFromSynonyms(m)
			
			m.delete()
		}
	}
	
}
