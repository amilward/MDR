package uk.co.mdc

import org.codehaus.groovy.grails.plugins.orm.auditable.AuditLogEvent

class AuditLoggingHistoryTagLib {
	
	def objectHistory = {attrs->
		if(attrs.persistedObjectId) {
			def events = AuditLogEvent.findAllByPersistedObjectId(attrs.persistedObjectId, [sort:"dateCreated",order:"desc"])
			
			out <<  '<div class="accordion-group">'
			out <<  '<div class="accordion-heading">'
			out <<  '  <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#history">'
			out <<  '	History (click to expand)'
			out <<  '  </a>'
			out <<  '</div>'
			out <<  '<div id="history" class="accordion-body collapse">'
			out <<  '<div class="accordion-inner">'
			
			if(events.size() > 0) {
			  events.each{event->
				  if(event.propertyName!=null){
						out << '<p>'
						out << 'Property:' + event.propertyName
						out << '</br> Changed on : ' + event.dateCreated
						out << '</br> Changed by: ' + event.actor
						out << '</br> Old value: ' + event.oldValue
						out << '</br> New value: ' + event.newValue
						out << '</p>'
				  }
			  }
			}else{
			  out << ' no object history '
			}
			out <<  '</div>'
			out <<  '</div>'
			out <<  '</div>'
		}
	}
	
}
