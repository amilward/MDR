package uk.co.mdc

import grails.plugins.springsecurity.Secured

@Secured(['permitAll']) 

class ErrorsController {

def error403 = {}

def error404 = {}

def error500 = { render view: '/error' } 

}


