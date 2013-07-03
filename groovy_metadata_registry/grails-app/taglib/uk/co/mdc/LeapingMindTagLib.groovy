package uk.co.mdc

class LeapingMindTagLib {

	static namespace = "lm"
 
	   def css = { attrs ->
	 
	      def fs = File.separator
	 
	      def dir = 'css'
	      def file = 'main.css'
	      def exists = grailsApplication.mainContext.getResource(dir + fs + file).exists()
	      if ( exists ) {
	         def href = g.resource(dir: dir, file: file)
	         out << "<link rel='stylesheet' href='${href}' />\r\n"
	      }//end if
	 
	      dir = 'css'
	      file = "${controllerName}.css"
	      exists = grailsApplication.mainContext.getResource(dir + fs + file).exists()
	      if ( exists ) {
	         def href = g.resource(dir: dir, file: file)
	         out << "<link rel='stylesheet' href='${href}' />\r\n"
	      }//end if
	 
	      dir = 'css'
	      file = "${controllerName}_${actionName}.css"
	      exists = grailsApplication.mainContext.getResource(dir + fs + file).exists()
	      if ( exists ) {
	         def href = g.resource(dir: dir, file: file)
	         out << "<link rel='stylesheet' href='${href}' />\r\n"
	      }//end if
	 
	   }//end css
	 
	}//end LeapingMindTagLib
