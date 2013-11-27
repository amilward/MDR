package uk.co.mdc.model

class DataElementValueDomain extends ExtensibleObject  {
	
	DataElement dataElement
	
	ValueDomain valueDomain

    static constraints = {
		
    }
	
	static DataElementValueDomain link(DataElement, ValueDomain) {
		def m = DataElementValueDomain.findByDataElementAndValueDomain(DataElement, ValueDomain)
		if (!m)
		{
			m = new DataElementValueDomain()
			
			DataElement?.addToDataElementValueDomains(m)
			ValueDomain?.addToDataElementValueDomains(m)
			m.save()
		}
		return m
	}

	static void unlink(DataElement, ValueDomain) {
		def m = DataElementValueDomain.findByDataElementAndValueDomain(DataElement, ValueDomain)
		if (m)
		{
			DataElement?.removeFromDataElementValueDomains(m)
			ValueDomain?.removeFromDataElementValueDomains(m)
			m.delete()
		}
	}
	
	
	
}
