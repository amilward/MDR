package uk.co.mdc

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class NgController {

    def index() {
        def config = SpringSecurityUtils.securityConfig
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        [loginUrl: postUrl, rememberMe: config.rememberMe.parameter]
    }

}
