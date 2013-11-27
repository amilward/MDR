grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
		
		mavenRepo 'http://maven.restlet.org'
		
    }

    dependencies {
		// Selenium WebDriver, for use in Geb
		def webDriverVersion = "2.37.0"
		
		// specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes
		
		compile 'com.googlecode.json-simple:json-simple:1.1.1'
		
		// Testing modules
		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
		test "org.gebish:geb-spock:0.9.2"
		test "org.seleniumhq.selenium:selenium-support:${webDriverVersion}"
		test "org.seleniumhq.selenium:selenium-firefox-driver:${webDriverVersion}"
		test "org.seleniumhq.selenium:selenium-chrome-driver:${webDriverVersion}"
		
		// Required because of bug in 2.37.0 of WebDriver:
		test "org.apache.httpcomponents:httpclient:4.3.1"
		test("org.seleniumhq.selenium:selenium-htmlunit-driver:${webDriverVersion}") {
			exclude 'xml-apis'
		}
		
		
        runtime 'mysql:mysql-connector-java:5.1.22'
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.8.3"
        runtime ":resources:1.1.6"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.5"

        build ":tomcat:$grailsVersion"

        runtime ":database-migration:1.3.2"

        compile ':cache:1.0.1'
		
		test ":geb:0.9.2"
		test(":spock:0.7") {
			exclude "spock-grails-support"
		  }

    }
}
