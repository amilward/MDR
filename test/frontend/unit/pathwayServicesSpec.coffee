'use strict';

describe 'service', ->
    setup = {}
    beforeEach module('pathway.services')

    beforeEach inject (_$httpBackend_, $rootScope, Grails) ->
        setup.httpBackend = _$httpBackend_
        setup.scope = $rootScope.$new()
        setup.grails = Grails

    afterEach ->
        setup.httpBackend.verifyNoOutstandingExpectation();
        setup.httpBackend.verifyNoOutstandingRequest();

    describe 'NodeSelector', ->
        it 'should return null by default', inject (NodeSelector) ->
          expect(NodeSelector.getSelectedNode()).toBeNull()


        it 'should return the selected node', inject (NodeSelector) ->
            node = "node"
            NodeSelector.selectNode(node)
            expect(NodeSelector.getSelectedNode()).toBe(node)

        it 'should return allow the selected node to be changed', inject (NodeSelector) ->
            node = "node"
            node2 = "node2"
            NodeSelector.selectNode(node)
            expect(NodeSelector.isSelected(node)).toBe(true)
            expect(NodeSelector.isSelected(node2)).toBe(false)
            expect(NodeSelector.getSelectedNode()).toBe(node)
            expect(NodeSelector.getSelectedNode()).not.toBe(node2)


            NodeSelector.selectNode(node2)
            expect(NodeSelector.isSelected(node2)).toBe(true)
            expect(NodeSelector.isSelected(node)).toBe(false)
            expect(NodeSelector.getSelectedNode()).toBe(node2)
            expect(NodeSelector.getSelectedNode()).not.toBe(node)


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
