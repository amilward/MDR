import grails.plugins.springsecurity.SecurityConfigType
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

// Additional configuration file locations. This is the default, but we need to load the contents of ~/.grails/model_catalogue-config.groovy
// for the production DB connection/username/passord.
 grails.config.locations = [ "classpath:${appName}-config.properties",
                             "classpath:${appName}-config.groovy",
                             "file:${userHome}/.grails/${appName}-config.properties",
                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = uk.co.mdc // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
	all:           '*/*',
	atom:          'application/atom+xml',
	css:           'text/css',
	csv:           'text/csv',
	form:          'application/x-www-form-urlencoded',
	html:          [
		'text/html',
		'application/xhtml+xml'
	],
	js:            'text/javascript',
	json:          [
		'application/json',
		'text/json'
	],
	multipartForm: 'multipart/form-data',
	rss:           'application/rss+xml',
	text:          'text/plain',
	xml:           [
		'text/xml',
		'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

//NEED TO REMOVE IN PRODUCTION - DISABLING JAVASCRIPT BUNDLING
grails.resources.debug=true

grails.converters.encoding = "UTF-8"

// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
	development {
		grails.logging.jul.usebridge = true
		
		//disable mail send functionality
		grails.mail.disabled=true
	}
	production {
		// TODO: grails.serverURL = "http://www.changeme.com"
		grails {
			logging.jul.usebridge = false
			// TODO: serverURL = "http://www.changeme.com"
			mail {
			  host = "smtp.gmail.com"
			  port = 465
			  props = ["mail.smtp.auth":"true",
					   "mail.smtp.socketFactory.port":"465",
					   "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
					   "mail.smtp.socketFactory.fallback":"false"]
			}
		 }
	}
}

// log4j configuration
log4j = {
	// Example of changing the log pattern for the default console appender:
	//
	//appenders {
	//    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
	//}

    //debug "org.grails.plugin.resource"
	error  'org.codehaus.groovy.grails.web.servlet',        // controllers
			'org.codehaus.groovy.grails.web.pages',          // GSP
			'org.codehaus.groovy.grails.web.sitemesh',       // layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping',        // URL mapping
			'org.codehaus.groovy.grails.commons',            // core / classloading
			'org.codehaus.groovy.grails.plugins',            // plugins
			'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate'
	debug 	'grails.app.services.grails.plugin.springsecurity.ui.SpringSecurityUiService'
	info 	'org.springframework.security'
	debug  	'uk.co.mdc.mail'		// Dummy mail output for dev
}


//javascript libraries

grails.views.javascript.library="jquery"



grails{
	plugins{
		springsecurity{

			// page to redirect to if a login attempt fails
			failureHandler.defaultFailureUrl = '/login/authfail/?login_error=1'

            // redirection page for success (including successful registration
            successHandler.defaultTargetUrl = '/dashboard/'
			
			// Added by the Spring Security Core plugin:
			userLookup.userDomainClassName = 'uk.co.mdc.SecUser'
			userLookup.authorityJoinClassName = 'uk.co.mdc.SecUserSecAuth'
			authority.className = 'uk.co.mdc.SecAuth'

			//disable to prevent double encryption of passwords
			ui.encodePassword = false

			// User registration: don't add user to any roles by default (this is done by an admin to approve the account)
			ui.register.defaultRoleNames = ['ROLE_PENDING']

			// Grails security password requirements
			// Stupendously long password validation regex (courtesy of Ryan Brooks (ryan.brooks@ndm.ox.ac.uk). Checks for all permutations of digit, character, symbol.
			//ui.password.validationRegex='((?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&]))|((?=.*\\d)(?=.*[!@#$%^&](?=.*[a-zA-Z])))|((?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&]))|((?=.*[a-zA-Z])(?=.*[!@#$%^&])(?=.*\\d))|((?=.*[!@#$%^&])(?=.*\\d)(?=.*[a-zA-Z]))|((?=.*[!@#$%^&])(?=.*[a-zA-Z])(?=.*\\d))'
			ui.password.minLength=8
			ui.password.maxLength=64

			securityConfigType = SecurityConfigType.InterceptUrlMap
			useSecurityEventListener = true

			onInteractiveAuthenticationSuccessEvent = { e, appCtx ->
				uk.co.mdc.SecUser.withTransaction {
					def user = uk.co.mdc.SecUser.get(appCtx.springSecurityService.currentUser.id)
					user.lastLoginDate = new Date()
					user.save()
				}
			}

			securityConfigType = "Annotation"
			controllerAnnotations.staticRules = [
                '/':                    ['IS_AUTHENTICATED_ANONYMOUSLY'],
                // Bower dependencies
                '/bower_components/**': ['IS_AUTHENTICATED_ANONYMOUSLY'],
				// Javascript
				'/js/**':      			['IS_AUTHENTICATED_ANONYMOUSLY'],
				'/js/vendor/**':  		['IS_AUTHENTICATED_ANONYMOUSLY'],
				'/plugins/**/js/**':	['IS_AUTHENTICATED_ANONYMOUSLY'],
				// CSS
				'/**/css/**':      		['IS_AUTHENTICATED_ANONYMOUSLY'],
				'/css/**': 				['IS_AUTHENTICATED_ANONYMOUSLY'],
                '/**/*.less':           ['IS_AUTHENTICATED_ANONYMOUSLY'],
				// Images
				'/images/**': 			['IS_AUTHENTICATED_ANONYMOUSLY'],
				'/img/**': 				['IS_AUTHENTICATED_ANONYMOUSLY'],
				// Anonymously acessible pages, e.g. registration & login
				'/login/*':    			['IS_AUTHENTICATED_ANONYMOUSLY'],
				'/logout/*':    		['IS_AUTHENTICATED_ANONYMOUSLY'],
				'/register/*':    		['IS_AUTHENTICATED_ANONYMOUSLY'],
                 '/ng/*': ['IS_AUTHENTICATED_ANONYMOUSLY'],
                 '/securityInfo/**': 	["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/role':  				["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/role/**':  			["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/user':  				["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/user/**':  			["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/aclClass':  			["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/aclClass/**': 	 	["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/aclSid':  			["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/aclSid/**':  			["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/aclEntry':  			["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/aclEntry/**': 		["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/aclObjectIdentity':	["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/aclObjectIdentity/*': ["hasAnyRole('ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/conceptualDomain/*':  ["hasAnyRole('ROLE_USER', 'ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/valueDomain/*':       ["hasAnyRole('ROLE_USER', 'ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/dataElement/*':       ["hasAnyRole('ROLE_USER', 'ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/umlModel/*':         	["hasAnyRole('ROLE_USER', 'ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/document/*':         	["hasAnyRole('ROLE_USER', 'ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY'],
				'/**':         			["hasAnyRole('ROLE_USER', 'ROLE_ADMIN')",'IS_AUTHENTICATED_FULLY']
			]
		}
	}
	
	views {
		codec = "html" // none, html, base64
		gsp {
			encoding = 'UTF-8'
			htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
			codecs {
				expression = 'html' // escapes values inside ${}
				scriptlet = 'html' // escapes output from scriptlets in GSPs
				taglib = 'none' // escapes output from taglibs
				staticparts = 'none' // escapes output from static template parts
			}
			sitemesh {
				preprocess = true
			}
		}
		// escapes all not-encoded output at final stage of outputting
		filteringCodecForContentType {
			//'text/html' = 'html'
		}
	}
}


//get username and add to audit logging

auditLog {
	actorClosure = { request, session ->
		if (request.applicationContext.springSecurityService.principal instanceof java.lang.String){
			return request.applicationContext.springSecurityService.principal
		}
		def username = request.applicationContext.springSecurityService.principal?.username
		if (SpringSecurityUtils.isSwitched()){
			username = SpringSecurityUtils.switchedUserOriginalUsername+" AS "+username
		}
		return username
	}
}

coffeescript.modules = {
    angularApp {
        String src = 'src/coffee/angular'
        files "${src}/services", "${src}/filters", "${src}/controllers", "${src}/app.coffee"
        output 'js/angular/angular-app.js'
        defaultBundle angularApp
    }
    angularTests {
        String src = 'src/coffee/angular'
        files "${src}/servicesSpec", "${src}/filtersSpec", "${src}/controllersSpec"
        output 'js/angular/test/unit/angularTests.js'
        defaultBundle angularTests
    }
}
