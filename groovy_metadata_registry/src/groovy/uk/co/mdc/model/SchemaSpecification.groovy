package uk.co.mdc.model;

public enum SchemaSpecification {
	MANDATORY("MANDATORY"), REQUIRED("REQUIRED"), OPTIONAL("OPTIONAL"), X("X");

	final String value;

	SchemaSpecification(String value) { this.value = value; }
	
	String toString() { value; } 
	
	String getKey() { name(); }
	
}

