package uk.co.mdc.model;


import static org.grails.jaxrs.support.ConverterUtils.getDefaultEncoding;
import static org.grails.jaxrs.support.ConverterUtils.xmlToMap;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect.Type
import java.lang.annotation.Annotation;
import java.util.Map;
import grails.converters.XML;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware;
import org.codehaus.groovy.grails.web.converters.XMLParsingParameterCreationListener;
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap;
import org.grails.jaxrs.support.MessageBodyReaderSupport;


@Provider
@Consumes(["text/xml", "application/xml"])

class XMLCustomReader extends MessageBodyReaderSupport<Map> {
	
		/**
		 * Creates a map from an XML request entity stream.
		 *
		 * @param httpHeaders
		 *            HTTP headers.
		 * @param entityStream
		 *            XML request entity stream.
		 * @return a map representation of the XML request entity stream.
		 * @see ConverterUtils#xmlToMap(InputStream, String)
		 */
		@Override
		public Map readFrom(MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
				
			// TODO: obtain encoding from HTTP header and/or XML document
			String encoding = "UTF-8"
	
			System.out.println("test");
			
			// Convert XML to map used for constructing domain objects
			return xmlToMap(entityStream, encoding);
		}
			
}

