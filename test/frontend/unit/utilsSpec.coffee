'use strict';

describe 'service', ->
    setup = {}
    beforeEach module('utils')

    beforeEach inject (_$httpBackend_, $rootScope, Grails) ->
        setup.httpBackend = _$httpBackend_
        setup.scope = $rootScope.$new()
        setup.grails = Grails

    afterEach ->
        setup.httpBackend.verifyNoOutstandingExpectation();
        setup.httpBackend.verifyNoOutstandingRequest();

    describe 'Grails', ->

        it 'should call url with controller, action, and id', ->
            setup.httpBackend.expectGET('/testapp/grailsControllerName/grailsActionName/grailsId').respond()
            setup.scope.grailsAppName = 'testapp'
            setup.scope.controller = 'grailsControllerName'
            setup.scope.action = 'grailsActionName'
            setup.scope.id = 'grailsId'
            setup.grails.getResource(setup.scope).get()
            setup.httpBackend.flush()

        it 'should call url with only controller and action', ->
            setup.httpBackend.expectGET('/testapp/grailsControllerName/grailsActionName').respond()
            setup.scope.grailsAppName = 'testapp'
            setup.scope.controller = 'grailsControllerName'
            setup.scope.action = 'grailsActionName'
            setup.grails.getResource(setup.scope).get()
            setup.httpBackend.flush()

        it 'should call url with only controller and specified action', ->
            setup.httpBackend.expectGET('/testapp/grailsControllerName/alternateGrailsActionName').respond()
            setup.scope.grailsAppName = 'testapp'
            setup.scope.controller = 'grailsControllerName'
            setup.scope.action = 'grailsActionName'
            setup.grails.getResource(setup.scope).get {action: 'alternateGrailsActionName'}
            setup.httpBackend.flush()

        it 'should call url without controller, action, or id', ->
            setup.scope.grailsAppName = 'testapp'
            setup.httpBackend.expectGET('/testapp').respond()
            setup.grails.getResource(setup.scope).get()
            setup.httpBackend.flush()
