package uk.co.mdc.model

class DataElementCollection {
	
	SchemaSpecification schemaSpecification
	
	DataElement dataElement
	
	Collection collection

    static constraints = {
		
		schemaSpecification nullable: true
		
		}
	
	
	
	static DataElementCollection link(DataElement, Collection) {
		def m = DataElementCollection.findByDataElementAndCollection(DataElement, Collection)
		if (!m)
		{
			m = new DataElementCollection()
			DataElement?.addToDataElementCollections(m)
			Collection?.addToDataElementCollections(m)
			m.save()
		}
		return m
	}
	
	static DataElementCollection link(DataElement, Collection, SchemaSpecification) {
		def m = DataElementCollection.findByDataElementAndCollection(DataElement, Collection)
		if (!m)
		{
			m = new DataElementCollection()
			DataElement?.addToDataElementCollections(m)
			Collection?.addToDataElementCollections(m)
			m.schemaSpecification = SchemaSpecification
			m.save()
		}else{
			m.schemaSpecification = SchemaSpecification
			m.save()
		}
		return m
	}
	
	

	static void unlink(DataElement, Collection) {
		def m = DataElementCollection.findByDataElementAndCollection(DataElement, Collection)
		if (m)
		{
			DataElement?.removeFromDataElementCollections(m)
			Collection?.removeFromDataElementCollections(m)
			m.delete(flush:true)
		}
	}
	
	
}
